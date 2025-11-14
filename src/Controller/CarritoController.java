package Controller;

import DAO.CarritoDAO;
import Model.Entities.Carrito;
import Model.Entities.ItemCarrito;
import Model.Entities.Sesion;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarritoController {

    private static CarritoController instancia;

    private final CarritoDAO carritoDAO;

    private CarritoController() {
        this.carritoDAO = CarritoDAO.getInstancia();
    }

    public static CarritoController getInstancia() {
        if (instancia == null) {
            instancia = new CarritoController();
        }
        return instancia;
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
    
    
    
public Carrito cargarCarritoDeUsuario(String idUsuario) {
    if (idUsuario == null || idUsuario.isEmpty()) {
        System.err.println("ID de usuario inválido");
        return null;
    }

    List<Carrito> carritos = carritoDAO.cargarTodos();

    Carrito carritoUsuario = null;

    for (Carrito c : carritos) {
        if (idUsuario.equals(c.getIdUsuario())) {
            carritoUsuario = c;
            break;
        }
    }

    if (carritoUsuario == null) {
        Carrito nuevo = new Carrito();
        nuevo.setIdUsuario(idUsuario);
        nuevo.setFechaCreacion(LocalDate.now());

        boolean guardado = carritoDAO.guardarCarrito(nuevo);

        if (!guardado) {
            System.err.println("Error al crear carrito para usuario " + idUsuario);
            return null;
        }

        carritos = carritoDAO.cargarTodos();
        for (Carrito c : carritos) {
            if (idUsuario.equals(c.getIdUsuario())) {
                carritoUsuario = c;
                break;
            }
        }
    }

    ItemCarritoController itemController = ItemCarritoController.getInstancia();
    List<ItemCarrito> items = itemController.obtenerItemsPorUsuario(idUsuario);

    carritoUsuario.setItems(items != null ? items : new ArrayList<>());

    Sesion sesion = Sesion.getInstancia();
    sesion.setCarritoActual(carritoUsuario);

    return carritoUsuario;
}


}
