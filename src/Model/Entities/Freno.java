package Model.Entities;

import Model.Constants.UbicacionFreno;
import Model.Constants.MaterialFreno;

public class Freno {

    private UbicacionFreno ubicacion;
    private String marca;
    private String modelo;
    private MaterialFreno material;

    public Freno(UbicacionFreno ubicacion, String marca, String modelo, MaterialFreno material) {
        this.ubicacion = ubicacion;
        this.marca = marca;
        this.modelo = modelo;
        this.material = material;
    }

    public UbicacionFreno getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionFreno ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public MaterialFreno getMaterial() {
        return material;
    }

    public void setMaterial(MaterialFreno material) {
        this.material = material;
    }

 
}
