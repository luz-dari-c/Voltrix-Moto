package Model.Entities;

import java.math.BigDecimal;


public class ItemCarrito {

 
    private Moto vehiculo;
    private String id;
    private String usuarioId; 
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public ItemCarrito() {
    }

    public ItemCarrito(Moto vehiculo, String usuarioId, String id, int cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.vehiculo = vehiculo;
        this.usuarioId = usuarioId;
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }
    
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }


    public Moto getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Moto vehiculo) {
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

