
package Model.Entities;

import Model.Constants.CapacidadAsiento;
import Model.Constants.MaterialAsiento;
import Utilidades.GeneradorDeIdPartes;

public class Asiento {
    private String idAsiento;
    private MaterialAsiento material;
    private CapacidadAsiento capacidad; 
    
    public Asiento( MaterialAsiento material, CapacidadAsiento capacidad) {
        this.idAsiento = GeneradorDeIdPartes.generarId("AST");
        this.material = material;
        this.capacidad = capacidad;
    }

    public String getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(String idAsiento) {
        this.idAsiento = idAsiento;
    }

    public MaterialAsiento getMaterial() {
        return material;
    }

    public void setMaterial(MaterialAsiento material) {
        this.material = material;
    }

    public CapacidadAsiento getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(CapacidadAsiento capacidad) {
        this.capacidad = capacidad;
    }
}
