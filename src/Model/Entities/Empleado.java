
package Model.Entities;

public class Empleado {
   
    private Usuario usuario;

    private String idEmpleado;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String edad;
    private String identificacion;
    private String correo;
    private String telefono;

    public Empleado(Usuario usuario, String idEmpleado, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String edad, String identificacion, String correo, String telefono) {
        this.usuario = usuario;
        this.idEmpleado = idEmpleado;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.edad = edad;
        this.identificacion = identificacion;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

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

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
@Override
public String toString() {
    return "Empleado{" +
            "idEmpleado='" + idEmpleado + '\'' +
            ", primerNombre='" + primerNombre + '\'' +
            ", segundoNombre='" + segundoNombre + '\'' +
            ", primerApellido='" + primerApellido + '\'' +
            ", segundoApellido='" + segundoApellido + '\'' +
            ", edad='" + edad + '\'' +
            ", identificacion='" + identificacion + '\'' +
            ", correo='" + correo + '\'' +
            ", telefono='" + telefono + '\'' +
            '}';
}

    
    
}
