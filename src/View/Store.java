
package View;

import Controller.MotoController;
import Controller.UsuarioController;
import Model.Constants.TipoMoto;
import Model.Entities.Moto;
import Model.Entities.Sesion;
import Model.Entities.Usuario;
import View.BoxerVerde;
import View.BwsRoja;
import View.Config;
import View.Login;
import View.KawasakiVerde;
import View.LittleNegra;
import View.VerdeMinima;
import View.YamahaNegra;
import View.bwsNegra;
import View.historialCompras;
import View.y1Roja;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;


public class Store extends javax.swing.JFrame {

    private MotoController controller;
    private UsuarioController usuarioController;
    Config configFrame = new Config();

    public Store() {

        initComponents();
        this.usuarioController = usuarioController;

        this.setLocationRelativeTo(null);
        this.controller = new MotoController();
        mostrarMotoSemiautomatica();
        mostrarMotoBoxer();
        mostrarMotoScooter();
        mostrarMotoSemideportiva();
        mostrarMotoDeportiva();
        mostrarMotoSupersport();
        mostrarMotoChopper();
        mostrarMotoNaked();

        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refrescarTodosLosDatos();
            }
        });

    }

    private void refrescarTodosLosDatos() {
        mostrarMotoSemiautomatica();
        mostrarMotoBoxer();
        mostrarMotoScooter();
        mostrarMotoSemideportiva();
        mostrarMotoDeportiva();
        mostrarMotoSupersport();
        mostrarMotoChopper();
        mostrarMotoNaked();
    }

    private void mostrarMotoSemiautomatica() {
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.SEMIAUTOMATICA);
        Moto moto = null;

        if (!motos.isEmpty()) {
            moto = motos.get(0);
        } else {
            moto = controller.obtenerMotoBasePorTipo(TipoMoto.SEMIAUTOMATICA);
        }

        if (moto != null) {
            tipoPut.setText(moto.getTipoMoto().toString());
            AñoPut.setText(String.valueOf(moto.getFechaIngreso().getYear()));
            PrecioPut.setText(String.valueOf(moto.getPrecio()));
        } else {
            tipoPut.setText("No disponible");
            AñoPut.setText("No disponible");
            PrecioPut.setText("No disponible");
        }
    }

    private void mostrarMotoBoxer() {
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.BOXER);
        Moto moto = null;

        if (!motos.isEmpty()) {
            moto = motos.get(0);
        } else {
            moto = controller.obtenerMotoBasePorTipo(TipoMoto.BOXER);
        }

        if (moto != null) {
            tipoPut1.setText(moto.getTipoMoto().toString());
            AñoPut1.setText(String.valueOf(moto.getFechaIngreso().getYear()));
            PrecioPut1.setText(String.valueOf(moto.getPrecio()));
        } else {
            tipoPut1.setText("No disponible");
            AñoPut1.setText("No disponible");
            PrecioPut1.setText("No disponible");
        }
    }

    private void mostrarMotoScooter() {
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.SCOOTER);
        Moto moto = null;

        if (!motos.isEmpty()) {
            moto = motos.get(0);
        } else {
            moto = controller.obtenerMotoBasePorTipo(TipoMoto.SCOOTER);
        }

        if (moto != null) {
            tipoPut2.setText(moto.getTipoMoto().toString());
            AñoPut2.setText(String.valueOf(moto.getFechaIngreso().getYear()));
            PrecioPut2.setText(String.valueOf(moto.getPrecio()));
        } else {
            tipoPut2.setText("No disponible");
            AñoPut2.setText("No disponible");
            PrecioPut2.setText("No disponible");
        }
    }

    private void mostrarMotoSemideportiva() {
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.SEMIDEPORTIVA);
        Moto moto = null;

        if (!motos.isEmpty()) {
            moto = motos.get(0);
        } else {
            moto = controller.obtenerMotoBasePorTipo(TipoMoto.SEMIDEPORTIVA);
        }

        if (moto != null) {
            tipoPut3.setText(moto.getTipoMoto().toString());
            AñoPut3.setText(String.valueOf(moto.getFechaIngreso().getYear()));
            PrecioPut3.setText(String.valueOf(moto.getPrecio()));
        } else {
            tipoPut3.setText("No disponible");
            AñoPut3.setText("No disponible");
            PrecioPut3.setText("No disponible");
        }
    }

    private void mostrarMotoDeportiva() {
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.DEPORTIVA);
        Moto moto = null;

        if (!motos.isEmpty()) {
            moto = motos.get(0);
        } else {
            moto = controller.obtenerMotoBasePorTipo(TipoMoto.DEPORTIVA);
        }

        if (moto != null) {
            tipoPut4.setText(moto.getTipoMoto().toString());
            AñoPut4.setText(String.valueOf(moto.getFechaIngreso().getYear()));
            PrecioPut4.setText(String.valueOf(moto.getPrecio()));
        } else {
            tipoPut4.setText("No disponible");
            AñoPut4.setText("No disponible");
            PrecioPut4.setText("No disponible");
        }
    }

    private void mostrarMotoSupersport() {
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.SUPERSPORT);
        Moto moto = null;

        if (!motos.isEmpty()) {
            moto = motos.get(0);
        } else {
            moto = controller.obtenerMotoBasePorTipo(TipoMoto.SUPERSPORT);
        }

        if (moto != null) {
            tipoPut5.setText(moto.getTipoMoto().toString());
            AñoPut5.setText(String.valueOf(moto.getFechaIngreso().getYear()));
            PrecioPut5.setText(String.valueOf(moto.getPrecio()));
        } else {
            tipoPut5.setText("No disponible");
            AñoPut5.setText("No disponible");
            PrecioPut5.setText("No disponible");
        }
    }

    private void mostrarMotoChopper() {
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.CHOPPER);
        Moto moto = null;

        if (!motos.isEmpty()) {
            moto = motos.get(0);
        } else {
            moto = controller.obtenerMotoBasePorTipo(TipoMoto.CHOPPER);
        }

        if (moto != null) {
            tipoPut6.setText(moto.getTipoMoto().toString());
            AñoPut6.setText(String.valueOf(moto.getFechaIngreso().getYear()));
            PrecioPut6.setText(String.valueOf(moto.getPrecio()));
        } else {
            tipoPut6.setText("No disponible");
            AñoPut6.setText("No disponible");
            PrecioPut6.setText("No disponible");
        }
    }

    private void mostrarMotoNaked() {
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.NAKED);
        Moto moto = null;

        if (!motos.isEmpty()) {
            moto = motos.get(0);
        } else {
            moto = controller.obtenerMotoBasePorTipo(TipoMoto.NAKED);
        }

        if (moto != null) {
            tipoPut7.setText(moto.getTipoMoto().toString());
            AñoPut7.setText(String.valueOf(moto.getFechaIngreso().getYear()));
            PrecioPut7.setText(String.valueOf(moto.getPrecio()));
        } else {
            tipoPut7.setText("No disponible");
            AñoPut7.setText("No disponible");
            PrecioPut7.setText("No disponible");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        YamahaVerdePanel = new VisualHelpers.RoundedPanel(20)
        ;
        jPanel13 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        PrecioPut7 = new javax.swing.JLabel();
        AñoPut7 = new javax.swing.JLabel();
        VerMas8 = new javax.swing.JButton();
        tipotxt7 = new javax.swing.JLabel();
        tipoPut7 = new javax.swing.JLabel();
        bwsNegraPanel = new VisualHelpers.RoundedPanel(20)
        ;
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        VerMas1 = new javax.swing.JButton();
        AñoPut = new javax.swing.JLabel();
        PrecioPut = new javax.swing.JLabel();
        tipotxt = new javax.swing.JLabel();
        tipoPut = new javax.swing.JLabel();
        KawasakiVerdePanel = new VisualHelpers.RoundedPanel(20)
        ;
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        PrecioPut3 = new javax.swing.JLabel();
        AñoPut3 = new javax.swing.JLabel();
        VerMas4 = new javax.swing.JButton();
        tipotxt3 = new javax.swing.JLabel();
        tipoPut3 = new javax.swing.JLabel();
        MotitoNegraPanel = new VisualHelpers.RoundedPanel(20)
        ;
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        PrecioPut6 = new javax.swing.JLabel();
        AñoPut6 = new javax.swing.JLabel();
        VerMas7 = new javax.swing.JButton();
        tipotxt6 = new javax.swing.JLabel();
        tipoPut6 = new javax.swing.JLabel();
        YamahaRojaPanel = new VisualHelpers.RoundedPanel(20)
        ;
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        PrecioPut4 = new javax.swing.JLabel();
        AñoPut4 = new javax.swing.JLabel();
        VerMas5 = new javax.swing.JButton();
        tipotxt4 = new javax.swing.JLabel();
        tipoPut4 = new javax.swing.JLabel();
        YamahaNegraPanel = new VisualHelpers.RoundedPanel(20)
        ;
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        PrecioPut5 = new javax.swing.JLabel();
        AñoPut5 = new javax.swing.JLabel();
        VerMas6 = new javax.swing.JButton();
        tipotxt5 = new javax.swing.JLabel();
        tipoPut5 = new javax.swing.JLabel();
        BoxerVerdePanel = new VisualHelpers.RoundedPanel(20)
        ;
        jPanel40 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        PrecioPut1 = new javax.swing.JLabel();
        AñoPut1 = new javax.swing.JLabel();
        VerMas2 = new javax.swing.JButton();
        tipotxt1 = new javax.swing.JLabel();
        tipoPut1 = new javax.swing.JLabel();
        BwsRojaPanel = new VisualHelpers.RoundedPanel(20)
        ;
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        PrecioPut2 = new javax.swing.JLabel();
        AñoPut2 = new javax.swing.JLabel();
        VerMas3 = new javax.swing.JButton();
        tipotxt2 = new javax.swing.JLabel();
        tipoPut2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(240, 240, 240));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-refresh-38.png"))); // NOI18N
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 40, 40));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Diseño sin título (6) (1).png"))); // NOI18N
        jLabel14.setText("jLabel13");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 50, 60));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-back-30.png"))); // NOI18N
        jLabel15.setText("Cerrar sesión");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 0, 100, 60));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-settings-38.png"))); // NOI18N
        jLabel17.setText("jLabel13");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 40, 60));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-activity-history-38.png"))); // NOI18N
        jLabel18.setText("jLabel13");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 40, 60));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, 1140, 60));

        YamahaVerdePanel.setBackground(new java.awt.Color(0, 0, 0));
        YamahaVerdePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/motorojita-removebg-preview (1).png"))); // NOI18N
        jPanel8.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 200, 110));

        jPanel13.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 17, 200, 160));

        YamahaVerdePanel.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 250, 260));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/motovelde-removebg-preview (1)_1.png"))); // NOI18N
        YamahaVerdePanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 260, 170));

        jLabel49.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Año:");
        YamahaVerdePanel.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        jLabel26.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Precio:");
        YamahaVerdePanel.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        PrecioPut7.setForeground(new java.awt.Color(255, 255, 255));
        PrecioPut7.setText("Precio");
        YamahaVerdePanel.add(PrecioPut7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 130, -1));

        AñoPut7.setForeground(new java.awt.Color(255, 255, 255));
        AñoPut7.setText("Año");
        YamahaVerdePanel.add(AñoPut7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 130, -1));

        VerMas8.setBackground(new java.awt.Color(0, 153, 255));
        VerMas8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        VerMas8.setForeground(new java.awt.Color(0, 0, 0));
        VerMas8.setText("Ver más");
        VerMas8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        VerMas8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerMas8ActionPerformed(evt);
            }
        });
        YamahaVerdePanel.add(VerMas8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 140, -1));

        tipotxt7.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        tipotxt7.setForeground(new java.awt.Color(255, 255, 255));
        tipotxt7.setText("Tipo");
        YamahaVerdePanel.add(tipotxt7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        tipoPut7.setForeground(new java.awt.Color(255, 255, 255));
        tipoPut7.setText("Tip");
        YamahaVerdePanel.add(tipoPut7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 130, -1));

        jPanel1.add(YamahaVerdePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 350, 250, 300));

        bwsNegraPanel.setBackground(new java.awt.Color(0, 0, 0));
        bwsNegraPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(0, 0, 0));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(204, 204, 204));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/motorojita-removebg-preview (1).png"))); // NOI18N
        jPanel17.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 200, 110));

        jPanel16.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 17, 200, 160));

        bwsNegraPanel.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 250, 260));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/2020-Yamaha-BWS-125a-removebg-preview (1).png"))); // NOI18N
        bwsNegraPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 200, 150));

        jLabel40.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Año:");
        bwsNegraPanel.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        jLabel23.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Precio:");
        bwsNegraPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        VerMas1.setBackground(new java.awt.Color(0, 153, 255));
        VerMas1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        VerMas1.setForeground(new java.awt.Color(0, 0, 0));
        VerMas1.setText("Ver más");
        VerMas1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        VerMas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerMas1ActionPerformed(evt);
            }
        });
        bwsNegraPanel.add(VerMas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 160, -1));

        AñoPut.setForeground(new java.awt.Color(255, 255, 255));
        AñoPut.setText("Año");
        bwsNegraPanel.add(AñoPut, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 130, -1));

        PrecioPut.setForeground(new java.awt.Color(255, 255, 255));
        PrecioPut.setText("Precio");
        bwsNegraPanel.add(PrecioPut, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 130, -1));

        tipotxt.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        tipotxt.setForeground(new java.awt.Color(255, 255, 255));
        tipotxt.setText("Tipo");
        bwsNegraPanel.add(tipotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        tipoPut.setForeground(new java.awt.Color(255, 255, 255));
        tipoPut.setText("Tip");
        bwsNegraPanel.add(tipoPut, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 130, -1));

        jPanel1.add(bwsNegraPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 250, 300));

        KawasakiVerdePanel.setBackground(new java.awt.Color(0, 0, 0));
        KawasakiVerdePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(0, 0, 0));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(204, 204, 204));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/motorojita-removebg-preview (1).png"))); // NOI18N
        jPanel21.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 200, 110));

        jPanel20.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 17, 200, 160));

        KawasakiVerdePanel.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 250, 260));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/2025-Kawasaki-Ninja-ZX-6R-ABS-KRT-Edition3-removebg-preview (1)_1.png"))); // NOI18N
        KawasakiVerdePanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 240, 160));

        jLabel46.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Año:");
        KawasakiVerdePanel.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        jLabel25.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Precio:");
        KawasakiVerdePanel.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        PrecioPut3.setForeground(new java.awt.Color(255, 255, 255));
        PrecioPut3.setText("Precio");
        KawasakiVerdePanel.add(PrecioPut3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 130, -1));

        AñoPut3.setForeground(new java.awt.Color(255, 255, 255));
        AñoPut3.setText("Año");
        KawasakiVerdePanel.add(AñoPut3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 130, -1));

        VerMas4.setBackground(new java.awt.Color(0, 153, 255));
        VerMas4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        VerMas4.setForeground(new java.awt.Color(0, 0, 0));
        VerMas4.setText("Ver más");
        VerMas4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        VerMas4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerMas4ActionPerformed(evt);
            }
        });
        KawasakiVerdePanel.add(VerMas4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 150, -1));

        tipotxt3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        tipotxt3.setForeground(new java.awt.Color(255, 255, 255));
        tipotxt3.setText("Tipo");
        KawasakiVerdePanel.add(tipotxt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        tipoPut3.setForeground(new java.awt.Color(255, 255, 255));
        tipoPut3.setText("Tip");
        KawasakiVerdePanel.add(tipoPut3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 130, -1));

        jPanel1.add(KawasakiVerdePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 30, 250, 300));

        MotitoNegraPanel.setBackground(new java.awt.Color(0, 0, 0));
        MotitoNegraPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel24.setBackground(new java.awt.Color(0, 0, 0));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel25.setBackground(new java.awt.Color(204, 204, 204));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/motorojita-removebg-preview (1).png"))); // NOI18N
        jPanel25.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 200, 110));

        jPanel24.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 17, 200, 160));

        MotitoNegraPanel.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 250, 260));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/moto (1).png"))); // NOI18N
        MotitoNegraPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 200, 110));

        jLabel52.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Año:");
        MotitoNegraPanel.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        jLabel27.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Precio:");
        MotitoNegraPanel.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        PrecioPut6.setForeground(new java.awt.Color(255, 255, 255));
        PrecioPut6.setText("Precio");
        MotitoNegraPanel.add(PrecioPut6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 130, -1));

        AñoPut6.setForeground(new java.awt.Color(255, 255, 255));
        AñoPut6.setText("Año");
        MotitoNegraPanel.add(AñoPut6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 130, -1));

        VerMas7.setBackground(new java.awt.Color(0, 153, 255));
        VerMas7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        VerMas7.setForeground(new java.awt.Color(0, 0, 0));
        VerMas7.setText("Ver más");
        VerMas7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        VerMas7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerMas7ActionPerformed(evt);
            }
        });
        MotitoNegraPanel.add(VerMas7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 150, -1));

        tipotxt6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        tipotxt6.setForeground(new java.awt.Color(255, 255, 255));
        tipotxt6.setText("Tipo");
        MotitoNegraPanel.add(tipotxt6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        tipoPut6.setForeground(new java.awt.Color(255, 255, 255));
        tipoPut6.setText("Tip");
        MotitoNegraPanel.add(tipoPut6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 130, -1));

        jPanel1.add(MotitoNegraPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 350, 250, 300));

        YamahaRojaPanel.setBackground(new java.awt.Color(0, 0, 0));
        YamahaRojaPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel28.setBackground(new java.awt.Color(0, 0, 0));
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel29.setBackground(new java.awt.Color(204, 204, 204));
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/motorojita-removebg-preview (1).png"))); // NOI18N
        jPanel29.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 200, 110));

        jPanel28.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 17, 200, 160));

        YamahaRojaPanel.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 250, 260));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/motorojita-removebg-preview (1).png"))); // NOI18N
        YamahaRojaPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 200, 110));

        jLabel58.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Año:");
        YamahaRojaPanel.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        jLabel29.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Precio:");
        YamahaRojaPanel.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        PrecioPut4.setForeground(new java.awt.Color(255, 255, 255));
        PrecioPut4.setText("Precio");
        YamahaRojaPanel.add(PrecioPut4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 130, -1));

        AñoPut4.setForeground(new java.awt.Color(255, 255, 255));
        AñoPut4.setText("Año");
        YamahaRojaPanel.add(AñoPut4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 130, -1));

        VerMas5.setBackground(new java.awt.Color(0, 153, 255));
        VerMas5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        VerMas5.setForeground(new java.awt.Color(0, 0, 0));
        VerMas5.setText("Ver más");
        VerMas5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        VerMas5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerMas5ActionPerformed(evt);
            }
        });
        YamahaRojaPanel.add(VerMas5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 150, -1));

        tipotxt4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        tipotxt4.setForeground(new java.awt.Color(255, 255, 255));
        tipotxt4.setText("Tipo");
        YamahaRojaPanel.add(tipotxt4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        tipoPut4.setForeground(new java.awt.Color(255, 255, 255));
        tipoPut4.setText("Tip");
        YamahaRojaPanel.add(tipoPut4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 130, -1));

        jPanel1.add(YamahaRojaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 250, 300));

        YamahaNegraPanel.setBackground(new java.awt.Color(0, 0, 0));
        YamahaNegraPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel32.setBackground(new java.awt.Color(0, 0, 0));
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel33.setBackground(new java.awt.Color(204, 204, 204));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/motorojita-removebg-preview (1).png"))); // NOI18N
        jPanel33.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 200, 110));

        jPanel32.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 17, 200, 160));

        YamahaNegraPanel.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 250, 260));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/yamaha-yzf-r6-2017-gris-oscuro-mate-45498b (1).png"))); // NOI18N
        YamahaNegraPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 200, 110));

        jLabel55.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Año:");
        YamahaNegraPanel.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        jLabel28.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Precio:");
        YamahaNegraPanel.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        PrecioPut5.setForeground(new java.awt.Color(255, 255, 255));
        PrecioPut5.setText("Precio");
        YamahaNegraPanel.add(PrecioPut5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 130, -1));

        AñoPut5.setForeground(new java.awt.Color(255, 255, 255));
        AñoPut5.setText("Año");
        YamahaNegraPanel.add(AñoPut5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 130, -1));

        VerMas6.setBackground(new java.awt.Color(0, 153, 255));
        VerMas6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        VerMas6.setForeground(new java.awt.Color(0, 0, 0));
        VerMas6.setText("Ver más");
        VerMas6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        VerMas6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerMas6ActionPerformed(evt);
            }
        });
        YamahaNegraPanel.add(VerMas6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 150, -1));

        tipotxt5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        tipotxt5.setForeground(new java.awt.Color(255, 255, 255));
        tipotxt5.setText("Tipo");
        YamahaNegraPanel.add(tipotxt5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        tipoPut5.setForeground(new java.awt.Color(255, 255, 255));
        tipoPut5.setText("Tip");
        YamahaNegraPanel.add(tipoPut5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 130, -1));

        jPanel1.add(YamahaNegraPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 350, 250, 300));

        BoxerVerdePanel.setBackground(new java.awt.Color(10, 10, 10));
        BoxerVerdePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel40.setBackground(new java.awt.Color(0, 0, 0));
        jPanel40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel41.setBackground(new java.awt.Color(204, 204, 204));
        jPanel41.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/motorojita-removebg-preview (1).png"))); // NOI18N
        jPanel41.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 200, 110));

        jPanel40.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 17, 200, 160));

        BoxerVerdePanel.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 250, 260));

        jLabel22.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Precio:");
        BoxerVerdePanel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/motoo (1)_1.png"))); // NOI18N
        BoxerVerdePanel.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 230, 160));

        jLabel38.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Año:");
        BoxerVerdePanel.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        PrecioPut1.setForeground(new java.awt.Color(255, 255, 255));
        PrecioPut1.setText("Precio");
        BoxerVerdePanel.add(PrecioPut1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 130, -1));

        AñoPut1.setForeground(new java.awt.Color(255, 255, 255));
        AñoPut1.setText("Año");
        BoxerVerdePanel.add(AñoPut1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 130, -1));

        VerMas2.setBackground(new java.awt.Color(0, 153, 255));
        VerMas2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        VerMas2.setForeground(new java.awt.Color(0, 0, 0));
        VerMas2.setText("Ver más");
        VerMas2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        VerMas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerMas2ActionPerformed(evt);
            }
        });
        BoxerVerdePanel.add(VerMas2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 150, -1));

        tipotxt1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        tipotxt1.setForeground(new java.awt.Color(255, 255, 255));
        tipotxt1.setText("Tipo");
        BoxerVerdePanel.add(tipotxt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        tipoPut1.setForeground(new java.awt.Color(255, 255, 255));
        tipoPut1.setText("Tip");
        BoxerVerdePanel.add(tipoPut1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 130, -1));

        jPanel1.add(BoxerVerdePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 250, 300));

        BwsRojaPanel.setBackground(new java.awt.Color(0, 0, 0));
        BwsRojaPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel44.setBackground(new java.awt.Color(0, 0, 0));
        jPanel44.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel45.setBackground(new java.awt.Color(204, 204, 204));
        jPanel45.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/motorojita-removebg-preview (1).png"))); // NOI18N
        jPanel45.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 200, 110));

        jPanel44.add(jPanel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 17, 200, 160));

        BwsRojaPanel.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 250, 260));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/web-ares-new-1024x1024-removebg-preview (1).png"))); // NOI18N
        BwsRojaPanel.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 230, 160));

        jLabel43.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Año:");
        BwsRojaPanel.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        jLabel24.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Precio:");
        BwsRojaPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        PrecioPut2.setForeground(new java.awt.Color(255, 255, 255));
        PrecioPut2.setText("Precio");
        BwsRojaPanel.add(PrecioPut2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 130, -1));

        AñoPut2.setForeground(new java.awt.Color(255, 255, 255));
        AñoPut2.setText("Año");
        BwsRojaPanel.add(AñoPut2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 130, -1));

        VerMas3.setBackground(new java.awt.Color(0, 153, 255));
        VerMas3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        VerMas3.setForeground(new java.awt.Color(0, 0, 0));
        VerMas3.setText("Ver más");
        VerMas3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        VerMas3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerMas3ActionPerformed(evt);
            }
        });
        BwsRojaPanel.add(VerMas3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 150, -1));

        tipotxt2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        tipotxt2.setForeground(new java.awt.Color(255, 255, 255));
        tipotxt2.setText("Tipo");
        BwsRojaPanel.add(tipotxt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        tipoPut2.setForeground(new java.awt.Color(255, 255, 255));
        tipoPut2.setText("Tip");
        BwsRojaPanel.add(tipoPut2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 130, -1));

        jPanel1.add(BwsRojaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 250, 300));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        Sesion sesion = Sesion.getInstancia();
        Model.Entities.Carrito carritoActual = sesion.getCarritoActual();

        if (carritoActual != null) {
            this.dispose();
            View.Carrito vistaCarrito = new View.Carrito(carritoActual);
            vistaCarrito.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No hay carrito asociado al usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jLabel14MouseClicked

    private void VerMas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerMas1ActionPerformed
        MotoController controller = new MotoController();
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.SEMIAUTOMATICA);

        if (motos == null || motos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No hay motos disponibles de tipo SEMIAUTOMÁTICA en este momento.",
                    "Sin disponibilidad",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        bwsNegra neg = new bwsNegra();
        neg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_VerMas1ActionPerformed

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        Usuario usuarioActual = Sesion.getInstancia().getUsuarioActual();

        if (usuarioActual != null) {
            historialCompras historial = new historialCompras(usuarioActual);
            historial.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No hay usuario logueado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jLabel18MouseClicked

    private void VerMas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerMas2ActionPerformed
        MotoController controller = new MotoController();
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.BOXER);

        if (motos == null || motos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No hay motos disponibles de tipo BOXER en este momento.",
                    "Sin disponibilidad",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        BoxerVerde bxv = new BoxerVerde();
        this.dispose();
        bxv.setVisible(true);
    }//GEN-LAST:event_VerMas2ActionPerformed

    private void VerMas3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerMas3ActionPerformed
        MotoController controller = new MotoController();
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.SCOOTER);

        if (motos == null || motos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No hay motos disponibles de tipo SCOOTER en este momento.",
                    "Sin disponibilidad",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        BwsRoja bwr = new BwsRoja();
        this.dispose();
        bwr.setVisible(true);
    }//GEN-LAST:event_VerMas3ActionPerformed

    private void VerMas4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerMas4ActionPerformed
        MotoController controller = new MotoController();
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.SEMIDEPORTIVA);

        if (motos == null || motos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No hay motos disponibles de tipo SEMIDEPORTIVA en este momento.",
                    "Sin disponibilidad",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        KawasakiVerde kwv = new KawasakiVerde();
        this.dispose();
        kwv.setVisible(true);
    }//GEN-LAST:event_VerMas4ActionPerformed

    private void VerMas5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerMas5ActionPerformed
        MotoController controller = new MotoController();
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.DEPORTIVA);

        if (motos == null || motos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No hay motos disponibles de tipo DEPORTIVA en este momento.",
                    "Sin disponibilidad",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        y1Roja y1r = new y1Roja();
        this.dispose();
        y1r.setVisible(true);
    }//GEN-LAST:event_VerMas5ActionPerformed

    private void VerMas6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerMas6ActionPerformed
        MotoController controller = new MotoController();
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.SUPERSPORT);

        if (motos == null || motos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No hay motos disponibles de tipo SUPERSPORT en este momento.",
                    "Sin disponibilidad",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        YamahaNegra ymn = new YamahaNegra();
        this.dispose();
        ymn.setVisible(true);
    }//GEN-LAST:event_VerMas6ActionPerformed

    private void VerMas7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerMas7ActionPerformed
        MotoController controller = new MotoController();
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.CHOPPER);

        if (motos == null || motos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No hay motos disponibles de tipo CHOPPER en este momento.",
                    "Sin disponibilidad",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        LittleNegra ltn = new LittleNegra();
        this.dispose();
        ltn.setVisible(true);
    }//GEN-LAST:event_VerMas7ActionPerformed

    private void VerMas8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerMas8ActionPerformed
        MotoController controller = new MotoController();
        List<Moto> motos = controller.obtenerMotosDisponiblesPorTipo(TipoMoto.NAKED);

        if (motos == null || motos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No hay motos disponibles de tipo NAKED en este momento.",
                    "Sin disponibilidad",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        VerdeMinima vdm = new VerdeMinima();
        this.dispose();
        vdm.setVisible(true);
    }//GEN-LAST:event_VerMas8ActionPerformed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        Login jv = new Login();
        this.dispose();
        jv.setVisible(true);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        UsuarioController usuarioController = UsuarioController.getInstance();

        if (usuarioController != null && usuarioController.getUsuarioLogeado() != null) {
            Config configFrame = new Config();
            configFrame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No hay usuario logeado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }//GEN-LAST:event_jLabel17MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Login SE 6) is not available, stay with the default look and feel.
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
        java.util.logging.Logger.getLogger(Store.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(Store.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(Store.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(Store.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new Store().setVisible(true);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AñoPut;
    private javax.swing.JLabel AñoPut1;
    private javax.swing.JLabel AñoPut2;
    private javax.swing.JLabel AñoPut3;
    private javax.swing.JLabel AñoPut4;
    private javax.swing.JLabel AñoPut5;
    private javax.swing.JLabel AñoPut6;
    private javax.swing.JLabel AñoPut7;
    private javax.swing.JPanel BoxerVerdePanel;
    private javax.swing.JPanel BwsRojaPanel;
    private javax.swing.JPanel KawasakiVerdePanel;
    private javax.swing.JPanel MotitoNegraPanel;
    private javax.swing.JLabel PrecioPut;
    private javax.swing.JLabel PrecioPut1;
    private javax.swing.JLabel PrecioPut2;
    private javax.swing.JLabel PrecioPut3;
    private javax.swing.JLabel PrecioPut4;
    private javax.swing.JLabel PrecioPut5;
    private javax.swing.JLabel PrecioPut6;
    private javax.swing.JLabel PrecioPut7;
    private javax.swing.JButton VerMas1;
    private javax.swing.JButton VerMas2;
    private javax.swing.JButton VerMas3;
    private javax.swing.JButton VerMas4;
    private javax.swing.JButton VerMas5;
    private javax.swing.JButton VerMas6;
    private javax.swing.JButton VerMas7;
    private javax.swing.JButton VerMas8;
    private javax.swing.JPanel YamahaNegraPanel;
    private javax.swing.JPanel YamahaRojaPanel;
    private javax.swing.JPanel YamahaVerdePanel;
    private javax.swing.JPanel bwsNegraPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel tipoPut;
    private javax.swing.JLabel tipoPut1;
    private javax.swing.JLabel tipoPut2;
    private javax.swing.JLabel tipoPut3;
    private javax.swing.JLabel tipoPut4;
    private javax.swing.JLabel tipoPut5;
    private javax.swing.JLabel tipoPut6;
    private javax.swing.JLabel tipoPut7;
    private javax.swing.JLabel tipotxt;
    private javax.swing.JLabel tipotxt1;
    private javax.swing.JLabel tipotxt2;
    private javax.swing.JLabel tipotxt3;
    private javax.swing.JLabel tipotxt4;
    private javax.swing.JLabel tipotxt5;
    private javax.swing.JLabel tipotxt6;
    private javax.swing.JLabel tipotxt7;
    // End of variables declaration//GEN-END:variables
}
