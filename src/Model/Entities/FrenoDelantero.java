package Model.Entities;

import Model.Constants.UbicacionFreno;
import Model.Constants.MaterialFreno;
import Utilidades.GeneradorDeIdPartes;

public class FrenoDelantero extends Freno {

    private String idFrenoDelantero;

    public FrenoDelantero(String marca, String modelo, MaterialFreno material) {
        super(UbicacionFreno.DELANTERO, marca, modelo, material);
        this.idFrenoDelantero = GeneradorDeIdPartes.generarId("FRD");
    }

    public String getIdFrenoDelantero() {
        return idFrenoDelantero;
    }

    public void setIdFrenoDelantero(String idFrenoDelantero) {
        this.idFrenoDelantero = idFrenoDelantero;
    }

}
