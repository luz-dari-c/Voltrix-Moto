
package Model.Entities;


import java.time.LocalDateTime;

public class Factura {
    private int idFactura;
    private String idUsuario;
    private Integer idMoto; 
    private String nombreArchivoPDF;
    private String rutaPDF;
    private LocalDateTime fechaFactura;

    public Factura(int idFactura, String idUsuario, Integer idMoto, String nombreArchivoPDF, String rutaPDF, LocalDateTime fechaFactura) {
        this.idFactura = idFactura;
        this.idUsuario = idUsuario;
        this.idMoto = idMoto;
        this.nombreArchivoPDF = nombreArchivoPDF;
        this.rutaPDF = rutaPDF;
        this.fechaFactura = fechaFactura;
    }

    public int getIdFactura() { return idFactura; }
    public String getIdUsuario()
    { return idUsuario; }
    public int getIdMoto() { return idMoto; }
    public String getNombreArchivoPDF() { return nombreArchivoPDF; }
    public String getRutaPDF() { return rutaPDF; }
    public LocalDateTime getFechaFactura() { return fechaFactura; }
}

