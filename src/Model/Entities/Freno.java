
package Model.Entities;

import Model.Constants.TipoFreno;
import Model.Constants.UbicacionFreno;


public class Freno {
    private int idFreno;
    private TipoFreno tipo;
    private UbicacionFreno ubicacion;

    public Freno(int idFreno, TipoFreno tipo, UbicacionFreno ubicacion) {
        this.idFreno = idFreno;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
    }

    public int getIdFreno() {
        return idFreno;
    }

    public void setIdFreno(int idFreno) {
        this.idFreno = idFreno;
    }

    public TipoFreno getTipo() {
        return tipo;
    }

    public void setTipo(TipoFreno tipo) {
        this.tipo = tipo;
    }

    public UbicacionFreno getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionFreno ubicacion) {
        this.ubicacion = ubicacion;
    }
}
