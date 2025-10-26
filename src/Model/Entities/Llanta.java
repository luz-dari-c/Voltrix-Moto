package Model.Entities;

import Model.Constants.MedidaLlanta;
import Model.Constants.UbicacionLlanta;
import Model.Constants.MaterialLlanta;

public class Llanta {

    private MedidaLlanta medida;
    private UbicacionLlanta ubicacion;
    private String marca;
    private String modelo;
    private MaterialLlanta material;

    public Llanta(MedidaLlanta medida, UbicacionLlanta ubicacion, String marca, String modelo, MaterialLlanta material) {
        this.medida = medida;
        this.ubicacion = ubicacion;
        this.marca = marca;
        this.modelo = modelo;
        this.material = material;
    }

    public MedidaLlanta getMedida() {
        return medida;
    }

    public void setMedida(MedidaLlanta medida) {
        this.medida = medida;
    }

    public UbicacionLlanta getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionLlanta ubicacion) {
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

    public MaterialLlanta getMaterial() {
        return material;
    }

    public void setMaterial(MaterialLlanta material) {
        this.material = material;
    }

}
