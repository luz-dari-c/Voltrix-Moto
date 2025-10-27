package voltrixmoto;

import Controller.VentaController;
import DAO.MotoDAO;
import Model.Constants.*;
import Model.Entities.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VoltrixMoto {

    public static void main(String[] args) {

        // 1️⃣ Cargar usuario desde JSON
        List<Usuario> usuarios = cargarUsuariosDesdeJson("src/Resources/Data/usuarios.json");
        Usuario usuario = null;

        for (Usuario u : usuarios) {
            if ("luzdaricrespo@gmail.com".equalsIgnoreCase(u.getEmail())) {
                usuario = u;
                break;
            }
        }

        if (usuario == null) {
            System.out.println("❌ No se encontró el usuario en usuarios.json");
            return;
        }
        System.out.println("✅ Usuario encontrado: " + usuario.getPrimerNombre() + " " + usuario.getPrimerApellido());

        // 2️⃣ Crear una moto real con todas sus partes
        MotoDAO motoDAO = MotoDAO.getInstancia();

        Motor motor = new Motor(TipoMotor.CUATRO_TIEMPOS, 250, 22);
        Chasis chasis = new Chasis(MaterialChasis.ACERO, TipoChasis.TUBULAR);
        Asiento asiento = new Asiento(MaterialAsiento.CUERO, CapacidadAsiento.BIPLAZA);
        LlantaDelantera llantaDelantera = new LlantaDelantera(MedidaLlanta.R17_120_70, "Voltrix", "Mkt", MaterialLlanta.ALEACION);
        LlantaTrasera llantaTrasera = new LlantaTrasera(MedidaLlanta.R17_120_70, "Voltrix", "Mkt", MaterialLlanta.ALEACION);
        FrenoDelantero frenoDelantero = new FrenoDelantero("FAORD", "Liopmn", MaterialFreno.CERAMICO);
        FrenoTrasero frenoTrasero = new FrenoTrasero("FAORD", "Liopm", MaterialFreno.ACERO);
        Transmision transmision = new Transmision(TipoTransmision.AUTOMATICA, TipoVelocidades.SEIS);

        PartesMoto partes = new PartesMoto(
                llantaDelantera,
                llantaTrasera,
                chasis,
                motor,
                asiento,
                frenoDelantero,
                frenoTrasero,
                transmision
        );

        Moto motoNueva = new Moto(
                "Yamaha",
                "FZ-25",
                LocalDate.now(),
                12500000,
                partes,
                TipoMoto.DEPORTIVA,
                TipoColorMoto.NEGRO,
                true,
                false
        );

        // ✅ Asegurar que tenga una placa válida
        if (motoNueva.getPlaca() == null || motoNueva.getPlaca().isEmpty()) {
            motoNueva.setPlaca("TEMP-" + System.currentTimeMillis());
        }

        // Guardar moto
        boolean guardada = motoDAO.guardarMoto(motoNueva);

        if (guardada) {
            System.out.println("✅ Moto creada y guardada correctamente en motos.json");
        } else {
            System.out.println("⚠️ La moto no se pudo guardar (quizá ya existe una con la misma placa).");
        }

        // 3️⃣ Cargar moto desde el JSON (para venta)
        List<Moto> motos = motoDAO.cargarTodas();
        if (motos.isEmpty()) {
            System.out.println("❌ No hay motos registradas.");
            return;
        }

        Moto motoSeleccionada = motos.get(0);
        System.out.println("✅ Moto seleccionada para la venta: "
                + motoSeleccionada.getMarca() + " " + motoSeleccionada.getModelo());

        // 4️⃣ Crear item del carrito
        BigDecimal precio = new BigDecimal(motoSeleccionada.getPrecio());
        ItemCarrito item = new ItemCarrito(motoSeleccionada, "ITEM-" + motoSeleccionada.getIdMoto(), 1, precio, precio);
        List<ItemCarrito> items = new ArrayList<>();
        items.add(item);

        // 5️⃣ Registrar la venta
        VentaController ventaController = new VentaController();
        boolean exito = ventaController.registrarVenta(usuario, items);

        if (exito) {
            System.out.println("\n✅ Venta registrada correctamente para: " + usuario.getPrimerNombre());
        } else {
            System.out.println("\n❌ Error al registrar la venta.");
        }

        // 6️⃣ Mostrar ventas
        List<Venta> ventas = ventaController.listarVentas();
        System.out.println("\n📋 Ventas registradas en el sistema:");
        for (Venta v : ventas) {
            System.out.println("---------------------------------------------");
            System.out.println("ID Venta: " + v.getIdVenta());
            System.out.println("Cliente: " + v.getUsuario().getPrimerNombre() + " " + v.getUsuario().getPrimerApellido());
            System.out.println("Correo: " + v.getUsuario().getEmail());
            System.out.println("Fecha: " + v.getFechaVenta());
            System.out.println("Total: $" + v.getTotal());
            System.out.println("Estado: " + v.getEstado());
            System.out.println("Motos compradas:");
            for (ItemCarrito i : v.getItemsVendidos()) {
                System.out.println(" - " + i.getVehiculo().getMarca() + " " + i.getVehiculo().getModelo() + " | Precio: $" + i.getPrecioUnitario());
            }
        }
    }

    private static List<Usuario> cargarUsuariosDesdeJson(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<ArrayList<Usuario>>() {
            }.getType();
            return new Gson().fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.err.println("Error al leer usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
