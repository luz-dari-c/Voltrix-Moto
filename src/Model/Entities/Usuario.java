package Model.Entities;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String cedula;
    // Eliminados: telefono y direccion
    private String email;
    private String password;
    private List<String> motosCompradas;

    public Usuario() {
        this.motosCompradas = new ArrayList<>();
    }

    // Constructor final sin 'telefono' ni 'direccion'
    public Usuario(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String cedula, String email, String password) {
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.cedula = cedula;
        this.email = email;
        this.password = password;
        this.motosCompradas = new ArrayList<>();
    }

    // Getters y Setters
    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre; }
    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; }
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<String> getMotosCompradas() { return motosCompradas; }
    public void setMotosCompradas(List<String> motosCompradas) { this.motosCompradas = motosCompradas; }
}