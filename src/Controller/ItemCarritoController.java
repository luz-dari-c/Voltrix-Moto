package Controller;

import DAO.ItemCarritoDAO;
import Model.Entities.ItemCarrito;
import Model.Entities.Moto;
import java.math.BigDecimal;
import java.util.List;

public class ItemCarritoController {

    private final ItemCarritoDAO itemCarritoDAO;

    public ItemCarritoController() {
        this.itemCarritoDAO = ItemCarritoDAO.getInstancia();
    }

    public boolean agregarItem(Moto vehiculo, int cantidad, BigDecimal precioUnitario) {
        if (vehiculo == null || cantidad <= 0 || precioUnitario == null) {
            System.err.println("Datos inválidos al agregar item al carrito.");
            return false;
        }

        List<ItemCarrito> items = itemCarritoDAO.cargarTodos();
        for (ItemCarrito item : items) {
            if (item.getVehiculo().getIdMoto() == vehiculo.getIdMoto()) {
                int nuevaCantidad = item.getCantidad() + cantidad;
                itemCarritoDAO.actualizarCantidad(item.getId(), nuevaCantidad);
                System.out.println("Cantidad actualizada para el vehículo: " + vehiculo.getModelo());
                return true;
            }
        }

        BigDecimal subtotal = precioUnitario.multiply(new BigDecimal(cantidad));
        ItemCarrito nuevoItem = new ItemCarrito(vehiculo, null, cantidad, precioUnitario, subtotal);
        return itemCarritoDAO.guardarItem(nuevoItem);
    }

    public boolean eliminarItem(String id) {
        if (id == null || id.isEmpty()) {
            System.err.println("ID inválido al intentar eliminar un item.");
            return false;
        }
        return itemCarritoDAO.eliminarItem(id);
    }

    public boolean actualizarCantidad(String id, int nuevaCantidad) {
        if (id == null || id.isEmpty() || nuevaCantidad <= 0) {
            System.err.println("Datos inválidos al actualizar cantidad.");
            return false;
        }
        return itemCarritoDAO.actualizarCantidad(id, nuevaCantidad);
    }

    public List<ItemCarrito> listarItems() {
        return itemCarritoDAO.cargarTodos();
    }

    public ItemCarrito buscarItemPorId(String id) {
        if (id == null || id.isEmpty()) {
            System.err.println("ID inválido al buscar item.");
            return null;
        }
        return itemCarritoDAO.buscarPorId(id);
    }

    public BigDecimal calcularTotal() {
        return itemCarritoDAO.calcularTotal();
    }

    public boolean limpiarCarrito() {
        return itemCarritoDAO.limpiarCarrito();
    }

    public int generarNuevoId() {
        List<ItemCarrito> items = itemCarritoDAO.cargarTodos();
        return itemCarritoDAO.generarNuevoId(items);
    }
}
