package Controller;

import DAO.CarritoDAO;
import Model.Entities.Carrito;
import Model.Entities.ItemCarrito;
import java.math.BigDecimal;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;

    public CarritoController() {
        this.carritoDAO = CarritoDAO.getInstancia();
    }

    public boolean crearCarrito(String idUsuario) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            return false;
        }
        Carrito nuevoCarrito = new Carrito(idUsuario);
        return carritoDAO.guardarCarrito(nuevoCarrito);
    }

    public boolean agregarItem(String idCarrito, ItemCarrito item) {
        if (idCarrito == null || idCarrito.isEmpty() || item == null) {
            return false;
        }
        Carrito carrito = carritoDAO.buscarPorId(idCarrito);
        if (carrito == null) {
            return false;
        }
        carrito.agregarItem(item);
        carrito.recalcularTotal();
        return carritoDAO.actualizarCarrito(carrito);
    }

    public boolean eliminarItem(String idCarrito, String idItem) {
        if (idCarrito == null || idCarrito.isEmpty() || idItem == null || idItem.isEmpty()) {
            return false;
        }
        Carrito carrito = carritoDAO.buscarPorId(idCarrito);
        if (carrito == null) {
            return false;
        }
        carrito.eliminarItem(idItem);
        carrito.recalcularTotal();
        return carritoDAO.actualizarCarrito(carrito);
    }

    public boolean eliminarCarrito(String idCarrito) {
        if (idCarrito == null || idCarrito.isEmpty()) {
            return false;
        }
        return carritoDAO.eliminarCarrito(idCarrito);
    }

    public List<Carrito> listarCarritos() {
        return carritoDAO.cargarTodos();
    }

    public Carrito buscarCarritoPorId(String idCarrito) {
        if (idCarrito == null || idCarrito.isEmpty()) {
            return null;
        }
        return carritoDAO.buscarPorId(idCarrito);
    }

    public BigDecimal calcularTotal(String idCarrito) {
        Carrito carrito = buscarCarritoPorId(idCarrito);
        if (carrito == null) {
            return BigDecimal.ZERO;
        }
        carrito.recalcularTotal();
        carritoDAO.actualizarCarrito(carrito);
        return carrito.getTotal();
    }

    public boolean vaciarCarrito(String idCarrito) {
        Carrito carrito = buscarCarritoPorId(idCarrito);
        if (carrito == null) {
            return false;
        }
        carrito.getItems().clear();
        carrito.recalcularTotal();
        return carritoDAO.actualizarCarrito(carrito);
    }
}
