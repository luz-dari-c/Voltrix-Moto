package Model.Entities;

import Model.Constants.MedidaLlanta;
import Model.Constants.UbicacionLlanta;
import Model.Constants.MaterialLlanta;
import Utilidades.GeneradorDeIdPartes;

public class LlantaTrasera extends Llanta {

    private String idLlantaTrasera;

    public LlantaTrasera(MedidaLlanta medida, String marca, String modelo, MaterialLlanta material) {
        super(medida, UbicacionLlanta.DELANTERA, marca, modelo, material);
        this.idLlantaTrasera = GeneradorDeIdPartes.generarId("LLT");
    }

    public String getIdLlantaTrasera() {
        return idLlantaTrasera;
    }

    public void setIdLlantaTrasera(String idLlantaTrasera) {
        this.idLlantaTrasera = idLlantaTrasera;
    }

}
