
package Model.Entities;

import Model.Constants.CapacidadAsiento;

public class Asiento {
    private int idAsiento;
    private String material;
    private CapacidadAsiento capacidad; 
    
    public Asiento( String material, CapacidadAsiento capacidad) {
        this.idAsiento = 0;
        this.material = material;
        this.capacidad = capacidad;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public CapacidadAsiento getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(CapacidadAsiento capacidad) {
        this.capacidad = capacidad;
    }
}
