package Controller;

import DAO.ItemCarritoDAO;
import Model.Entities.ItemCarrito;
import Model.Entities.Moto;
import Model.Entities.Sesion;
import Model.Entities.Usuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemCarritoController {

 
    private static ItemCarritoController instancia;

    private final ItemCarritoDAO itemCarritoDAO;

    private ItemCarritoController() {
        this.itemCarritoDAO = ItemCarritoDAO.getInstancia();
    }

    public static ItemCarritoController getInstancia() {
        if (instancia == null) {
            instancia = new ItemCarritoController();
        }
        return instancia;
    }

    public boolean agregarItem(Moto vehiculo, int cantidad, BigDecimal precioUnitario) {
        if (vehiculo == null || cantidad <= 0 || precioUnitario == null) {
            System.err.println("Datos inválidos al agregar item al carrito.");
            return false;
        }

        Sesion sesion = Sesion.getInstancia();
        Usuario usuario = sesion.getUsuarioActual();
        String usuarioId = usuario != null ? usuario.getCedula() : null;

        List<ItemCarrito> items = itemCarritoDAO.cargarTodos();

        for (ItemCarrito item : items) {
            if (item.getVehiculo() != null
                    && item.getVehiculo().getIdMoto() == vehiculo.getIdMoto()
                    && usuarioId != null
                    && usuarioId.equals(item.getUsuarioId())) {

                int nuevaCantidad = item.getCantidad() + cantidad;
                itemCarritoDAO.actualizarCantidad(item.getId(), nuevaCantidad);
                System.out.println("Cantidad actualizada para el vehículo: " + vehiculo.getModelo());
                return true;
            }
        }

        BigDecimal subtotal = precioUnitario.multiply(new BigDecimal(cantidad));

        ItemCarrito nuevoItem = new ItemCarrito(vehiculo, usuarioId, null, cantidad, precioUnitario, subtotal);
        return itemCarritoDAO.guardarItem(nuevoItem);
    }

    public List<ItemCarrito> obtenerItemsPorUsuario(String idUsuario) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            return new ArrayList<>();
        }
        List<ItemCarrito> todos = itemCarritoDAO.cargarTodos();
        return new ArrayList<>(
                todos.stream()
                        .filter(item -> item.getUsuarioId() != null && item.getUsuarioId().equals(idUsuario))
                        .toList()
        );
    }

    public boolean eliminarItem(String id) {
        if (id == null || id.isEmpty()) {
            System.err.println("ID inválido al intentar eliminar un item.");
            return false;
        }
        return itemCarritoDAO.eliminarItem(id);
    }

    public boolean actualizarCantidad(String id, int nuevaCantidad) {
        if (id == null || id.isEmpty() || nuevaCantidad <= 0) {
            System.err.println("Datos inválidos al actualizar cantidad.");
            return false;
        }
        return itemCarritoDAO.actualizarCantidad(id, nuevaCantidad);
    }

    public List<ItemCarrito> listarItems() {
        return itemCarritoDAO.cargarTodos();
    }

    public ItemCarrito buscarItemPorId(String id) {
        if (id == null || id.isEmpty()) {
            System.err.println("ID inválido al buscar item.");
            return null;
        }
        return itemCarritoDAO.buscarPorId(id);
    }

    public BigDecimal calcularTotal() {
        return itemCarritoDAO.calcularTotal();
    }

    public boolean limpiarCarrito() {
        return itemCarritoDAO.limpiarCarrito();
    }

    public int generarNuevoId() {
        List<ItemCarrito> items = itemCarritoDAO.cargarTodos();
        return itemCarritoDAO.generarNuevoId(items);
    }

    public Moto buscarMotoPorId(int idMoto) {
        MotoController motoController = new MotoController();
        return motoController.buscarPorId(idMoto);
    }

    public boolean limpiarCarritoPorUsuario(String idUsuario) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            System.err.println("ID de usuario inválido al limpiar carrito.");
            return false;
        }

        List<ItemCarrito> itemsUsuario = obtenerItemsPorUsuario(idUsuario);
        System.out.println("Eliminando " + itemsUsuario.size() + " items del usuario: " + idUsuario);

        boolean todosEliminados = true;

        for (ItemCarrito item : itemsUsuario) {
            boolean eliminado = itemCarritoDAO.eliminarItem(item.getId());
            if (!eliminado) {
                System.err.println("Error al eliminar item: " + item.getId());
                todosEliminados = false;
            }
        }

        System.out.println("Resultado limpieza carrito: " + todosEliminados);
        return todosEliminados;
    }
}
