
package Model.Entities;

import java.time.LocalDate;


public class Factura {
    private String idFactura;
    private Venta venta;
    private LocalDate fecha;
    private double subtotal;
    private double impuestos;
    private double total;
}
