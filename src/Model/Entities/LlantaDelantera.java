package Model.Entities;

import Model.Constants.MedidaLlanta;
import Model.Constants.UbicacionLlanta;
import Model.Constants.MaterialLlanta;
import Utilidades.GeneradorDeIdPartes;

public class LlantaDelantera extends Llanta {

    private String idLlantaDelantera;

    public LlantaDelantera(MedidaLlanta medida, String marca, String modelo, MaterialLlanta material) {
        super(medida, UbicacionLlanta.DELANTERA, marca, modelo, material);
        this.idLlantaDelantera = GeneradorDeIdPartes.generarId("LLD");
    }

    public String getIdLlantaDelantera() {
        return idLlantaDelantera;
    }

    public void setIdLlantaDelantera(String idLlantaDelantera) {
        this.idLlantaDelantera = idLlantaDelantera;
    }

}
