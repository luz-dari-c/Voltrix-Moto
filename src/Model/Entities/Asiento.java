
package Model.Entities;

import Model.Constants.CapacidadAsiento;

public class Asiento {
    private int id;
    private String material;
    private CapacidadAsiento capacidad; 
    
    public Asiento(int id, String material, CapacidadAsiento capacidad) {
        this.id = id;
        this.material = material;
        this.capacidad = capacidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
