package Model.Entities;


import Model.Constants.EstadoVenta;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {

    private String idVenta;
    private Usuario usuario; 
    private LocalDate fechaVenta;
    private List<ItemCarrito> itemsVendidos;
    private BigDecimal total;
    private EstadoVenta estado;

    public Venta() {
        this.itemsVendidos = new ArrayList<>();
        this.fechaVenta = LocalDate.now();
        this.total = BigDecimal.ZERO;
        this.estado = EstadoVenta.PENDIENTE;
    }

    public Venta(Usuario usuario, List<ItemCarrito> itemsVendidos) {
        this.usuario = usuario;
        this.itemsVendidos = itemsVendidos != null ? itemsVendidos : new ArrayList<>();
        this.fechaVenta = LocalDate.now();
        this.estado = EstadoVenta.COMPLETADA;
        recalcularTotal();
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public List<ItemCarrito> getItemsVendidos() {
        return itemsVendidos;
    }

    public void setItemsVendidos(List<ItemCarrito> itemsVendidos) {
        this.itemsVendidos = itemsVendidos;
        recalcularTotal();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public void agregarItem(ItemCarrito item) {
        if (item != null) {
            this.itemsVendidos.add(item);
            recalcularTotal();
        }
    }

    public void recalcularTotal() {
        BigDecimal nuevoTotal = BigDecimal.ZERO;
        for (ItemCarrito item : itemsVendidos) {
            if (item.getSubtotal() != null) {
                nuevoTotal = nuevoTotal.add(item.getSubtotal());
            }
        }
        this.total = nuevoTotal;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta='" + idVenta + '\'' +
                ", usuario=" + (usuario != null ? usuario.getPrimerNombre() + " " + usuario.getPrimerApellido() : "Sin usuario") +
                ", fechaVenta=" + fechaVenta +
                ", total=" + total +
                ", estado=" + estado +
                ", items=" + itemsVendidos +
                '}';
    }
}
