package Model.Entities;

import Model.Constants.TipoColorMoto;
import Model.Constants.TipoMoto;
import java.time.LocalDate;

public class Moto {

    private int idMoto;
    private String placa;
    private String marca;
    private String modelo;
    private LocalDate fechaIngreso; 
    private double precio;

    private TipoMoto tipoMoto;
    private TipoColorMoto tipoColorMoto;

    private boolean tieneParrilla;
    private boolean tieneMaletero;

    private PartesMoto partesMoto;

    public Moto(String marca, String modelo, LocalDate fechaIngreso, double precio,
                PartesMoto partesMoto, TipoMoto tipoMoto, TipoColorMoto tipoColorMoto,
                boolean tieneParrilla, boolean tieneMaletero) {

        this.idMoto = 0;
        this.placa = Utilidades.GeneradorDePlaca.generarPlaca();
        this.marca = marca;
        this.modelo = modelo;
        this.fechaIngreso = fechaIngreso; 
        this.precio = precio;
        this.partesMoto = partesMoto;
        this.tipoMoto = tipoMoto;
        this.tipoColorMoto = tipoColorMoto;
        this.tieneParrilla = tieneParrilla;
        this.tieneMaletero = tieneMaletero;
    }

    public int getIdMoto() {
        return idMoto;
    }

    public void setIdMoto(int idMoto) {
        this.idMoto = idMoto;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    // ✅ Getters y setters actualizados para fechaIngreso
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public TipoMoto getTipoMoto() {
        return tipoMoto;
    }

    public void setTipoMoto(TipoMoto tipoMoto) {
        this.tipoMoto = tipoMoto;
    }

    public TipoColorMoto getTipoColorMoto() {
        return tipoColorMoto;
    }

    public void setTipoColorMoto(TipoColorMoto tipoColorMoto) {
        this.tipoColorMoto = tipoColorMoto;
    }

    public boolean isTieneParrilla() {
        return tieneParrilla;
    }

    public void setTieneParrilla(boolean tieneParrilla) {
        this.tieneParrilla = tieneParrilla;
    }

    public boolean isTieneMaletero() {
        return tieneMaletero;
    }

    public void setTieneMaletero(boolean tieneMaletero) {
        this.tieneMaletero = tieneMaletero;
    }

    public PartesMoto getPartesMoto() {
        return partesMoto;
    }

    public void setPartesMoto(PartesMoto partesMoto) {
        this.partesMoto = partesMoto;
    }
}
