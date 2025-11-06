
package Model.Entities;


import Model.Entities.Usuario;
import Model.Entities.Carrito;

public class Sesion {
    private static Sesion instancia;

    private Usuario usuarioActual;
    private Carrito carritoActual;

    private Sesion() {}

    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public Carrito getCarritoActual() {
        return carritoActual;
    }

    public void setCarritoActual(Carrito carritoActual) {
        this.carritoActual = carritoActual;
    }

    public void cerrarSesion() {
        usuarioActual = null;
        carritoActual = null;
    }
}

    

