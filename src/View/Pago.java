package View;

import Controller.ItemCarritoController;
import Controller.MotoController;
import Controller.VentaController;
import DAO.FacturaDAO;
import Model.Constants.EstadoMoto;
import Model.Entities.Factura;
import Model.Entities.ItemCarrito;
import java.text.DecimalFormat;
import Model.Entities.Moto;
import Model.Entities.Sesion;
import Model.Entities.Usuario;
import Model.Entities.Venta;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Pago extends javax.swing.JFrame {

    private Model.Entities.Carrito carrito;
    private Moto motoSeleccionada;
    private final MotoController motoController = new MotoController();
    private final VentaController ventaController = new VentaController();

    public Pago() {
        initComponents();
        configurarFormatosAutomáticos();
        this.setLocationRelativeTo(null);

    }

    public Pago(Model.Entities.Carrito carrito) {
        initComponents();
        configurarFormatosAutomáticos();
        this.carrito = carrito;
        this.setLocationRelativeTo(null);
        DatosPago.setVisible(false);

        DecimalFormat formato = new DecimalFormat("#,###");
        String precioFormateado = formato.format(carrito.getTotal());
        labelPrecio.setText("$" + precioFormateado);
    }

    public Pago(Moto moto) {
        initComponents();
        configurarFormatosAutomáticos();
        this.motoSeleccionada = moto;
        DatosPago.setVisible(false);
        this.setLocationRelativeTo(null);

        DecimalFormat formato = new DecimalFormat("#,###");
        String precioFormateado = formato.format(moto.getPrecio());

        labelPrecio.setText(String.valueOf(precioFormateado));

    }

    private void configurarFormatosAutomáticos() {
        txtNumeroTarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = txtNumeroTarjeta.getText().replaceAll("[^\\d]", "");
                StringBuilder nuevoTexto = new StringBuilder();

                for (int i = 0; i < texto.length(); i++) {
                    if (i > 0 && i % 4 == 0) {
                        nuevoTexto.append("-");
                    }
                    nuevoTexto.append(texto.charAt(i));
                }

                String formateado = nuevoTexto.toString();
                if (!formateado.equals(txtNumeroTarjeta.getText())) {
                    txtNumeroTarjeta.setText(formateado);
                }
            }
        });

        txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = txtFecha.getText().replaceAll("[^\\d]", "");

                if (texto.length() > 4) {
                    texto = texto.substring(0, 4);
                }

                StringBuilder nuevoTexto = new StringBuilder();
                for (int i = 0; i < texto.length(); i++) {
                    if (i == 2) {
                        nuevoTexto.append("/");
                    }
                    nuevoTexto.append(texto.charAt(i));
                }

                String formateado = nuevoTexto.toString();
                if (!formateado.equals(txtFecha.getText())) {
                    txtFecha.setText(formateado);
                }
            }
        });

        txtCVV.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = txtCVV.getText().replaceAll("[^\\d]", "");
                if (texto.length() > 4) {
                    texto = texto.substring(0, 4);
                }
                if (!texto.equals(txtCVV.getText())) {
                    txtCVV.setText(texto);
                }
            }
        });

        txtNumeroTarjeta.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (str == null) {
                    return;
                }
                String textoActual = getText(0, getLength()).replaceAll("[^\\d]", "") + str.replaceAll("[^\\d]", "");
                if (textoActual.length() <= 16) {
                    super.insertString(offs, str, a);
                }
            }
        });

    }

    private boolean validarDatosPago(String nombre, String numeroTarjeta, String fecha, String cvv) {
        Sesion sesion = Sesion.getInstancia();
        Usuario usuario = sesion.getUsuarioActual();
        String nombreCompleto = usuario.getPrimerNombre() + " " + usuario.getPrimerApellido();
        if (!nombre.equalsIgnoreCase(nombreCompleto)) {
            JOptionPane.showMessageDialog(this, "El nombre no coincide con el registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String numeroTarjetaSoloDigitos = numeroTarjeta.replaceAll("\\D", "");

        if (!numeroTarjetaSoloDigitos.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(this, "Número de tarjeta inválido (debe tener 16 dígitos).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!fecha.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use MM/yy.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!cvv.matches("\\d{3,4}")) {
            JOptionPane.showMessageDialog(this, "CVV inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pagos = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        labelRegresar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        PanelCompra = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        rbCredito = new javax.swing.JRadioButton();
        rbDebito = new javax.swing.JRadioButton();
        DatosPago = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtNumeroTarjeta = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        txtCVV = new javax.swing.JTextField();
        FinalizarCompra = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        labelPrecio = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelRegresar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        labelRegresar.setForeground(new java.awt.Color(0, 0, 0));
        labelRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-back-34.png"))); // NOI18N
        labelRegresar.setText("Regresar");
        labelRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelRegresarMouseClicked(evt);
            }
        });
        jPanel1.add(labelRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Gemini_Generated_Image_puwo0jpuwo0jpuwo-removebg-preview (1).png"))); // NOI18N
        jLabel3.setText("jLabel3");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, 690, 570));

        PanelCompra.setBackground(new java.awt.Color(0, 153, 255));
        PanelCompra.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel9.setText("Elije tu metodo de pago:");
        PanelCompra.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, -1, -1));

        pagos.add(rbCredito);
        rbCredito.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        rbCredito.setText("Tarjeta de credito");
        rbCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCreditoActionPerformed(evt);
            }
        });
        PanelCompra.add(rbCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, 30));

        pagos.add(rbDebito);
        rbDebito.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        rbDebito.setText("Tarjeta de debito");
        rbDebito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbDebitoActionPerformed(evt);
            }
        });
        PanelCompra.add(rbDebito, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, 30));

        DatosPago.setBackground(new java.awt.Color(0, 153, 204));
        DatosPago.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel10.setText("Nombre del propietario/a:");
        DatosPago.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, -1, 30));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel12.setText("Numero de la tarjeta:");
        DatosPago.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, -1, 20));

        txtNombre.setBackground(new java.awt.Color(0, 102, 153));
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        DatosPago.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 230, -1));

        txtNumeroTarjeta.setBackground(new java.awt.Color(0, 102, 153));
        txtNumeroTarjeta.setForeground(new java.awt.Color(255, 255, 255));
        DatosPago.add(txtNumeroTarjeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 260, 30));

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel13.setText("CVV:");
        DatosPago.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel11.setText("Fecha:");
        DatosPago.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        txtFecha.setBackground(new java.awt.Color(0, 102, 153));
        txtFecha.setForeground(new java.awt.Color(255, 255, 255));
        DatosPago.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 104, 340, -1));

        txtCVV.setBackground(new java.awt.Color(0, 102, 153));
        txtCVV.setForeground(new java.awt.Color(255, 255, 255));
        txtCVV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCVVActionPerformed(evt);
            }
        });
        DatosPago.add(txtCVV, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 340, 30));

        FinalizarCompra.setBackground(new java.awt.Color(255, 255, 255));
        FinalizarCompra.setForeground(new java.awt.Color(0, 0, 0));
        FinalizarCompra.setText("Finalizar el pago");
        FinalizarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalizarCompraActionPerformed(evt);
            }
        });
        DatosPago.add(FinalizarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 140, 40));

        jLabel5.setForeground(new java.awt.Color(102, 0, 0));
        jLabel5.setText("Cancelar");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        DatosPago.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, -1, -1));

        PanelCompra.add(DatosPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 410, 310));

        jPanel2.setBackground(new java.awt.Color(0, 102, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Precio total:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, -1));

        labelPrecio.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 33)); // NOI18N
        labelPrecio.setForeground(new java.awt.Color(255, 255, 255));
        labelPrecio.setText("Total $66.000");
        jPanel2.add(labelPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 410, -1));

        PanelCompra.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 420, 110));

        jPanel1.add(PanelCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 500, 660));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("ADQUIERE TU MOTO ¡YA!");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/unnamed-removebg-preview (1).png"))); // NOI18N
        jLabel14.setText("jLabel12");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, -20, 120, 160));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Tu nueva forma de moverte comienza ahora.");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 130, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void FinalizarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinalizarCompraActionPerformed

        String nombre = txtNombre.getText().trim();
        String numeroTarjeta = txtNumeroTarjeta.getText().trim();
        String fecha = txtFecha.getText().trim();
        String cvv = txtCVV.getText().trim();

        if (!validarDatosPago(nombre, numeroTarjeta, fecha, cvv)) {
            return;
        }

        Sesion sesion = Sesion.getInstancia();
        Usuario usuario = sesion.getUsuarioActual();
        boolean exitoVenta = false;
        List<ItemCarrito> itemsVenta = null;

        if (motoSeleccionada != null) {
            ItemCarrito item = new ItemCarrito(
                    motoSeleccionada,
                    usuario.getCedula(),
                    null,
                    1,
                    new BigDecimal(motoSeleccionada.getPrecio()),
                    new BigDecimal(motoSeleccionada.getPrecio())
            );
            itemsVenta = List.of(item);
            exitoVenta = ventaController.registrarVenta(usuario, itemsVenta);

            if (exitoVenta) {
                motoSeleccionada.setEstado(EstadoMoto.VENDIDO);
                motoController.eliminarMoto(motoSeleccionada.getIdMoto());
                motoController.guardarMoto(motoSeleccionada);
            }

        } else if (carrito != null) {
            itemsVenta = new ArrayList<>(carrito.getItems());

            if (itemsVenta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El carrito está vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            exitoVenta = ventaController.registrarVenta(usuario, itemsVenta);

            if (exitoVenta) {
                for (ItemCarrito item : itemsVenta) {
                    Moto moto = item.getVehiculo();
                    moto.setEstado(EstadoMoto.VENDIDO);
                    motoController.eliminarMoto(moto.getIdMoto());
                    motoController.guardarMoto(moto);
                }

                ItemCarritoController itemController = new ItemCarritoController();
                boolean vaciado = itemController.limpiarCarritoPorUsuario(usuario.getCedula());

                if (vaciado) {
                    System.out.println("Carrito vaciado correctamente después de la compra.");
                    carrito.getItems().clear();
                } else {
                    System.err.println("Error al vaciar el carrito después de la compra.");
                    JOptionPane.showMessageDialog(this,
                            "Compra realizada pero hubo un error al vaciar el carrito. "
                            + "Por favor, contacte con soporte.",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        if (exitoVenta && itemsVenta != null) {
            JOptionPane.showMessageDialog(this, "¡Compra realizada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            Venta ventaGenerada = ventaController.getUltimaVenta();
            if (ventaGenerada != null) {
                File carpetaFacturas = new File("src/Resources/Data/Facturas");
                if (!carpetaFacturas.exists()) {
                    carpetaFacturas.mkdirs();
                }

                String nombreArchivo = "Factura_" + ventaGenerada.getIdVenta() + ".pdf";
                File archivoPDF = new File(carpetaFacturas, nombreArchivo);
                String rutaPDF = archivoPDF.getAbsolutePath();

                try {
                    Utilidades.GeneradorFacturasPDF.generarFacturaPDF(ventaGenerada, itemsVenta, rutaPDF);
                } catch (Exception e) {
                    System.err.println("Error al generar PDF: " + e.getMessage());
                    JOptionPane.showMessageDialog(this,
                            "Compra exitosa pero error al generar factura PDF: " + e.getMessage(),
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }

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

                JOptionPane.showMessageDialog(this,
                        "Factura generada exitosamente.\nRuta: " + rutaPDF,
                        "Factura PDF", JOptionPane.INFORMATION_MESSAGE);

                try {
                    java.awt.Desktop.getDesktop().open(archivoPDF);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "No se pudo abrir el archivo PDF. Verifica que tengas un visor de PDF instalado.");
                }
            }

            Store jv = new Store();
            jv.setVisible(true);
            this.dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_FinalizarCompraActionPerformed

    private void txtCVVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCVVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCVVActionPerformed

    private void rbCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCreditoActionPerformed
        DatosPago.setVisible(true);
    }//GEN-LAST:event_rbCreditoActionPerformed

    private void rbDebitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDebitoActionPerformed
        DatosPago.setVisible(true);
    }//GEN-LAST:event_rbDebitoActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro/a que desea cancelar la compra?\n\n"
                + "Se le devolverá a la pantalla principal y no se guardarán sus datos de compra.",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            System.out.println("Compra cancelada. Volviendo a la pantalla principal...");
            this.dispose();
            Store st = new Store();
            st.setVisible(true);
        } else {
            System.out.println("El usuario decidió continuar con la compra.");
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void labelRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelRegresarMouseClicked

        Store cat = new Store();
        cat.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_labelRegresarMouseClicked

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
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pago().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DatosPago;
    private javax.swing.JButton FinalizarCompra;
    private javax.swing.JPanel PanelCompra;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelPrecio;
    private javax.swing.JLabel labelRegresar;
    private javax.swing.ButtonGroup pagos;
    private javax.swing.JRadioButton rbCredito;
    private javax.swing.JRadioButton rbDebito;
    private javax.swing.JTextField txtCVV;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumeroTarjeta;
    // End of variables declaration//GEN-END:variables
}
