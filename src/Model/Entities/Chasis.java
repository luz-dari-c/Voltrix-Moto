
package Model.Entities;

import Model.Constants.MaterialChasis;
import Model.Constants.TipoChasis;
import Utilidades.GeneradorDeIdPartes;


public class Chasis {
    private String idChasis;
    private MaterialChasis material;
    private TipoChasis tipo;

    public Chasis( MaterialChasis material, TipoChasis tipo) {
        this.idChasis = GeneradorDeIdPartes.generarId("CHS");
        this.material = material;
        this.tipo = tipo;
    }

    public String getIdChasis() {
        return idChasis;
    }

    public void setIdChasis(String idChasis) {
        this.idChasis = idChasis;
    }

    public MaterialChasis getMaterial() {
        return material;
    }

    public void setMaterial(MaterialChasis material) {
        this.material = material;
    }

    public TipoChasis getTipo() {
        return tipo;
    }

    public void setTipo(TipoChasis tipo) {
        this.tipo = tipo;
    }
}
