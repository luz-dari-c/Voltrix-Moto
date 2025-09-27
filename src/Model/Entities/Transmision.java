
package Model.Entities;

import Model.Constants.TipoTransmision;
import Model.Constants.TipoVelocidades;


public class Transmision {
    
    private String idTransmision;
    private TipoTransmision tipoTransmision;
    private TipoVelocidades velocidades;

    public Transmision(String idTransmision, TipoTransmision tipoTransmision, TipoVelocidades velocidades) {
        this.idTransmision = idTransmision;
        this.tipoTransmision = tipoTransmision;
        this.velocidades = velocidades;
    }

    public String getIdTransmision() {
        return idTransmision;
    }

    public void setIdTransmision(String idTransmision) {
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
