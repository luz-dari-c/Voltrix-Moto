package Model.Entities;

import Model.Constants.UbicacionFreno;
import Model.Constants.MaterialFreno;
import Utilidades.GeneradorDeIdPartes;

public class FrenoTrasero extends Freno {

    private String idFrenoTrasero;

    public FrenoTrasero(String marca, String modelo, MaterialFreno material) {
        super(UbicacionFreno.TRASERO, marca, modelo, material);
        this.idFrenoTrasero = GeneradorDeIdPartes.generarId("FRT");
    }

    public String getIdFrenoTrasero() {
        return idFrenoTrasero;
    }

    public void setIdFrenoTrasero(String idFrenoTrasero) {
        this.idFrenoTrasero = idFrenoTrasero;
    }

}
