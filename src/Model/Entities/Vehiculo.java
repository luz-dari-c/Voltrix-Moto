
package Model.Entities;


public class Vehiculo {
    private int idVehiculo;
    private String marca;
    private String modelo;
    private int año;
    private double precio;

    private Motor motor;
    private Chasis chasis;
    private Llanta llantaDelantera;
    private Llanta llantaTrasera;
    private Freno frenoDelantero;
    private Freno frenoTrasero;
    private Asiento asiento;

    public Vehiculo(int idVehiculo, String marca, String modelo, int año, double precio,
                    Motor motor, Chasis chasis, Llanta llantaDelantera, Llanta llantaTrasera,
                    Freno frenoDelantero, Freno frenoTrasero, Asiento asiento) {
        this.idVehiculo = idVehiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.precio = precio;
        this.motor = motor;
        this.chasis = chasis;
        this.llantaDelantera = llantaDelantera;
        this.llantaTrasera = llantaTrasera;
        this.frenoDelantero = frenoDelantero;
        this.frenoTrasero = frenoTrasero;
        this.asiento = asiento;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
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

    public int getAño() {
        return año;
    }

    public void setAño(int anio) {
        this.año = anio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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
}
