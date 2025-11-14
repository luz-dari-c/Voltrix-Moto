package View;

import Controller.ItemCarritoController;
import Controller.MotoController;
import Model.Constants.EstadoMoto;
import Model.Entities.ItemCarrito;
import java.text.DecimalFormat;

import Model.Entities.Moto;
import Model.Entities.Sesion;
import Model.Entities.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Carrito extends javax.swing.JFrame {

    ItemCarritoController itemController = ItemCarritoController.getInstancia();

    public Carrito() {
        initComponents();
    }
    private Model.Entities.Carrito carrito;

    public Carrito(Model.Entities.Carrito carrito) {
        initComponents();
        this.carrito = carrito;
        this.setLocationRelativeTo(null);

        recargarItemsDesdeJson();
        cargarCarritoEnTabla();
        configurarItemMenu();

    }

    private void configurarItemMenu() {
        JMenuItem comprar = new JMenuItem("Comprar esta moto");
        JMenuItem eliminar = new JMenuItem("Eliminar del carrito");

        popMenuComprarOEliminar.add(comprar);
        popMenuComprarOEliminar.add(eliminar);

        tablaDeCarrito.setComponentPopupMenu(popMenuComprarOEliminar);

        comprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprarMotoSeleccionada();

            }
        });

        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarMotoSeleccionada();

            }
        });

    }

    private void comprarMotoSeleccionada() {
        int filaSeleccionada = tablaDeCarrito.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una moto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ItemCarrito itemSeleccionado = carrito.getItems().get(filaSeleccionada);
        Moto motoSeleccionada = itemSeleccionado.getVehiculo();

        if (motoSeleccionada.getEstado() != EstadoMoto.EN_CARRITO) {
            JOptionPane.showMessageDialog(this, "Esta moto no está disponible para compra.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres comprar " + motoSeleccionada.getModelo() + "?",
                "Confirmar compra individual",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                boolean eliminado = itemController.eliminarItem(itemSeleccionado.getId());

                if (eliminado) {
                    carrito.getItems().remove(filaSeleccionada);

                    Sesion sesion = Sesion.getInstancia();
                    sesion.setCarritoActual(carrito);

                    cargarCarritoEnTabla();

                    Pago pago = new Pago(motoSeleccionada);
                    this.dispose();
                    pago.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error al preparar la moto para compra.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                System.err.println("Error al procesar compra individual: " + e.getMessage());
                JOptionPane.showMessageDialog(this,
                        "Error al procesar la compra: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarMotoSeleccionada() {
        int filaSeleccionada = tablaDeCarrito.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una moto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ItemCarrito itemSeleccionado = carrito.getItems().get(filaSeleccionada);
        Moto motoSeleccionada = itemSeleccionado.getVehiculo();

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres eliminar la moto " + motoSeleccionada.getModelo() + " del carrito?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                motoSeleccionada.setEstado(EstadoMoto.DISPONIBLE);

                MotoController motoController = new MotoController();
                motoController.actualizarMoto(motoSeleccionada);

                boolean eliminado = itemController.eliminarItem(itemSeleccionado.getId());

                if (eliminado) {
                    carrito.getItems().remove(filaSeleccionada);

                    Sesion sesion = Sesion.getInstancia();
                    sesion.setCarritoActual(carrito);

                    cargarCarritoEnTabla();

                    JOptionPane.showMessageDialog(this,
                            "Moto eliminada del carrito correctamente.",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar la moto del carrito.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                System.err.println("Error al eliminar item del carrito: " + e.getMessage());
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar la moto del carrito: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarCarritoEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDeCarrito.getModel();
        modelo.setRowCount(0);

        if (carrito == null || carrito.getItems() == null || carrito.getItems().isEmpty()) {
            labelTotal.setText("Total: $0");
            return;
        }

        DecimalFormat formato = new DecimalFormat("#,###");
        BigDecimal total = BigDecimal.ZERO;

        for (ItemCarrito item : carrito.getItems()) {
            Moto moto = item.getVehiculo();
            String estado = moto.getEstado() != null ? moto.getEstado().toString() : "Desconocido";

            Object[] fila = {
                moto.getIdMoto(),
                moto.getModelo(),
                moto.getMarca(),
                moto.getPartesMoto().getMotor().getTipo(),
                moto.getCilindraje(),
                moto.getTipoColorMoto(),
                formato.format(item.getPrecioUnitario()),
                estado
            };

            modelo.addRow(fila);

            if ("EN_CARRITO".equalsIgnoreCase(estado)) {
                total = total.add(item.getPrecioUnitario());
            }
        }

        labelTotal.setText("Total: $" + formato.format(total));
    }

    private void recargarItemsDesdeJson() {
        try {
            Sesion sesion = Sesion.getInstancia();
            if (sesion.getUsuarioActual() == null || carrito == null) {
                return;
            }

            String idUsuario = sesion.getUsuarioActual().getCedula();
            List<ItemCarrito> items = itemController.obtenerItemsPorUsuario(idUsuario);

            for (ItemCarrito item : items) {
                Moto motoActualizada = itemController.buscarMotoPorId(item.getVehiculo().getIdMoto());
                if (motoActualizada != null) {
                    item.getVehiculo().setEstado(motoActualizada.getEstado());
                }
            }

            carrito.setItems(items);
        } catch (Exception e) {
            System.err.println("Error al recargar los ítems del carrito: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenuComprarOEliminar = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tablaCarrito = new javax.swing.JScrollPane();
        tablaDeCarrito = new javax.swing.JTable();
        labelTotal = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1140, 717));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Motos añadidas al carrito");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 240, -1));

        tablaCarrito.setBackground(new java.awt.Color(246, 246, 246));
        tablaCarrito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaCarrito.setForeground(new java.awt.Color(246, 246, 246));

        tablaDeCarrito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Modelo", "Marca", "Tipo motor", "Cilindraje", "Color Selecionado", "Precio", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDeCarrito.setRowHeight(25);
        tablaDeCarrito.setSelectionBackground(new java.awt.Color(51, 0, 0));
        tablaDeCarrito.setShowVerticalLines(true);
        tablaCarrito.setViewportView(tablaDeCarrito);
        if (tablaDeCarrito.getColumnModel().getColumnCount() > 0) {
            tablaDeCarrito.getColumnModel().getColumn(0).setResizable(false);
            tablaDeCarrito.getColumnModel().getColumn(1).setResizable(false);
            tablaDeCarrito.getColumnModel().getColumn(2).setResizable(false);
            tablaDeCarrito.getColumnModel().getColumn(3).setResizable(false);
            tablaDeCarrito.getColumnModel().getColumn(4).setResizable(false);
            tablaDeCarrito.getColumnModel().getColumn(5).setResizable(false);
            tablaDeCarrito.getColumnModel().getColumn(6).setResizable(false);
            tablaDeCarrito.getColumnModel().getColumn(7).setResizable(false);
        }

        jPanel1.add(tablaCarrito, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 1020, 490));

        labelTotal.setFont(new java.awt.Font("Franklin Gothic Demi", 0, 24)); // NOI18N
        labelTotal.setForeground(new java.awt.Color(102, 0, 0));
        labelTotal.setText(" 7.000.000");
        jPanel1.add(labelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 590, 390, 40));

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-back-30.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 40));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(0, 102, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ir a pagar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 130, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, 1140, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Model.Entities.Carrito carritoDisponible = carrito.ontenerSoloEnCarrito();

        if (carritoDisponible == null || carritoDisponible.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío o contiene motos no disponibles.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pago pago = new Pago(carritoDisponible);
        this.dispose();
        pago.setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Store st = new Store();
        st.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Carrito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Carrito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Carrito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Carrito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Carrito().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JPopupMenu popMenuComprarOEliminar;
    private javax.swing.JScrollPane tablaCarrito;
    private javax.swing.JTable tablaDeCarrito;
    // End of variables declaration//GEN-END:variables
}
