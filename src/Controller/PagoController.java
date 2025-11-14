package Controller;

import DAO.ClienteDAO;
import Model.Constants.EstadoMoto;
import Model.Entities.*;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PagoController {

    private static PagoController instancia;

    private final VentaController ventaController;
    private final MotoController motoController;
    private final ClienteDAO clienteDAO;
    private final ItemCarritoController itemCarritoController;

    private PagoController() {
        this.ventaController = VentaController.getInstancia();
        this.motoController = MotoController.getInstancia();
        this.clienteDAO = ClienteDAO.getInstance();
        this.itemCarritoController = ItemCarritoController.getInstancia();
    }

    public static synchronized PagoController getInstancia() {
        if (instancia == null) {
            instancia = new PagoController();
        }
        return instancia;
    }

    public static void resetInstancia() {
        instancia = null;
    }

    public boolean procesarPago(
            Usuario usuarioActual,
            String nombreTarjeta,
            String numeroTarjeta,
            String fecha,
            String cvv,
            Moto motoSeleccionada,
            Carrito carrito
    ) {

        if (!validarDatosPago(nombreTarjeta, numeroTarjeta, fecha, cvv, usuarioActual)) {
            return false;
        }

        boolean guardarTarjeta = procesarDecisionTarjeta(usuarioActual, nombreTarjeta, numeroTarjeta, fecha, cvv);

        Cliente clienteCreado = crearActualizarCliente(usuarioActual, guardarTarjeta, nombreTarjeta, numeroTarjeta, fecha, cvv);

        List<ItemCarrito> items = obtenerItemsVenta(motoSeleccionada, carrito, clienteCreado);

        if (items == null || items.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El carrito está vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        boolean exito = ventaController.registrarVenta(clienteCreado, items);

        if (exito) {
            actualizarEstadoMotos(items);
            limpiarCarritoSiEsNecesario(carrito, clienteCreado);
            Venta ventaGenerada = ventaController.getUltimaVenta();
            ventaController.generarFacturaPDF(ventaGenerada, items, clienteCreado);
        }

        return exito;
    }

    public boolean validarDatosPago(String nombre, String numeroTarjeta, String fecha, String cvv, Usuario usuario) {

        String numeroTarjetaSoloDigitos = numeroTarjeta.replaceAll("\\D", "");

        if (!numeroTarjetaSoloDigitos.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(null, "Número de tarjeta inválido (debe tener 16 dígitos).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!fecha.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use MM/yy.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!cvv.matches("\\d{3,4}")) {
            JOptionPane.showMessageDialog(null, "CVV inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean procesarDecisionTarjeta(
            Usuario usuarioActual,
            String nombre,
            String numeroTarjeta,
            String fecha,
            String cvv
    ) {
        Cliente cliente = clienteDAO.buscarPorCedula(usuarioActual.getCedula());

        if (cliente == null || !cliente.isTarjetaGuardada()) {
            int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea guardar la tarjeta para futuras compras?",
                    "Guardar tarjeta",
                    JOptionPane.YES_NO_OPTION
            );
            return opcion == JOptionPane.YES_OPTION;
        }

        boolean tarjetaDiferente = !cliente.getNumeroTarjeta().equals(numeroTarjeta)
                || !cliente.getNombreTarjeta().equals(nombre)
                || !cliente.getFechaExpiracion().equals(fecha)
                || !cliente.getCvv().equals(cvv);

        if (!tarjetaDiferente) {
            return true;
        }

        int opcion = JOptionPane.showConfirmDialog(
                null,
                "La tarjeta ingresada es diferente a la guardada. ¿Desea actualizar la tarjeta guardada?",
                "Actualizar tarjeta",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            String pass = JOptionPane.showInputDialog("Ingrese su contraseña para confirmar:");

            if (pass != null && pass.equals(usuarioActual.getPassword())) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Contraseña incorrecta. No se actualizó la tarjeta.");
                return false;
            }
        }

        return false;
    }

    private Cliente crearActualizarCliente(
            Usuario usuarioActual,
            boolean guardarTarjeta,
            String nombre,
            String numeroTarjeta,
            String fecha,
            String cvv
    ) {
        Cliente clienteExistente = clienteDAO.buscarPorCedula(usuarioActual.getCedula());

        if (clienteExistente == null) {
            Cliente nuevo = new Cliente(
                    usuarioActual.getPrimerNombre(),
                    usuarioActual.getSegundoNombre(),
                    usuarioActual.getPrimerApellido(),
                    usuarioActual.getSegundoApellido(),
                    usuarioActual.getCedula(),
                    usuarioActual.getEmail(),
                    usuarioActual.getPassword(),
                    guardarTarjeta ? numeroTarjeta : null,
                    guardarTarjeta ? nombre : null,
                    guardarTarjeta ? fecha : null,
                    guardarTarjeta ? cvv : null,
                    guardarTarjeta
            );

            clienteDAO.registrarCliente(nuevo);

            if (clienteDAO instanceof ClienteDAO) {
                ((ClienteDAO) clienteDAO).recargarClientes();
            }

            return clienteDAO.buscarPorCedula(usuarioActual.getCedula());
        }

        if (guardarTarjeta) {
            clienteDAO.actualizarTarjeta(
                    clienteExistente.getCedula(),
                    numeroTarjeta,
                    nombre,
                    fecha,
                    cvv
            );

            if (clienteDAO instanceof ClienteDAO) {
                ((ClienteDAO) clienteDAO).recargarClientes();
            }

            clienteExistente = clienteDAO.buscarPorCedula(usuarioActual.getCedula());
        }

        return clienteExistente;
    }

    private List<ItemCarrito> obtenerItemsVenta(Moto motoSeleccionada, Carrito carrito, Cliente cliente) {

        if (motoSeleccionada != null) {
            ItemCarrito item = new ItemCarrito(
                    motoSeleccionada,
                    cliente.getCedula(),
                    null,
                    1,
                    new BigDecimal(motoSeleccionada.getPrecio()),
                    new BigDecimal(motoSeleccionada.getPrecio())
            );
            return List.of(item);
        }

        if (carrito != null) {
            return new ArrayList<>(carrito.getItems());
        }

        return null;
    }

    private void actualizarEstadoMotos(List<ItemCarrito> items) {
        for (ItemCarrito item : items) {
            Moto moto = item.getVehiculo();
            moto.setEstado(EstadoMoto.VENDIDO);
            motoController.eliminarMoto(moto.getIdMoto());
            motoController.guardarMoto(moto);
        }
    }

    private void limpiarCarritoSiEsNecesario(Carrito carrito, Cliente cliente) {
        if (carrito != null) {
            carrito.getItems().clear();
            itemCarritoController.limpiarCarritoPorUsuario(cliente.getCedula());
        }
    }

    public boolean cargarDatosTarjeta(
            Component parent,
            JTextField txtNombre,
            JTextField txtNumeroTarjeta,
            JTextField txtFecha,
            JTextField txtCVV
    ) {

        Sesion sesion = Sesion.getInstancia();
        Usuario usuarioActual = sesion.getUsuarioActual();

        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(parent, "Debe iniciar sesión primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String password = JOptionPane.showInputDialog(parent, "Ingrese su contraseña para continuar:");

        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Debe ingresar la contraseña.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!password.equals(usuarioActual.getPassword())) {
            JOptionPane.showMessageDialog(parent, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Cliente cliente = clienteDAO.buscarPorCedula(usuarioActual.getCedula());

        if (cliente == null || !cliente.isTarjetaGuardada()) {
            JOptionPane.showMessageDialog(parent, "No hay tarjeta guardada para este usuario.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        txtNombre.setText(cliente.getNombreTarjeta());
        txtNumeroTarjeta.setText(cliente.getNumeroTarjeta());
        txtFecha.setText(cliente.getFechaExpiracion());
        txtCVV.setText(cliente.getCvv());

        JOptionPane.showMessageDialog(parent, "Datos de tarjeta cargados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        return true;
    }

}
