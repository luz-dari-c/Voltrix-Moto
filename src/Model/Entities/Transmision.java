
package Model.Entities;

import Model.Constants.TipoTransmision;
import Model.Constants.TipoVelocidades;


public class Transmision {
    
    private int idTransmision;
    private TipoTransmision tipoTransmision;
    private TipoVelocidades velocidades;

    public Transmision(TipoTransmision tipoTransmision, TipoVelocidades velocidades) {
        this.idTransmision = 0;
        this.tipoTransmision = tipoTransmision;
        this.velocidades = velocidades;
    }

    public int getIdTransmision() {
        return idTransmision;
    }

    public void setIdTransmision(int idTransmision) {
        this.idTransmision = idTransmision;
    }

    public TipoTransmision getTipoTransmision() {
        return tipoTransmision;
    }

    public void setTipoTransmision(TipoTransmision tipoTransmision) {
        this.tipoTransmision = tipoTransmision;
    }

    public TipoVelocidades getVelocidades() {
        return velocidades;
    }

    public void setVelocidades(TipoVelocidades velocidades) {
        this.velocidades = velocidades;
    }
    
    
    
    
    
}
