package Controller;

import DAO.MotoDAO;
import Model.Constants.*;
import Model.Entities.Moto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/*    public boolean actualizarMoto(
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
    */
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
    
  public boolean actualizarMoto(Moto motoSeleccionada){
      if (motoSeleccionada==null) {
          System.out.println("No hay ninguna moto para actualizar (Null)");
                         
      } else {
          motoDAO.actualizarMoto(motoSeleccionada);
          return true;
      }
      
      return false;
  } 
    
  

}
