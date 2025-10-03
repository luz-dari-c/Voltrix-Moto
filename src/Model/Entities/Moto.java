package Model.Entities;

import Model.Constants.TipoColorMoto;
import Model.Constants.TipoMoto;
import java.time.LocalDate;

public class Moto {

    private int idMoto;
    private String marca;
    private String modelo;
    private LocalDate fechaLanzamiento;
    private double precio;

    private TipoMoto tipoMoto;
    private TipoColorMoto tipoColorMoto;
    private boolean tieneParrilla;
    private boolean tieneMaletero;

    private Motor motor;
    private Chasis chasis;
    private Llanta llantaDelantera;
    private Llanta llantaTrasera;
    private Freno frenoDelantero;
    private Freno frenoTrasero;
    private Asiento asiento;
    private Transmision transmision;

    public Moto(String marca, String modelo, LocalDate fechaLanzamiento, double precio,
            Motor motor, Chasis chasis, Llanta llantaDelantera, Llanta llantaTrasera,
            Freno frenoDelantero, Freno frenoTrasero, Asiento asiento, Transmision transmision,
            TipoMoto tipoMoto, TipoColorMoto tipoColorMoto,
            boolean tieneParrilla, boolean tieneMaletero) {
        this.idMoto = 0;
        this.marca = marca;
        this.modelo = modelo;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precio = precio;
        this.motor = motor;
        this.chasis = chasis;
        this.llantaDelantera = llantaDelantera;
        this.llantaTrasera = llantaTrasera;
        this.frenoDelantero = frenoDelantero;
        this.frenoTrasero = frenoTrasero;
        this.asiento = asiento;
        this.transmision = transmision;
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

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
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

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public Chasis getChasis() {
        return chasis;
    }

    public void setChasis(Chasis chasis) {
        this.chasis = chasis;
    }

    public Llanta getLlantaDelantera() {
        return llantaDelantera;
    }

    public void setLlantaDelantera(Llanta llantaDelantera) {
        this.llantaDelantera = llantaDelantera;
    }

    public Llanta getLlantaTrasera() {
        return llantaTrasera;
    }

    public void setLlantaTrasera(Llanta llantaTrasera) {
        this.llantaTrasera = llantaTrasera;
    }

    public Freno getFrenoDelantero() {
        return frenoDelantero;
    }

    public void setFrenoDelantero(Freno frenoDelantero) {
        this.frenoDelantero = frenoDelantero;
    }

    public Freno getFrenoTrasero() {
        return frenoTrasero;
    }

    public void setFrenoTrasero(Freno frenoTrasero) {
        this.frenoTrasero = frenoTrasero;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public Transmision getTransmision() {
        return transmision;

    }

    public void setTransmision(Transmision transmision) {
        this.transmision = transmision;

    }

}
