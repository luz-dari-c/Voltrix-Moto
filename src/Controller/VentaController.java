
package Controller;

import DAO.FacturaDAO;
import DAO.VentaDAO;
import Model.Entities.Factura;
import Model.Entities.Venta;
import Model.Entities.ItemCarrito;
import Model.Entities.Usuario;
import java.io.File;
import java.util.List;
import java.math.BigDecimal;
import javax.swing.JOptionPane;


public class VentaController {

    private final VentaDAO ventaDAO;

    public VentaController() {
        this.ventaDAO = VentaDAO.getInstancia();
    }

 
    public boolean registrarVenta(Usuario usuario, List<ItemCarrito> itemsVendidos) {
        if (usuario == null) {
            System.err.println("Error: el usuario no puede ser nulo.");
            return false;
        }

        if (itemsVendidos == null || itemsVendidos.isEmpty()) {
            System.err.println("Error: la venta debe tener al menos un ítem.");
            return false;
        }

        Venta venta = new Venta(usuario, itemsVendidos);
        venta.setTotal(calcularTotal(itemsVendidos));

        boolean guardado = ventaDAO.guardarVenta(venta);
        if (guardado) {
            System.out.println("Venta registrada exitosamente para el usuario: " + usuario.getPrimerNombre() + " " + usuario.getPrimerApellido());
        } else {
            System.err.println("Error al guardar la venta.");
        }

        return guardado;
    }

   
    public BigDecimal calcularTotal(List<ItemCarrito> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemCarrito item : items) {
            if (item.getSubtotal() != null) {
                total = total.add(item.getSubtotal());
            }
        }
        return total;
    }

   
    public Venta buscarVentaPorId(String idVenta) {
        if (idVenta == null || idVenta.isEmpty()) {
            System.err.println("Error: ID de venta inválido.");
            return null;
        }
        return ventaDAO.buscarPorId(idVenta);
    }

   
    public boolean eliminarVenta(String idVenta) {
        if (idVenta == null || idVenta.isEmpty()) {
            System.err.println("Error: ID de venta inválido para eliminar.");
            return false;
        }
        return ventaDAO.eliminarVenta(idVenta);
    }

  
    public boolean actualizarVenta(Venta ventaActualizada) {
        if (ventaActualizada == null || ventaActualizada.getIdVenta() == null) {
            System.err.println("Error: Venta inválida para actualizar.");
            return false;
        }
        return ventaDAO.actualizarVenta(ventaActualizada);
    }

   
    public List<Venta> listarVentas() {
        return ventaDAO.cargarTodas();
    }
    
    
     public Venta getUltimaVenta() {
        List<Venta> ventas = ventaDAO.cargarTodas();
        if (ventas.isEmpty()) {
            return null;
        }
        return ventas.get(ventas.size() - 1);
    }
     
     public void generarFacturaPDF(Venta ventaGenerada, List<ItemCarrito> itemsVenta, Usuario usuario) {
    try {
        File carpetaFacturas = new File("src/Resources/Data/Facturas");
        if (!carpetaFacturas.exists()) carpetaFacturas.mkdirs();

        String nombreArchivo = "Factura_" + ventaGenerada.getIdVenta() + ".pdf";
        File archivoPDF = new File(carpetaFacturas, nombreArchivo);
        String rutaPDF = archivoPDF.getAbsolutePath();

        Utilidades.GeneradorFacturasPDF.generarFacturaPDF(ventaGenerada, itemsVenta, rutaPDF);

        FacturaDAO facturaDAO = new FacturaDAO();
        Factura factura = new Factura(
                facturaDAO.cargarTodas().size() + 1,
                usuario.getCedula(),
                0,
                nombreArchivo,
                rutaPDF,
                java.time.LocalDateTime.now()
        );
        facturaDAO.guardarFactura(factura);
        java.awt.Desktop.getDesktop().open(archivoPDF);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al generar o abrir la factura: " + e.getMessage(),
                "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}

    
    


}
