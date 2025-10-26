
package Model.Entities;

import Model.Constants.TipoTransmision;
import Model.Constants.TipoVelocidades;
import Utilidades.GeneradorDeIdPartes;


public class Transmision {
    
    private String idTransmision;
    private TipoTransmision tipoTransmision;
    private TipoVelocidades velocidades;

    public Transmision(TipoTransmision tipoTransmision, TipoVelocidades velocidades) {
        this.idTransmision = GeneradorDeIdPartes.generarId("TRS");
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
