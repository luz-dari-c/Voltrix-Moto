
package Model.Entities;

import Model.Constants.MedidaLlanta;
import Model.Constants.TipoLlanta;


public class Llanta {
    private int idLlanta;
    private MedidaLlanta medida;
    private TipoLlanta tipo;

    public Llanta(int idLlanta, MedidaLlanta medida, TipoLlanta tipo) {
        this.idLlanta = idLlanta;
        this.medida = medida;
        this.tipo = tipo;
    }

    public int getIdLlanta() {
        return idLlanta;
    }

    public void setIdLlanta(int idLlanta) {
        this.idLlanta = idLlanta;
    }

    public MedidaLlanta getMedida() {
        return medida;
    }

    public void setMedida(MedidaLlanta medida) {
        this.medida = medida;
    }

    public TipoLlanta getTipo() {
        return tipo;
    }

    public void setTipo(TipoLlanta tipo) {
        this.tipo = tipo;
    }
}
