package Model.Entities;

import java.math.BigDecimal;


public class ItemCarrito {

    private Vehiculo vehiculo;
    private String id;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public ItemCarrito(Vehiculo vehiculo, String id, int cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.vehiculo = vehiculo;
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    
    
}

