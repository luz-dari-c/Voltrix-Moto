package Controller;

import DAO.MotoDAO;
import Model.Constants.*;
import Model.Entities.Moto;

import java.time.LocalDate;
import java.util.List;

public class MotoController {

    private final MotoDAO motoDAO;

    public MotoController() {
        this.motoDAO = MotoDAO.getInstancia();
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

    public boolean actualizarMoto(
            int idMoto,
            String nuevaMarca,
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
            TipoVelocidades nuevasVelocidades,
            EstadoMoto nuevoEstado
    ) {
        if (idMoto <= 0) {
            System.err.println("ID inválido al actualizar moto.");
            return false;
        }

        return motoDAO.actualizarMoto(
                idMoto,
                nuevaMarca,
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
                nuevasVelocidades,
                nuevoEstado
        );
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
}
