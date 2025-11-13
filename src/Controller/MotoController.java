package Controller;

import DAO.MotoDAO;
import Model.Constants.*;
import Model.Entities.Moto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class MotoController {

    private final MotoDAO motoDAO;
    private static MotoController instancia;

    public MotoController() {
        this.motoDAO = MotoDAO.getInstancia();
    }

    public static MotoController getInstancia() {
        if (instancia == null) {
            instancia = new MotoController();
        }
        return instancia;
    }

    public boolean guardarMoto(Moto moto) {
        if (moto == null) {
            System.err.println("No se puede guardar una moto nula");
            return false;
        }
        return motoDAO.guardarMoto(moto);
    }

    public boolean eliminarMoto(int idMoto) {
        if (idMoto <= 0) {
            System.err.println("El ID de la moto no es valido");
            return false;
        }
        return motoDAO.eliminarMoto(idMoto);
    }

    /*
    public boolean actualizarPorPlacaBase(
            String placaBase,
            String nuevoModelo,
            LocalDate nuevaFechaIngreso,
            Double nuevoPrecio,
            Boolean nuevaParrilla,
            Boolean nuevoMaletero,
            TipoMotor nuevoTipoMotor,
            Integer nuevaCilindrada,
            Integer nuevaPotencia,
            MaterialChasis nuevoMaterialChasis,
            TipoChasis nuevoTipoChasis,
            MaterialAsiento nuevoMaterialAsiento,
            CapacidadAsiento nuevaCapacidadAsiento,
            TipoTransmision nuevoTipoTransmision,
            TipoVelocidades nuevasVelocidades
    ) {
        if (placaBase == null || placaBase.trim().isEmpty()) {
            System.err.println("La placa base no puede estar vacía");
            return false;
        }

        if (!placaBase.startsWith("BSE-")) {
            System.err.println("La placa debe ser de una moto base (BSE-...)");
            return false;
        }

        return motoDAO.actualizarPorPlacaBase(
                placaBase,
                nuevoModelo,
                nuevaFechaIngreso,
                nuevoPrecio,
                nuevaParrilla,
                nuevoMaletero,
                nuevoTipoMotor,
                nuevaCilindrada,
                nuevaPotencia,
                nuevoMaterialChasis,
                nuevoTipoChasis,
                nuevoMaterialAsiento,
                nuevaCapacidadAsiento,
                nuevoTipoTransmision,
                nuevasVelocidades
        );
    }
     */
    public boolean actualizarPorPlacaBase(
            String placaBaseSeleccionada,
            String nuevoModelo,
            Boolean nuevaParrilla,
            Boolean nuevoMaletero,
            Integer nuevoCilindraje,
            // Motor
            TipoMotor nuevoTipoMotor,
            Integer nuevaCilindradaMotor,
            Integer nuevaPotenciaMotor,
            // Chasis
            MaterialChasis nuevoMaterialChasis,
            TipoChasis nuevoTipoChasis,
            // Asiento
            MaterialAsiento nuevoMaterialAsiento,
            CapacidadAsiento nuevaCapacidadAsiento,
            // Transmisión
            TipoTransmision nuevoTipoTransmision,
            TipoVelocidades nuevasVelocidades,
            // Llanta
            String tipoLlantaSeleccionada,
            MedidaLlanta nuevaMedidaLlanta,
            String nuevaMarcaLlanta,
            MaterialLlanta nuevoMaterialLlanta,
            String nuevoModeloLlanta,
            // Freno
            String tipoFrenoSeleccionado,
            String nuevaMarcaFreno,
            MaterialFreno nuevoMaterialFreno,
            String nuevoModeloFreno
    ) {
        if (placaBaseSeleccionada == null || placaBaseSeleccionada.equals("Seleccionar")) {
            System.err.println("Debes seleccionar una placa base válida.");
            return false;
        }

        if (!placaBaseSeleccionada.startsWith("BSE-")) {
            System.err.println("La placa seleccionada debe pertenecer a una moto base (BSE-...).");
            return false;
        }

        boolean hayCambios
                = (nuevoModelo != null && !nuevoModelo.isEmpty())
                || nuevaParrilla != null || nuevoMaletero != null
                || nuevoCilindraje != null
                || nuevoTipoMotor != null || nuevaCilindradaMotor != null || nuevaPotenciaMotor != null
                || nuevoMaterialChasis != null || nuevoTipoChasis != null
                || nuevoMaterialAsiento != null || nuevaCapacidadAsiento != null
                || nuevoTipoTransmision != null || nuevasVelocidades != null
                || (tipoLlantaSeleccionada != null && !tipoLlantaSeleccionada.equals("Seleccionar"))
                || (tipoFrenoSeleccionado != null && !tipoFrenoSeleccionado.equals("Seleccionar"));

        if (!hayCambios) {
            JOptionPane.showMessageDialog(
                    null,
                    "No se realizó ningún cambio. Todos los campos están vacíos o sin selección.",
                    "Sin cambios",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        return motoDAO.actualizarPorPlacaBase(
                placaBaseSeleccionada,
                nuevoModelo,
                nuevaParrilla,
                nuevoMaletero,
                nuevoCilindraje,
                nuevoTipoMotor,
                nuevaCilindradaMotor,
                nuevaPotenciaMotor,
                nuevoMaterialChasis,
                nuevoTipoChasis,
                nuevoMaterialAsiento,
                nuevaCapacidadAsiento,
                nuevoTipoTransmision,
                nuevasVelocidades,
                tipoLlantaSeleccionada,
                nuevaMedidaLlanta,
                nuevaMarcaLlanta,
                nuevoMaterialLlanta,
                nuevoModeloLlanta,
                tipoFrenoSeleccionado,
                nuevaMarcaFreno,
                nuevoMaterialFreno,
                nuevoModeloFreno
        );
    }

    public boolean actualizarMotoIndividual(
            int idMoto,
            String nuevaMarca,
            TipoColorMoto nuevoColor
    ) {
        if (idMoto <= 0) {
            System.err.println("El ID de la moto no es valido");
            return false;
        }

        if (nuevaMarca == null && nuevoColor == null) {
            System.err.println("Debe proporcionar al menos una marca o color para actualizar");
            return false;
        }

        return motoDAO.actualizarMotoIndividual(idMoto, nuevaMarca, nuevoColor);
    }

    public List<Moto> listarMotos() {
        return motoDAO.cargarTodas();
    }

    public Moto buscarPorId(int idMoto) {
        if (idMoto <= 0) {
            System.err.println("El ID de la moto no es valido");
            return null;
        }

        return motoDAO.cargarTodas().stream()
                .filter(m -> m.getIdMoto() == idMoto)
                .findFirst()
                .orElse(null);
    }

    public List<Moto> obtenerMotosDisponiblesPorTipo(TipoMoto tipo) {
        return motoDAO.obtenerMotosPorTipoYDisponibles(tipo);
    }

    public Map<TipoColorMoto, Integer> contarPorColor(TipoMoto tipo) {
        List<Moto> disponibles = motoDAO.obtenerMotosPorTipoYDisponibles(tipo);
        Map<TipoColorMoto, Integer> conteo = new HashMap<>();

        for (Moto moto : disponibles) {
            TipoColorMoto color = moto.getTipoColorMoto();
            conteo.put(color, conteo.getOrDefault(color, 0) + 1);
        }

        for (TipoColorMoto color : TipoColorMoto.values()) {
            conteo.putIfAbsent(color, 0);
        }

        return conteo;
    }

    public boolean disminuirMotoPorColorYTipo(TipoMoto tipo, TipoColorMoto color) {
        List<Moto> disponibles = motoDAO.obtenerMotosPorTipoYDisponibles(tipo);

        for (Moto moto : disponibles) {
            if (moto.getTipoColorMoto() == color && moto.getEstado() == EstadoMoto.DISPONIBLE) {
                moto.setEstado(EstadoMoto.VENDIDO);
                motoDAO.actualizarMoto(moto);
                return true;
            }
        }

        return false;
    }

    public boolean actualizarMoto(Moto motoSeleccionada) {
        if (motoSeleccionada == null) {
            System.out.println("No hay ninguna moto para actualizar (Null)");

        } else {
            motoDAO.actualizarMoto(motoSeleccionada);
            return true;
        }

        return false;
    }

    public Moto obtenerMotoBase() {
        return motoDAO.obtenerMotoBase();
    }

    public Moto obtenerMotoBasePorTipo(TipoMoto tipo) {
        return motoDAO.obtenerMotoBasePorTipo(tipo);
    }

    public boolean duplicarMotoPorTipo(
            TipoMoto tipoMoto,
            TipoColorMoto nuevoColor,
            String nuevaMarca,
            int cantidad
    ) {
        if (tipoMoto == null) {
            System.err.println("El tipo de moto no puede ser nulo.");
            return false;
        }

        if (cantidad <= 0) {
            System.err.println("La cantidad debe ser un número positivo mayor que 0.");
            return false;
        }

        return motoDAO.duplicarMotoPorTipo(tipoMoto, nuevoColor, nuevaMarca, cantidad);
    }

}
