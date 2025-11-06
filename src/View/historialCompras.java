package View;

import DAO.FacturaDAO;
import Model.Entities.Factura;
import Model.Entities.Usuario;
import java.awt.Desktop;
import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class historialCompras extends javax.swing.JFrame {

    private FacturaDAO facturaDAO;
    private Usuario usuarioActual;

    public historialCompras() {
        initComponents();
        this.setLocationRelativeTo(null);
        inicializarComponentes();
        cargarFacturasUsuario();
    }

    public historialCompras(Usuario usuario) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.usuarioActual = usuario;
        inicializarComponentes();
        cargarFacturasUsuario();
    }

    private void inicializarComponentes() {
        facturaDAO = new FacturaDAO();

        if (usuarioActual == null) {
            // Obtener usuario de la sesión (ajusta según tu implementación)
            // usuarioActual = Sesion.getInstancia().getUsuarioActual();
        }

        // Personalizar la tabla
        personalizarTabla();
    }

    private void personalizarTabla() {
        String[] columnNames = {"N° Factura", "Fecha", "Archivo", "Ver Factura"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {
                    return String.class;
                }
                return String.class;
            }
        };

        tablaFactura.setModel(model);

        tablaFactura.setRowHeight(30);
        tablaFactura.getTableHeader().setReorderingAllowed(false);

        tablaFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tablaFactura.rowAtPoint(evt.getPoint());
                int col = tablaFactura.columnAtPoint(evt.getPoint());

                if (row >= 0 && col == 3) {
                    abrirFactura(row);
                }
            }
        });
    }

    private void cargarFacturasUsuario() {
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(this,
                    "No hay usuario logueado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<Factura> facturas = facturaDAO.cargarTodas();
            List<Factura> facturasUsuario = filtrarFacturasPorUsuario(facturas, usuarioActual.getCedula());
            actualizarTabla(facturasUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar las facturas: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<Factura> filtrarFacturasPorUsuario(List<Factura> facturas, String cedulaUsuario) {
        List<Factura> facturasFiltradas = new java.util.ArrayList<>();
        for (Factura factura : facturas) {
            if (factura.getIdUsuario().equals(cedulaUsuario)) {
                facturasFiltradas.add(factura);
            }
        }
        return facturasFiltradas;
    }

    private void actualizarTabla(List<Factura> facturas) {
        DefaultTableModel model = (DefaultTableModel) tablaFactura.getModel();
        model.setRowCount(0);

        for (Factura factura : facturas) {
            Object[] rowData = {
                factura.getIdFactura(),
                formatearFecha(factura.getFechaFactura()),
                factura.getNombreArchivoPDF(),
                "📄 Abrir PDF"
            };
            model.addRow(rowData);
        }

        if (facturas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No tienes facturas registradas.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String formatearFecha(java.time.LocalDateTime fecha) {
        if (fecha == null) {
            return "N/A";
        }
        try {
            java.time.format.DateTimeFormatter formatter
                    = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return fecha.format(formatter);
        } catch (Exception e) {
            return fecha.toString();
        }
    }

    private void abrirFactura(int row) {
        try {
            String nombreArchivo = (String) tablaFactura.getValueAt(row, 2);
            List<Factura> facturas = facturaDAO.cargarTodas();

            Factura facturaSeleccionada = null;
            for (Factura factura : facturas) {
                if (factura.getNombreArchivoPDF().equals(nombreArchivo)) {
                    facturaSeleccionada = factura;
                    break;
                }
            }

            if (facturaSeleccionada != null) {
                File archivoPDF = new File(facturaSeleccionada.getRutaPDF());
                if (archivoPDF.exists()) {
                    Desktop.getDesktop().open(archivoPDF);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "El archivo PDF no se encuentra en la ruta:\n"
                            + facturaSeleccionada.getRutaPDF(),
                            "Archivo no encontrado",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al abrir el PDF: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarHistorial() {
        cargarFacturasUsuario();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaFactura = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        buttonVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1140, 720));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1140, 720));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaFactura.setBackground(new java.awt.Color(255, 255, 255));
        tablaFactura.setForeground(new java.awt.Color(0, 0, 0));
        tablaFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaFactura.setGridColor(new java.awt.Color(102, 102, 102));
        tablaFactura.setShowGrid(false);
        jScrollPane1.setViewportView(tablaFactura);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 1050, 520));

        jLabel1.setFont(new java.awt.Font("Segoe UI Historic", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Historial de compras");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 240, 50));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Si desea visualizar los detalles de su factura de doble click");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 410, -1));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonVolver.setBackground(new java.awt.Color(255, 255, 255));
        buttonVolver.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonVolver.setForeground(new java.awt.Color(0, 0, 0));
        buttonVolver.setText("Volver");
        buttonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVolverActionPerformed(evt);
            }
        });
        jPanel2.add(buttonVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 17, 120, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 680, 1140, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 740));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVolverActionPerformed

        Store st = new Store();
        st.setVisible(true);

        this.dispose();

    }//GEN-LAST:event_buttonVolverActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(historialCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(historialCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(historialCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(historialCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new historialCompras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaFactura;
    // End of variables declaration//GEN-END:variables
}
