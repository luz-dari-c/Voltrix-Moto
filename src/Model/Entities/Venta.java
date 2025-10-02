package Model.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Venta {
    private static final AtomicInteger contador = new AtomicInteger(1);

    private String idVenta;
    private LocalDateTime fechaVenta;
    private List<ItemCarrito> items;
    private BigDecimal total;

    public Venta() {
        this.idVenta = generarIdVenta();
        this.fechaVenta = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.total = BigDecimal.ZERO;
    }

    private String generarIdVenta() {
        return "V" + String.format("%07d", contador.getAndIncrement());
    }

    public String getIdVenta() {
        return idVenta;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void agregarItem(ItemCarrito item) {
        this.items.add(item);
        this.total = this.total.add(item.getSubtotal());
    }
}
