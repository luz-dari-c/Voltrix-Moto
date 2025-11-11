/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.UsuarioController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author gameV
 */
public class Verificación extends javax.swing.JFrame {

    UsuarioController usuarioController = UsuarioController.getInstance();
    private Timer timer;
    private int tiempoRestante;
    private static final int TIEMPO_ESPERA = 120; // 2 minutos
    private String codigoGenerado;

    public Verificación() {
        initComponents();

        this.tiempoRestante = 0;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Ocultar panel de nueva contraseña inicialmente
        PanelNewPass.setVisible(false);

        // Configurar campos de código para validación automática
        configurarCamposCodigo();

        // Inicializar contador
        actualizarContador();
    }

    private void configurarCamposCodigo() {
        // Agregar DocumentListener a cada campo de código para validación automática
        javax.swing.event.DocumentListener documentListener = new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                validarCodigoCompleto();
                moverSiguienteCampo(e);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                validarCodigoCompleto();
                moverCampoAnterior(e);
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                validarCodigoCompleto();
            }

            private void validarCodigoCompleto() {
                if (esCodigoCompleto()) {
                    String codigoIngresado = obtenerCodigoCompleto();
                    if (usuarioController.verificarCodigo(codigoIngresado)) {
                        PanelNewPass.setVisible(true);
                        JOptionPane.showMessageDialog(Verificación.this,
                                "Código verificado correctamente. Ahora puede establecer su nueva contraseña.",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(Verificación.this,
                                "Código incorrecto. Por favor, verifique el código enviado a su correo.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        limpiarCamposCodigo();
                    }
                }
            }

            private void moverSiguienteCampo(javax.swing.event.DocumentEvent e) {
                JTextField source = getTextFieldFromEvent(e);
                if (source.getDocument().getLength() == 1) {
                    transferFocus();
                }
            }

            private void moverCampoAnterior(javax.swing.event.DocumentEvent e) {
                JTextField source = getTextFieldFromEvent(e);
                if (source.getDocument().getLength() == 0) {
                    transferFocusBackward();
                }
            }

            private JTextField getTextFieldFromEvent(javax.swing.event.DocumentEvent e) {
                if (e.getDocument() == digitoo1.getDocument()) {
                    return digitoo1;
                }
                if (e.getDocument() == digitoo2.getDocument()) {
                    return digitoo2;
                }
                if (e.getDocument() == digitoo3.getDocument()) {
                    return digitoo3;
                }
                if (e.getDocument() == digitoo4.getDocument()) {
                    return digitoo4;
                }
                if (e.getDocument() == digitoo5.getDocument()) {
                    return digitoo5;
                }
                return null;
            }
        };

        digitoo1.getDocument().addDocumentListener(documentListener);
        digitoo2.getDocument().addDocumentListener(documentListener);
        digitoo3.getDocument().addDocumentListener(documentListener);
        digitoo4.getDocument().addDocumentListener(documentListener);
        digitoo5.getDocument().addDocumentListener(documentListener);
    }

    private boolean esCodigoCompleto() {
        return !digitoo1.getText().isEmpty()
                && !digitoo2.getText().isEmpty()
                && !digitoo3.getText().isEmpty()
                && !digitoo4.getText().isEmpty()
                && !digitoo5.getText().isEmpty();
    }

    private String obtenerCodigoCompleto() {
        return digitoo1.getText() + digitoo2.getText() + digitoo3.getText()
                + digitoo4.getText() + digitoo5.getText();
    }

    private void limpiarCamposCodigo() {
        digitoo1.setText("");
        digitoo2.setText("");
        digitoo3.setText("");
        digitoo4.setText("");
        digitoo5.setText("");
        digitoo1.requestFocus();
    }

    private void iniciarTemporizador() {
        tiempoRestante = TIEMPO_ESPERA;
        jLabel5.setEnabled(false);

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;
                actualizarContador();

                if (tiempoRestante <= 0) {
                    timer.stop();
                    jLabel5.setEnabled(true);
                    contador.setText("Listo");
                }
            }
        });
        timer.start();
    }

    private void actualizarContador() {
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        contador.setText(String.format("%02d:%02d", minutos, segundos));
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
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        digitoo5 = new javax.swing.JTextField();
        digitoo1 = new javax.swing.JTextField();
        digitoo2 = new javax.swing.JTextField();
        digitoo3 = new javax.swing.JTextField();
        digitoo4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        contador = new javax.swing.JLabel();
        PanelNewPass = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        nuevaContraseñaField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        ConfirmarContraseñaField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        enviarCodigo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Para reestablecer su contraseña, por favor ingresé el codigo de 4 digitos que se envió a su correo.");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 220, 100, 40));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, 100, 40));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, 100, 40));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 100, 40));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 220, 100, 40));

        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Revisa en todas las bandejas de tu correo");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, -1, -1));

        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("jLabel3");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("incluyendo la de spam.");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, -1, -1));

        digitoo5.setBackground(new java.awt.Color(255, 255, 255));
        digitoo5.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 18)); // NOI18N
        digitoo5.setForeground(new java.awt.Color(0, 0, 0));
        digitoo5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        digitoo5.setText("5");
        jPanel1.add(digitoo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 140, 100, 80));

        digitoo1.setBackground(new java.awt.Color(255, 255, 255));
        digitoo1.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 18)); // NOI18N
        digitoo1.setForeground(new java.awt.Color(0, 0, 0));
        digitoo1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        digitoo1.setText("1");
        jPanel1.add(digitoo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 136, 100, 80));

        digitoo2.setBackground(new java.awt.Color(255, 255, 255));
        digitoo2.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 18)); // NOI18N
        digitoo2.setForeground(new java.awt.Color(0, 0, 0));
        digitoo2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        digitoo2.setText("2");
        jPanel1.add(digitoo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 100, 80));

        digitoo3.setBackground(new java.awt.Color(255, 255, 255));
        digitoo3.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 18)); // NOI18N
        digitoo3.setForeground(new java.awt.Color(0, 0, 0));
        digitoo3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        digitoo3.setText("3");
        jPanel1.add(digitoo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 100, 80));

        digitoo4.setBackground(new java.awt.Color(255, 255, 255));
        digitoo4.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 18)); // NOI18N
        digitoo4.setForeground(new java.awt.Color(0, 0, 0));
        digitoo4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        digitoo4.setText("4");
        jPanel1.add(digitoo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 100, 80));

        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("Reenviar codigo");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 250, -1, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("en:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 270, -1, -1));

        contador.setText("contador");
        jPanel1.add(contador, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 270, -1, -1));

        PanelNewPass.setBackground(new java.awt.Color(204, 204, 204));
        PanelNewPass.setForeground(new java.awt.Color(0, 0, 0));
        PanelNewPass.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Ingresar nueva contraseña:");
        PanelNewPass.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 21, 220, -1));
        PanelNewPass.add(nuevaContraseñaField, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 270, -1));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Confirmar la contraseña:");
        PanelNewPass.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 220, -1));
        PanelNewPass.add(ConfirmarContraseñaField, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 270, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Guardar Contraseña");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        PanelNewPass.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, -1, -1));

        jPanel1.add(PanelNewPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 680, 170));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Ingrese su correo:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, -1, -1));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 310, -1));

        enviarCodigo.setText("Enviar codigo");
        enviarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(enviarCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        if (jLabel5.isEnabled()) {
            enviarCodigoActionPerformed(null);
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nuevaContrasena = new String(nuevaContraseñaField.getText());
        String confirmacion = new String(ConfirmarContraseñaField.getText());

        String resultado = usuarioController.actualizarContrasena(nuevaContrasena, confirmacion);

        if (resultado.equals("OK")) {
            JOptionPane.showMessageDialog(this,
                    "Contraseña actualizada exitosamente. Ahora puede iniciar sesión con su nueva contraseña.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar campos y ocultar panel
            nuevaContraseñaField.setText("");
            ConfirmarContraseñaField.setText("");
            PanelNewPass.setVisible(false);
            limpiarCamposCodigo();

            // Regresar al login
            new Java().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void enviarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarCodigoActionPerformed
        String email = jTextField1.getText().trim();
        String resultado = usuarioController.enviarCodigoRecuperacion(email);

        if (resultado.equals("OK")) {
            JOptionPane.showMessageDialog(this,
                    "Código de verificación enviado a su correo electrónico.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            iniciarTemporizador();
            limpiarCamposCodigo();
            PanelNewPass.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_enviarCodigoActionPerformed

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
            java.util.logging.Logger.getLogger(Verificación.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Verificación.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Verificación.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Verificación.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Verificación().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ConfirmarContraseñaField;
    private javax.swing.JPanel PanelNewPass;
    private javax.swing.JLabel contador;
    private javax.swing.JTextField digitoo1;
    private javax.swing.JTextField digitoo2;
    private javax.swing.JTextField digitoo3;
    private javax.swing.JTextField digitoo4;
    private javax.swing.JTextField digitoo5;
    private javax.swing.JButton enviarCodigo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField nuevaContraseñaField;
    // End of variables declaration//GEN-END:variables
}
