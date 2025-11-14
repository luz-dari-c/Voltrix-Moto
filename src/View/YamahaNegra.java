
package View;

import Controller.CarritoController;
import Controller.ItemCarritoController;
import Controller.MotoController;
import DAO.CarritoDAO;
import Model.Constants.EstadoMoto;
import Model.Constants.TipoColorMoto;
import static Model.Constants.TipoColorMoto.BLANCO;
import static Model.Constants.TipoColorMoto.NEGRO;
import static Model.Constants.TipoColorMoto.ROJO;
import static Model.Constants.TipoColorMoto.VERDE;
import Model.Constants.TipoMoto;
import Model.Entities.ItemCarrito;
import Model.Entities.Moto;
import Model.Entities.Sesion;
import Model.Entities.Usuario;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author gameV
 */
public class YamahaNegra extends javax.swing.JFrame {

    private Moto motoActual;

    /**
     * Creates new form YamahaNegra
     */
    public YamahaNegra() {
        initComponents();
        this.setLocationRelativeTo(null);
        MotoController controller = new MotoController();
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.SUPERSPORT);
        Map<TipoColorMoto, Integer> conteo = controller.contarPorColor(TipoMoto.SUPERSPORT);

        if (!motos.isEmpty()) {
            Moto moto = motos.get(0);
            motoActual = moto;

            TipoColorMoto color = moto.getTipoColorMoto();

            txtModelo.setText(moto.getModelo());
            marcatxt.setText(moto.getMarca());
            tipoTxt.setText(moto.getTipoMoto().toString());
            precioTxt.setText("$" + moto.getPrecio());
            estadotxt.setText(moto.getEstado().toString());
            CilindrajeTxt1.setText(moto.getPartesMoto().getMotor().getCilindrada() + " cc");
            Motortxt.setText(moto.getPartesMoto().getMotor().getTipo().toString());
            String añoIngreso = String.valueOf(moto.getFechaIngreso().getYear());
            txtAño1.setText(añoIngreso);

            String ruta = switch (color) {
                case ROJO ->
                    "/Images/Supersport-roja.png";
                case NEGRO ->
                    "/Images/yamaha-yzf-r6-2017-gris-oscuro-mate-45498b (2).png";
                case BLANCO ->
                    "/Images/Supersport-blanco.png";
                case VERDE ->
                    "/Images/Supersport-verde.png";
                default ->
                    "";
            };

            MotoFoto.setIcon(new ImageIcon(getClass().getResource(ruta)));

            int cantidadColor = conteo.getOrDefault(color, 0);
            jLabel14.setText("Disponibles color " + color + ": " + cantidadColor);
        } else {
            txtModelo.setText("No hay motos disponibles de ese tipo.");
        }

        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarColorMoto(TipoColorMoto.ROJO);
            }
        });
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarColorMoto(TipoColorMoto.VERDE);
            }
        });
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarColorMoto(TipoColorMoto.BLANCO);
            }
        });
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarColorMoto(TipoColorMoto.NEGRO);
            }
        });
    }

    private void cambiarColorMoto(TipoColorMoto colorSeleccionado) {
        MotoController controller = new MotoController();
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.SUPERSPORT);
        Map<TipoColorMoto, Integer> conteo = controller.contarPorColor(TipoMoto.SUPERSPORT);

        List<Moto> motosDelColor = motos.stream()
                .filter(m -> m.getTipoColorMoto() == colorSeleccionado)
                .toList();

        if (motosDelColor.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No hay motos disponibles de color " + colorSeleccionado + ".",
                    "Sin disponibilidad",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Moto moto = motosDelColor.get(0);
        motoActual = moto;

        txtModelo.setText(moto.getModelo());
        marcatxt.setText(moto.getMarca());
        tipoTxt.setText(moto.getTipoMoto().toString());
        precioTxt.setText("$" + moto.getPrecio());
        estadotxt.setText(moto.getEstado().toString());
        CilindrajeTxt1.setText(moto.getPartesMoto().getMotor().getCilindrada() + " cc");
        Motortxt.setText(moto.getPartesMoto().getMotor().getTipo().toString());
        String añoIngreso = String.valueOf(moto.getFechaIngreso().getYear());
        txtAño1.setText(añoIngreso);

        String ruta = switch (colorSeleccionado) {
            case ROJO ->
                "/Images/Supersport-roja.png";
            case NEGRO ->
                "/Images/yamaha-yzf-r6-2017-gris-oscuro-mate-45498b (2).png";
            case BLANCO ->
                "/Images/Supersport-blanco.png";
            case VERDE ->
                "/Images/Supersport-verde.png";
            default ->
                "";
        };

        MotoFoto.setIcon(new ImageIcon(getClass().getResource(ruta)));

        int cantidadColor = conteo.getOrDefault(colorSeleccionado, 0);
        jLabel14.setText("Disponibles color " + colorSeleccionado + ": " + cantidadColor);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        MotoFoto = new javax.swing.JLabel();
        jPanel2 = new VisualHelpers.RoundedPanel(20)
        ;
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Motortxt = new javax.swing.JLabel();
        txtModelo = new javax.swing.JLabel();
        txtAño1 = new javax.swing.JLabel();
        CilindrajeTxt1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        marcatxt = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        estadotxt = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tipoTxt = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        precioTxt = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Carrito6 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MotoFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/yamaha-yzf-r6-2017-gris-oscuro-mate-45498b (2).png"))); // NOI18N
        jPanel1.add(MotoFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 720, 420));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Tipo de Motor:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Modelo:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Año:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Cilindraje:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        Motortxt.setForeground(new java.awt.Color(0, 0, 0));
        Motortxt.setText("Motor");
        jPanel2.add(Motortxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 160, -1));

        txtModelo.setForeground(new java.awt.Color(0, 0, 0));
        txtModelo.setText("Modelo");
        jPanel2.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 160, -1));

        txtAño1.setForeground(new java.awt.Color(0, 0, 0));
        txtAño1.setText("Año");
        jPanel2.add(txtAño1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 160, -1));

        CilindrajeTxt1.setForeground(new java.awt.Color(0, 0, 0));
        CilindrajeTxt1.setText("Cilindraje");
        jPanel2.add(CilindrajeTxt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 160, -1));

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Marca");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, -1, -1));

        marcatxt.setForeground(new java.awt.Color(0, 0, 0));
        marcatxt.setText("Voltrix");
        jPanel2.add(marcatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 160, -1));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Estado:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, -1, -1));

        estadotxt.setForeground(new java.awt.Color(0, 0, 0));
        estadotxt.setText("estado");
        jPanel2.add(estadotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, 160, -1));

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Tipo de moto:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, -1, -1));

        tipoTxt.setForeground(new java.awt.Color(0, 0, 0));
        tipoTxt.setText("tipo");
        jPanel2.add(tipoTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, 160, -1));

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Colores disponibles:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, -1, -1));

        precioTxt.setForeground(new java.awt.Color(0, 0, 0));
        precioTxt.setText("precio");
        jPanel2.add(precioTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 160, -1));

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Precio");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, -1, -1));

        jPanel3.setBackground(new java.awt.Color(0, 204, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, 30, 30));

        jPanel4.setBackground(new java.awt.Color(51, 0, 0));
        jPanel4.setForeground(new java.awt.Color(51, 0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 60, 30, 30));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 60, 30, 30));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Comprar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 140, 120, 40));

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 30, 30));

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Cantidad");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 110, -1, -1));

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Cantidad disponible en ese color:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, -1, 20));

        Carrito6.setBackground(new java.awt.Color(51, 102, 0));
        Carrito6.setForeground(new java.awt.Color(255, 255, 255));
        Carrito6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-add-to-cart-25.png"))); // NOI18N
        Carrito6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Carrito6.setDefaultCapable(false);
        Carrito6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Carrito6ActionPerformed(evt);
            }
        });
        jPanel2.add(Carrito6, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 140, 70, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 480, 1010, 200));

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setText("Conoce más acerca de nuestros modelos.");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/unnamed-removebg-preview (1).png"))); // NOI18N
        jLabel12.setText("jLabel12");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 0, 130, 130));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-return-37.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 742));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        Store st = new Store();
        st.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Pago crt = new Pago(motoActual);
        this.dispose();
        crt.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void Carrito6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Carrito6ActionPerformed
       
        Moto motoSeleccionada = this.motoActual;
        Sesion sesion = Sesion.getInstancia();
        Usuario usuario = sesion.getUsuarioActual();
        Model.Entities.Carrito carrito = sesion.getCarritoActual();
        CarritoController carritoController = CarritoController.getInstancia();
        ItemCarritoController itemController = ItemCarritoController.getInstancia();
        MotoController motoController = MotoController.getInstancia();
                

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión para agregar al carrito.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (motoSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una moto antes de agregarla al carrito.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (carrito == null) {
            carritoController.crearCarrito(usuario.getCedula());
            carrito = carritoController.listarCarritos().stream()
                    .filter(c -> c.getIdUsuario().equals(usuario.getCedula()))
                    .reduce((primero, segundo) -> segundo)
                    .orElse(null);
            sesion.setCarritoActual(carrito);
        }

        BigDecimal precioUnitario = BigDecimal.valueOf(motoSeleccionada.getPrecio());
        int cantidad = 1;

        ItemCarrito item = new ItemCarrito(
                motoSeleccionada,
                usuario.getCedula(), 
                String.valueOf(motoSeleccionada.getIdMoto()), 
                cantidad,
                precioUnitario,
                precioUnitario.multiply(BigDecimal.valueOf(cantidad))
        );

        carrito.agregarItem(item);
        carrito.recalcularTotal();
        itemController.agregarItem(motoSeleccionada, cantidad, precioUnitario);

        CarritoDAO.getInstancia().actualizarCarrito(carrito);
        sesion.setCarritoActual(carrito);

        
        motoSeleccionada.setEstado(EstadoMoto.EN_CARRITO);
        motoController.actualizarMoto(motoSeleccionada);
        
        JOptionPane.showMessageDialog(this, "Moto agregada al carrito correctamente.");

        View.Carrito vistaCarrito = new View.Carrito(carrito);
        vistaCarrito.setVisible(true);
        vistaCarrito.toFront();
        
    }//GEN-LAST:event_Carrito6ActionPerformed

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
            java.util.logging.Logger.getLogger(YamahaNegra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YamahaNegra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YamahaNegra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YamahaNegra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new YamahaNegra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Carrito6;
    private javax.swing.JLabel CilindrajeTxt1;
    private javax.swing.JLabel MotoFoto;
    private javax.swing.JLabel Motortxt;
    private javax.swing.JLabel estadotxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel marcatxt;
    private javax.swing.JLabel precioTxt;
    private javax.swing.JLabel tipoTxt;
    private javax.swing.JLabel txtAño1;
    private javax.swing.JLabel txtModelo;
    // End of variables declaration//GEN-END:variables
}
