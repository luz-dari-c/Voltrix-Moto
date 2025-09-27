
package Model.Entities;

import Model.Constants.MaterialChasis;
import Model.Constants.TipoChasis;


public class Chasis {
    private int idChasis;
    private MaterialChasis material;
    private TipoChasis tipo;

    public Chasis(int idChasis, MaterialChasis material, TipoChasis tipo) {
        this.idChasis = idChasis;
        this.material = material;
        this.tipo = tipo;
    }

    public int getIdChasis() {
        return idChasis;
    }

    public void setIdChasis(int idChasis) {
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
