package Model.Entities;

import Model.Constants.EstadoMoto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private String id;
    private String idUsuario;
    private LocalDate fechaCreacion;
    private List<ItemCarrito> items;
    private BigDecimal total;

    public Carrito() {
        this.items = new ArrayList<>();
        this.fechaCreacion = LocalDate.now();
        this.total = BigDecimal.ZERO;
    }

    public Carrito(String idUsuario) {
        this.idUsuario = idUsuario;
        this.items = new ArrayList<>();
        this.fechaCreacion = LocalDate.now();
        this.total = BigDecimal.ZERO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void agregarItem(ItemCarrito item) {
        this.items.add(item);
        recalcularTotal();
    }

    public void eliminarItem(String idItem) {
        items.removeIf(item -> item.getId().equals(idItem));
        recalcularTotal();
    }

    public void recalcularTotal() {
        BigDecimal nuevoTotal = BigDecimal.ZERO;
        for (ItemCarrito item : items) {
            if (item.getSubtotal() != null) {
                nuevoTotal = nuevoTotal.add(item.getSubtotal());
            }
        }
        this.total = nuevoTotal;
    }
    
   public Carrito ontenerSoloEnCarrito(){
        Carrito carritoParaVenta = new Carrito(this.idUsuario);
    for (ItemCarrito item : this.items) {
        if (item.getVehiculo() != null 
            && item.getVehiculo().getEstado() == EstadoMoto.EN_CARRITO) {
            carritoParaVenta.agregarItem(item);
        }
    }
    return carritoParaVenta;
   }
    
     public Carrito obtenerSoloDisponibles() {
    Carrito carritoDisponible = new Carrito(this.idUsuario);
    for (ItemCarrito item : this.items) {
        if (item.getVehiculo() != null 
            && item.getVehiculo().getEstado() == EstadoMoto.DISPONIBLE) {
            carritoDisponible.agregarItem(item);
        }
    }
    return carritoDisponible;
}
     
     
     

    @Override
    public String toString() {
        return "Carrito{" +
                "id='" + id + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", total=" + total +
                ", items=" + items +
                '}';
    }
    
}
