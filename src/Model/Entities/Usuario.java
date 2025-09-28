package Model.Entities;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String cedula;
    private String telefono;
    private String email;
    private String password;
    private String direccion;
    private List<String> motosCompradas;

    public Usuario() {
        this.motosCompradas = new ArrayList<>();
    }

    public Usuario(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, 
                  String cedula, String telefono, String email, String password, String direccion) {
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.motosCompradas = new ArrayList<>();
    }

    // Getters y Setters
    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<String> getMotosCompradas() {
        return motosCompradas;
    }

    public void setMotosCompradas(List<String> motosCompradas) {
        this.motosCompradas = motosCompradas;
    }

    public void agregarMotoComprada(String idMoto) {
        this.motosCompradas.add(idMoto);
    }

    public String getNombreCompleto() {
        return primerNombre + " " + (segundoNombre != null ? segundoNombre + " " : "") + 
               primerApellido + " " + (segundoApellido != null ? segundoApellido : "");
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "primerNombre='" + primerNombre + '\'' +
                ", segundoNombre='" + segundoNombre + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", cedula='" + cedula + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}