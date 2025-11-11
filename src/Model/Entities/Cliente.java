package Model.Entities;

import java.io.Serializable;
import java.util.Objects;


public class Cliente extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String numeroTarjeta;
    private String nombreTarjeta;
    private String fechaExpiracion;
    private String cvv;
    private boolean tarjetaGuardada;

    public Cliente() {
        super();
    }

    public Cliente(String primerNombre, String segundoNombre, String primerApellido,
                   String segundoApellido, String cedula, String email, String password,
                   String numeroTarjeta, String nombreTarjeta, String fechaExpiracion,
                   String cvv, boolean tarjetaGuardada) {
        super(primerNombre, segundoNombre, primerApellido, segundoApellido, cedula, email, password);
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTarjeta = nombreTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
        this.tarjetaGuardada = tarjetaGuardada;
    }

    // Getters y setters
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public boolean isTarjetaGuardada() {
        return tarjetaGuardada;
    }

    public void setTarjetaGuardada(boolean tarjetaGuardada) {
        this.tarjetaGuardada = tarjetaGuardada;
    }

   
    public String getNumeroTarjetaEnmascarado() {
        if (numeroTarjeta == null) return null;
        String digitsOnly = numeroTarjeta.replaceAll("\\s+", "");
        int len = digitsOnly.length();
        if (len <= 4) return digitsOnly;
        String last4 = digitsOnly.substring(len - 4);
        return "**** **** **** " + last4;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cedula=" + getCedula() +
                ", nombre=" + getPrimerNombre() + " " + getPrimerApellido() +
                ", tarjetaGuardada=" + tarjetaGuardada +
                ", numeroTarjetaEnmascarado=" + getNumeroTarjetaEnmascarado() +
                '}';
    }

    // equals y hashCode basados en la cédula (identificador único)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cliente cliente = (Cliente) o;
        return Objects.equals(getCedula(), cliente.getCedula());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCedula());
    }
}
