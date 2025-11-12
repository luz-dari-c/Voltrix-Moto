package View;

import Controller.EmpleadoController;
import DAO.EmpleadoDAO;
import Model.Entities.Empleado;
import Utilidades.ModernTopMenu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Administador extends javax.swing.JFrame {

    EmpleadoController empleadoController = EmpleadoController.getInstance();
    private Empleado empleadoSeleccionado;

    public Administador() {
        initComponents();
        visualizarEmpleados.setComponentPopupMenu(null);

        añadirEmpleado.setUI(null);
        this.setLocationRelativeTo(null);
        cargarEmpleados();
        cargarEmpleadosEnEliminar();
        cargarEmpleadosEnModificar();
        validacionAñadirYModicicarEmpleado();

        tablaModificacion.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                mostrarPopup(e);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                mostrarPopup(e);
            }

            private void mostrarPopup(java.awt.event.MouseEvent e) {
                int fila = tablaModificacion.rowAtPoint(e.getPoint());
                if (fila >= 0 && fila < tablaModificacion.getRowCount()) {
                    tablaModificacion.setRowSelectionInterval(fila, fila);
                } else {
                    tablaModificacion.clearSelection();
                }

                if (e.isPopupTrigger()) {
                    jPopupMenu3.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        Despedir.addActionListener(e -> {
            JTable tabla = (JTable) jPopupMenu2.getInvoker();

            if (tabla != tablaEliminar) {
                return;
            }

            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar un empleado primero");
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro que deseas despedir a este empleado?",
                    "Confirmar Despido",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                String identificacion = (String) tabla.getValueAt(filaSeleccionada, 5);
                boolean eliminado = empleadoController.eliminarEmpleado(identificacion);

                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Empleado despedido correctamente");
                    cargarEmpleadosEnEliminar();
                    cargarEmpleados();
                    cargarEmpleadosEnModificar();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo despedir al empleado");
                }
            }
        });

        ModernTopMenu menuSuperior = new ModernTopMenu(ModificarInfoAdmin);
        // 2️⃣ Crear el menú superior moderno
        menuSuperior = new Utilidades.ModernTopMenu(ModificarInfoAdmin);

        // 3️⃣ Aplicar colores personalizados
        menuSuperior.setColors(
                new Color(29, 35, 51), // Color principal del menú
                new Color(41, 50, 65), // Color hover
                Color.WHITE, // Texto blanco
                new Color(20, 25, 40), // Fondo principal oscuro
                new Color(24, 30, 45) // Fondo del desplegable
        );

        // 4️⃣ Establecer BorderLayout para que el menú quede arriba
        getContentPane().setLayout(new BorderLayout());

        // Buscar el panel principal que NetBeans generó (por ejemplo jPanel1)
        Component[] components = getContentPane().getComponents();
        JPanel mainPanel = null;

        for (Component comp : components) {
            if (comp instanceof JPanel && ((JPanel) comp).getComponentCount() > 0) {
                mainPanel = (JPanel) comp;
                break;
            }
        }

        // 5️⃣ Reorganizar los componentes
        if (mainPanel != null) {
            getContentPane().remove(mainPanel);
            getContentPane().add(menuSuperior, BorderLayout.NORTH);
            getContentPane().add(mainPanel, BorderLayout.CENTER);
        } else {
            getContentPane().add(menuSuperior, BorderLayout.NORTH);
        }

        pack();
        setLocationRelativeTo(null);

    }

    private void cargarEmpleados() {
        EmpleadoDAO empleadoDAO = EmpleadoDAO.getInstance();
        List<Empleado> empleados = empleadoDAO.cargarTodos();

        DefaultTableModel model = (DefaultTableModel) visualizarEmpleados.getModel();

        model.setRowCount(0);

        for (Empleado e : empleados) {
            model.addRow(new Object[]{
                e.getPrimerNombre(),
                e.getSegundoNombre(),
                e.getPrimerApellido(),
                e.getSegundoApellido(),
                e.getEdad(),
                e.getIdentificacion(),
                e.getCorreo(),
                e.getTelefono()
            });
        }
    }

    private void cargarEmpleadosEnEliminar() {
        EmpleadoDAO empleadoDAO = EmpleadoDAO.getInstance();
        List<Empleado> empleados = empleadoDAO.cargarTodos();

        DefaultTableModel model = (DefaultTableModel) tablaEliminar.getModel();

        model.setRowCount(0);

        for (Empleado e : empleados) {
            model.addRow(new Object[]{
                e.getPrimerNombre(),
                e.getSegundoNombre(),
                e.getPrimerApellido(),
                e.getSegundoApellido(),
                e.getEdad(),
                e.getIdentificacion(),
                e.getCorreo(),
                e.getTelefono()
            });
        }
    }

    private void cargarEmpleadosEnModificar() {
        EmpleadoDAO empleadoDAO = EmpleadoDAO.getInstance();
        List<Empleado> empleados = empleadoDAO.cargarTodos();

        DefaultTableModel model = (DefaultTableModel) tablaModificacion.getModel();

        model.setRowCount(0);

        for (Empleado e : empleados) {
            model.addRow(new Object[]{
                e.getPrimerNombre(),
                e.getSegundoNombre(),
                e.getPrimerApellido(),
                e.getSegundoApellido(),
                e.getEdad(),
                e.getIdentificacion(),
                e.getCorreo(),
                e.getTelefono(),
                e.getIdEmpleado()
            });
        }
    }

    private void cargarDatosEmpleado(Empleado emp) {
        if (emp == null) {
            return;
        }

        txtNuevoPrimerNombre.setText(emp.getPrimerNombre());
        Nombre2Field1.setText(emp.getSegundoNombre());
        Apellido1Field1.setText(emp.getPrimerApellido());
        txtNuevoSegundoApellido.setText(emp.getSegundoApellido());
        txtNuevoCorreo.setText(emp.getCorreo());
        txtNuevaCedula.setText(emp.getIdentificacion());
        txtNuevoTelefono.setText(emp.getTelefono());
        txtNuevaEdad.setText(String.valueOf(emp.getEdad()));

        empleadoSeleccionado = emp;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MotoEliminar = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        Despedir = new javax.swing.JMenuItem();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        modificar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        ModificarInfoAdmin = new javax.swing.JTabbedPane();
        añadirEmpleado = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        primerNombre = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        segundoNombre = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        primerApellido = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        segundoApellido = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        correo = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cedula = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        telefono = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        edad = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        checkBoxTerminosYConcidiones = new javax.swing.JCheckBox();
        btnAñadirEmpleado = new javax.swing.JButton();
        EliminarEmpleado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEliminar = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        ModificarEmpleado = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaModificacion = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        VisualizarEmpleado = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        visualizarEmpleados = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        AñadirMoto = new javax.swing.JPanel();
        VisualizarMoto2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        comboBoxTipoMoto2 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        MarcaMotos = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        EliminarMoto = new javax.swing.JPanel();
        tablaMotos = new javax.swing.JScrollPane();
        tablaDeCarrito = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        ModificarMoto = new javax.swing.JPanel();
        VisualizarMoto1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        panelExtraSinuso = new javax.swing.JPanel();
        modifcarEmpleado = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtNuevoPrimerNombre = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        Nombre2Field1 = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel36 = new javax.swing.JLabel();
        Apellido1Field1 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel38 = new javax.swing.JLabel();
        txtNuevoSegundoApellido = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtNuevoCorreo = new javax.swing.JTextField();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        txtNuevaCedula = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txtNuevoTelefono = new javax.swing.JTextField();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel47 = new javax.swing.JLabel();
        txtNuevaEdad = new javax.swing.JTextField();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        btnModificarEmpleado = new javax.swing.JButton();

        MotoEliminar.setText("Eliminar esta moto");
        jPopupMenu1.add(MotoEliminar);

        Despedir.setText("DespedirEmpleado");
        jPopupMenu2.add(Despedir);

        modificar.setText("Modificar información");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });
        jPopupMenu3.add(modificar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ModificarInfoAdmin.setBackground(new java.awt.Color(255, 255, 255));

        añadirEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        añadirEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-user-circle-34.png"))); // NOI18N
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 40, 50));

        jLabel15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Ingrese el primer nombre:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, 20));

        primerNombre.setForeground(new java.awt.Color(0, 0, 0));
        primerNombre.setBorder(null);
        primerNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                primerNombreActionPerformed(evt);
            }
        });
        jPanel3.add(primerNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 350, 30));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 350, 20));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Ingrese el segundo nombre:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, 20));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-user-circle-34.png"))); // NOI18N
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 40, 50));

        segundoNombre.setForeground(new java.awt.Color(0, 0, 0));
        segundoNombre.setBorder(null);
        segundoNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segundoNombreActionPerformed(evt);
            }
        });
        jPanel3.add(segundoNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 350, 30));

        jSeparator9.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 350, 20));

        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Ingrese el primer apellido:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, 20));

        primerApellido.setForeground(new java.awt.Color(0, 0, 0));
        primerApellido.setBorder(null);
        primerApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                primerApellidoActionPerformed(evt);
            }
        });
        jPanel3.add(primerApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 350, 30));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-user-circle-34.png"))); // NOI18N
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 40, 50));

        jSeparator8.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 350, 20));

        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Ingrese el segundo apellido:");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, 20));

        segundoApellido.setForeground(new java.awt.Color(0, 0, 0));
        segundoApellido.setBorder(null);
        segundoApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segundoApellidoActionPerformed(evt);
            }
        });
        jPanel3.add(segundoApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 350, 30));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-user-circle-34.png"))); // NOI18N
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 40, 50));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 340, 20));

        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Ingrese el correo:");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, 20));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-email-34 (1).png"))); // NOI18N
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 40, 50));

        correo.setForeground(new java.awt.Color(0, 0, 0));
        correo.setBorder(null);
        correo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correoActionPerformed(evt);
            }
        });
        jPanel3.add(correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 350, 30));

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 350, 20));

        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Ingrese la cedula:");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, -1, -1));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-id-42.png"))); // NOI18N
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 40, 50));

        cedula.setForeground(new java.awt.Color(0, 0, 0));
        cedula.setBorder(null);
        cedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cedulaActionPerformed(evt);
            }
        });
        jPanel3.add(cedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 350, 30));

        jSeparator7.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, 350, 20));

        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Ingrese el número telefonico:");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("DEBE LLENAR TODOS LOS CAMPOS PARA PODER CONTRATAR UN NUEVO EMPLEADO");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, -1, -1));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-telephone-32.png"))); // NOI18N
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 40, 50));

        telefono.setForeground(new java.awt.Color(0, 0, 0));
        telefono.setBorder(null);
        telefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonoActionPerformed(evt);
            }
        });
        jPanel3.add(telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 430, 350, 30));

        jSeparator10.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 460, 350, 20));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Ingrese la edad:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 466, -1, 20));

        edad.setForeground(new java.awt.Color(0, 0, 0));
        edad.setBorder(null);
        edad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edadActionPerformed(evt);
            }
        });
        jPanel3.add(edad, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, 350, 30));

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, 350, 20));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-age-32.png"))); // NOI18N
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, 40, 50));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/unnamed-removebg-preview.png"))); // NOI18N
        jLabel28.setText("jLabel28");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, 440, 350));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("VOLTRIX S.A.S COMPANY SOLO MANEJA CONTRATO A TERMINO INDEFINIDO, SEGÚN LA LEY 2395 DEL 2025");
        jPanel3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 530, 390, -1));

        checkBoxTerminosYConcidiones.setForeground(new java.awt.Color(0, 0, 0));
        checkBoxTerminosYConcidiones.setText("Entiendo y acepto los terminos y condiciones de contratación.");
        checkBoxTerminosYConcidiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxTerminosYConcidionesActionPerformed(evt);
            }
        });
        jPanel3.add(checkBoxTerminosYConcidiones, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 550, -1, -1));

        btnAñadirEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        btnAñadirEmpleado.setText("Contratar.");
        btnAñadirEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirEmpleadoActionPerformed(evt);
            }
        });
        jPanel3.add(btnAñadirEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 577, 120, 40));

        añadirEmpleado.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 660));

        ModificarInfoAdmin.addTab("tab10", añadirEmpleado);

        EliminarEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        EliminarEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaEliminar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Primer nombre", "Segundo nombre", "Primer apellido", "Segundo Apellido", "Edad", "Identificación", "Correo", "Telefono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaEliminar.setComponentPopupMenu(jPopupMenu2);
        tablaEliminar.setRowHeight(40);
        jScrollPane1.setViewportView(tablaEliminar);
        if (tablaEliminar.getColumnModel().getColumnCount() > 0) {
            tablaEliminar.getColumnModel().getColumn(0).setResizable(false);
            tablaEliminar.getColumnModel().getColumn(1).setResizable(false);
            tablaEliminar.getColumnModel().getColumn(2).setResizable(false);
            tablaEliminar.getColumnModel().getColumn(3).setResizable(false);
            tablaEliminar.getColumnModel().getColumn(4).setResizable(false);
            tablaEliminar.getColumnModel().getColumn(5).setResizable(false);
            tablaEliminar.getColumnModel().getColumn(6).setResizable(false);
            tablaEliminar.getColumnModel().getColumn(7).setResizable(false);
        }

        EliminarEmpleado.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1120, 590));
        EliminarEmpleado.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, -1, -1));

        jLabel29.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Seleccione el empleado que desea eliminar y dé click derecho para despedirlo. ");
        EliminarEmpleado.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, -1, 60));

        ModificarInfoAdmin.addTab("tab2", EliminarEmpleado);

        ModificarEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaModificacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Primer nombre", "Segundo nombre", "Primer apellido", "Segundo Apellido", "Edad", "Identificación", "Correo", "Telefono", "Id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaModificacion.setComponentPopupMenu(jPopupMenu3);
        tablaModificacion.setRowHeight(40);
        jScrollPane2.setViewportView(tablaModificacion);
        if (tablaModificacion.getColumnModel().getColumnCount() > 0) {
            tablaModificacion.getColumnModel().getColumn(0).setResizable(false);
            tablaModificacion.getColumnModel().getColumn(1).setResizable(false);
            tablaModificacion.getColumnModel().getColumn(2).setResizable(false);
            tablaModificacion.getColumnModel().getColumn(3).setResizable(false);
            tablaModificacion.getColumnModel().getColumn(4).setResizable(false);
            tablaModificacion.getColumnModel().getColumn(5).setResizable(false);
            tablaModificacion.getColumnModel().getColumn(6).setResizable(false);
            tablaModificacion.getColumnModel().getColumn(7).setResizable(false);
            tablaModificacion.getColumnModel().getColumn(8).setResizable(false);
        }

        jPanel12.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 1080, 590));

        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("PARA MODIFICAR UN EMPLEADO, HAGA CLICK DERECHO Y SE LE REDIGIDIRÁ A UNA NUEVA VENTANA.");
        jPanel12.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, 30));

        ModificarEmpleado.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 660));

        ModificarInfoAdmin.addTab("tab3", ModificarEmpleado);

        VisualizarEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        visualizarEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Primer nombre", "Segundo nombre", "Primer apellido", "Segundo Apellido", "Edad", "Identificación", "Correo", "Telefono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        visualizarEmpleados.setComponentPopupMenu(jPopupMenu3);
        visualizarEmpleados.setRowHeight(40);
        jScrollPane3.setViewportView(visualizarEmpleados);
        if (visualizarEmpleados.getColumnModel().getColumnCount() > 0) {
            visualizarEmpleados.getColumnModel().getColumn(0).setResizable(false);
            visualizarEmpleados.getColumnModel().getColumn(1).setResizable(false);
            visualizarEmpleados.getColumnModel().getColumn(2).setResizable(false);
            visualizarEmpleados.getColumnModel().getColumn(3).setResizable(false);
            visualizarEmpleados.getColumnModel().getColumn(4).setResizable(false);
            visualizarEmpleados.getColumnModel().getColumn(5).setResizable(false);
            visualizarEmpleados.getColumnModel().getColumn(6).setResizable(false);
            visualizarEmpleados.getColumnModel().getColumn(7).setResizable(false);
        }

        jPanel16.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 1120, 600));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Lista de empleados que se han unido a voltrix car y han iniciado un mejor futuro.");
        jPanel16.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        VisualizarEmpleado.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 710));

        ModificarInfoAdmin.addTab("tab4", VisualizarEmpleado);

        AñadirMoto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        VisualizarMoto2.setBackground(new java.awt.Color(255, 255, 255));
        VisualizarMoto2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("PARA AÑADIR UNA NUEVA MOTO INGRESE LOS DETALLES QUE SE LE PIDEN A CONTINUACIÓN:");
        VisualizarMoto2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, 80));

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Seleccione el color de la moto:");
        VisualizarMoto2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 260, 30));

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Ingrese el tipo de moto:");
        VisualizarMoto2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, 30));

        comboBoxTipoMoto2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SemiAutomatica", "Boxer", "SemiDeportiva", "Deportiva", "Chopper", "Naked", "SuperSport", "Scooter" }));
        VisualizarMoto2.add(comboBoxTipoMoto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 170, 30));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Marca:");
        VisualizarMoto2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 70, -1));

        jPanel8.setBackground(new java.awt.Color(0, 204, 51));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        VisualizarMoto2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 30, 30));

        jPanel9.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        VisualizarMoto2.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 30, 30));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        VisualizarMoto2.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 30, 30));

        jPanel11.setBackground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        VisualizarMoto2.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 30, 30));

        MarcaMotos.setText("Matca");
        VisualizarMoto2.add(MarcaMotos, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 176, 140, 30));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Añadir");
        VisualizarMoto2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, -1, -1));

        AñadirMoto.add(VisualizarMoto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 660));

        ModificarInfoAdmin.addTab("tab5", AñadirMoto);

        EliminarMoto.setBackground(new java.awt.Color(255, 255, 255));
        EliminarMoto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaMotos.setBackground(new java.awt.Color(246, 246, 246));
        tablaMotos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaMotos.setForeground(new java.awt.Color(246, 246, 246));

        tablaDeCarrito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Modelo", "Marca", "Tipo motor", "Cilindraje", "Precio", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDeCarrito.setComponentPopupMenu(jPopupMenu1);
        tablaDeCarrito.setRowHeight(25);
        tablaDeCarrito.setSelectionBackground(new java.awt.Color(51, 0, 0));
        tablaDeCarrito.setShowVerticalLines(true);
        tablaMotos.setViewportView(tablaDeCarrito);

        EliminarMoto.add(tablaMotos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 1090, 570));

        jLabel13.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Seleccione la moto que desea eliminar y dé click derecho para borrarla. ");
        EliminarMoto.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, -1, 50));

        ModificarInfoAdmin.addTab("tab6", EliminarMoto);

        ModificarMoto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        ModificarInfoAdmin.addTab("tab7", ModificarMoto);

        VisualizarMoto1.setBackground(new java.awt.Color(255, 255, 255));
        VisualizarMoto1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("LISTA DE MOTOS:");
        VisualizarMoto1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, -1, 80));

        ModificarInfoAdmin.addTab("tab8", VisualizarMoto1);

        panelExtraSinuso.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        ModificarInfoAdmin.addTab("tab9", panelExtraSinuso);

        modifcarEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setForeground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-user-circle-34.png"))); // NOI18N
        jPanel15.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 40, 50));

        jLabel33.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Ingrese el primer nombre:");
        jPanel15.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, 20));

        txtNuevoPrimerNombre.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevoPrimerNombre.setBorder(null);
        txtNuevoPrimerNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoPrimerNombreActionPerformed(evt);
            }
        });
        jPanel15.add(txtNuevoPrimerNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 350, 30));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jPanel15.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 350, 20));

        jLabel34.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Ingrese el segundo nombre:");
        jPanel15.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, 20));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-user-circle-34.png"))); // NOI18N
        jPanel15.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 40, 50));

        Nombre2Field1.setForeground(new java.awt.Color(0, 0, 0));
        Nombre2Field1.setBorder(null);
        Nombre2Field1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Nombre2Field1ActionPerformed(evt);
            }
        });
        jPanel15.add(Nombre2Field1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 350, 30));

        jSeparator12.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        jPanel15.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 350, 20));

        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Ingrese el primer apellido:");
        jPanel15.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, 20));

        Apellido1Field1.setForeground(new java.awt.Color(0, 0, 0));
        Apellido1Field1.setBorder(null);
        Apellido1Field1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Apellido1Field1ActionPerformed(evt);
            }
        });
        jPanel15.add(Apellido1Field1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 350, 30));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-user-circle-34.png"))); // NOI18N
        jPanel15.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 40, 50));

        jSeparator13.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        jPanel15.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 350, 20));

        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Ingrese el segundo apellido:");
        jPanel15.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, 20));

        txtNuevoSegundoApellido.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevoSegundoApellido.setBorder(null);
        txtNuevoSegundoApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoSegundoApellidoActionPerformed(evt);
            }
        });
        jPanel15.add(txtNuevoSegundoApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 350, 30));

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-user-circle-34.png"))); // NOI18N
        jPanel15.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 40, 50));

        jSeparator14.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));
        jPanel15.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 340, 20));

        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("Ingrese el correo:");
        jPanel15.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, 20));

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-email-34 (1).png"))); // NOI18N
        jPanel15.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 40, 50));

        txtNuevoCorreo.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevoCorreo.setBorder(null);
        txtNuevoCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoCorreoActionPerformed(evt);
            }
        });
        jPanel15.add(txtNuevoCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 350, 30));

        jSeparator15.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator15.setForeground(new java.awt.Color(0, 0, 0));
        jPanel15.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 350, 20));

        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Ingrese la cedula:");
        jPanel15.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, -1, -1));

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-id-42.png"))); // NOI18N
        jPanel15.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 40, 50));

        txtNuevaCedula.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevaCedula.setBorder(null);
        txtNuevaCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevaCedulaActionPerformed(evt);
            }
        });
        jPanel15.add(txtNuevaCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 350, 30));

        jSeparator16.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator16.setForeground(new java.awt.Color(0, 0, 0));
        jPanel15.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, 350, 20));

        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("Ingrese el número telefonico:");
        jPanel15.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, -1, -1));

        jLabel45.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("LOS CAMPOS QUE PERMANEZCAN IGUAL NO SERÁN MODIFICADOS.");
        jPanel15.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, -1, -1));

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-telephone-32.png"))); // NOI18N
        jPanel15.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 40, 50));

        txtNuevoTelefono.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevoTelefono.setBorder(null);
        txtNuevoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoTelefonoActionPerformed(evt);
            }
        });
        jPanel15.add(txtNuevoTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 430, 350, 30));

        jSeparator17.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));
        jPanel15.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 460, 350, 20));

        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setText("Ingrese la edad:");
        jPanel15.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 466, -1, 20));

        txtNuevaEdad.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevaEdad.setBorder(null);
        txtNuevaEdad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevaEdadActionPerformed(evt);
            }
        });
        jPanel15.add(txtNuevaEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, 350, 30));

        jSeparator18.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator18.setForeground(new java.awt.Color(0, 0, 0));
        jPanel15.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, 350, 20));

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-age-32.png"))); // NOI18N
        jPanel15.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, 40, 50));

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/unnamed-removebg-preview.png"))); // NOI18N
        jLabel49.setText("jLabel28");
        jPanel15.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 110, 440, 350));

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("VOLTRIX S.A.S COMPANY SOLO MANEJA CONTRATO A TERMINO INDEFINIDO, SEGÚN LA LEY 2395 DEL 2025");
        jPanel15.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 530, 390, -1));

        btnModificarEmpleado.setBackground(new java.awt.Color(0, 0, 0));
        btnModificarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarEmpleado.setText("Modificar");
        btnModificarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEmpleadoActionPerformed(evt);
            }
        });
        jPanel15.add(btnModificarEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 590, -1, -1));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1150, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 1150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        modifcarEmpleado.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 660));

        ModificarInfoAdmin.addTab("tab11", modifcarEmpleado);

        jPanel1.add(ModificarInfoAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 1140, 740));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 1140, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void primerNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_primerNombreActionPerformed
        //boton ya creado. agrega el codigo justamente necesario aqui
    }//GEN-LAST:event_primerNombreActionPerformed

    private void segundoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segundoNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_segundoNombreActionPerformed

    private void primerApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_primerApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_primerApellidoActionPerformed

    private void segundoApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segundoApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_segundoApellidoActionPerformed

    private void correoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_correoActionPerformed

    private void cedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cedulaActionPerformed

    private void telefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonoActionPerformed

    private void edadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edadActionPerformed

    private void checkBoxTerminosYConcidionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxTerminosYConcidionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxTerminosYConcidionesActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        int filaSeleccionada = tablaModificacion.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un empleado primero");
            return;
        }

        String identificacion = (String) tablaModificacion.getValueAt(filaSeleccionada, 5);

        Empleado empSeleccionado = empleadoController.buscarPorIdentificacion(identificacion);

        cargarDatosEmpleado(empSeleccionado);

        ModificarInfoAdmin.setSelectedComponent(modifcarEmpleado);


    }//GEN-LAST:event_modificarActionPerformed

    private void txtNuevoPrimerNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoPrimerNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoPrimerNombreActionPerformed

    private void Nombre2Field1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Nombre2Field1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nombre2Field1ActionPerformed

    private void Apellido1Field1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Apellido1Field1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Apellido1Field1ActionPerformed

    private void txtNuevoSegundoApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoSegundoApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoSegundoApellidoActionPerformed

    private void txtNuevoCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoCorreoActionPerformed

    private void txtNuevaCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevaCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevaCedulaActionPerformed

    private void txtNuevoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoTelefonoActionPerformed

    private void txtNuevaEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevaEdadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevaEdadActionPerformed

    private void btnModificarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEmpleadoActionPerformed
        if (empleadoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "No hay empleado seleccionado para modificar");
            return;
        }

        String nuevoPrimerNombre = txtNuevoPrimerNombre.getText().trim().isEmpty() ? empleadoSeleccionado.getPrimerNombre() : txtNuevoPrimerNombre.getText().trim();
        String nuevoSegundoNombre = Nombre2Field1.getText().trim().isEmpty() ? empleadoSeleccionado.getSegundoNombre() : Nombre2Field1.getText().trim();
        String nuevoPrimerApellido = Apellido1Field1.getText().trim().isEmpty() ? empleadoSeleccionado.getPrimerApellido() : Apellido1Field1.getText().trim();
        String nuevoSegundoApellido = txtNuevoSegundoApellido.getText().trim().isEmpty() ? empleadoSeleccionado.getSegundoApellido() : txtNuevoSegundoApellido.getText().trim();
        String nuevaEdad = txtNuevaEdad.getText().trim().isEmpty() ? empleadoSeleccionado.getEdad() : txtNuevaEdad.getText().trim();
        String nuevoCorreo = txtNuevoCorreo.getText().trim().isEmpty() ? empleadoSeleccionado.getCorreo() : txtNuevoCorreo.getText().trim();
        String nuevoTelefono = txtNuevoTelefono.getText().trim().isEmpty() ? empleadoSeleccionado.getTelefono() : txtNuevoTelefono.getText().trim();
        String nuevaCedula = txtNuevaCedula.getText().trim().isEmpty() ? empleadoSeleccionado.getIdentificacion() : txtNuevaCedula.getText().trim();
        
        
        
        if (nuevoPrimerNombre.equals(empleadoSeleccionado.getPrimerNombre())
                && nuevoSegundoNombre.equals(empleadoSeleccionado.getSegundoNombre())
                && nuevoPrimerApellido.equals(empleadoSeleccionado.getPrimerApellido())
                && nuevoSegundoApellido.equals(empleadoSeleccionado.getSegundoApellido())
                && nuevaEdad.equals(empleadoSeleccionado.getEdad())
                && nuevoCorreo.equals(empleadoSeleccionado.getCorreo())
                && nuevoTelefono.equals(empleadoSeleccionado.getTelefono())
                && nuevaCedula.equals(empleadoSeleccionado.getIdentificacion())
                ) {

            JOptionPane.showMessageDialog(this, "No se modificó ningún dato del empleado");
            limpiarCamposModificacion();
            empleadoSeleccionado = null;
            return;
        }

        List<String> errores = empleadoController.actualizarEmpleado(
                empleadoSeleccionado.getIdentificacion(),             
                nuevaCedula,
               nuevoPrimerNombre,
                nuevoSegundoNombre,
                nuevoPrimerApellido,
                nuevoSegundoApellido,
                nuevaEdad,
                nuevoCorreo,
                nuevoTelefono
        );

        if (errores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente");

            cargarEmpleadosEnModificar();
            cargarEmpleados();
            cargarEmpleadosEnEliminar();
            empleadoSeleccionado = null;
            limpiarCamposModificacion();
        } else {
            String mensaje = String.join("\n", errores);
            JOptionPane.showMessageDialog(this, mensaje, "Errores de validación", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnModificarEmpleadoActionPerformed

    private void limpiarCamposModificacion() {
        txtNuevoPrimerNombre.setText("");
        Nombre2Field1.setText("");
        Apellido1Field1.setText("");
        txtNuevoSegundoApellido.setText("");
        txtNuevoCorreo.setText("");
        txtNuevaCedula.setText("");
        txtNuevoTelefono.setText("");
        txtNuevaEdad.setText("");
    }


    private void btnAñadirEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirEmpleadoActionPerformed

        if (!checkBoxTerminosYConcidiones.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Debe aceptar los términos y condiciones de contratación para contratar al empleado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            checkBoxTerminosYConcidiones.requestFocus();
            return;
        }

        if (primerNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El primer nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            primerNombre.requestFocus();
            return;
        }
        if (primerApellido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El primer apellido es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            primerApellido.requestFocus();
            return;
        }
        if (edad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La edad es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            edad.requestFocus();
            return;
        }
        if (cedula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La cédula es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            cedula.requestFocus();
            return;
        }
        if (correo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El correo es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            correo.requestFocus();
            return;
        }
        if (telefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El teléfono es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            telefono.requestFocus();
            return;
        }
        
        
        String idEmpleado = empleadoController.generarIdEmpleado();
        String primerNombr = primerNombre.getText().trim();
        String segundoNombr = segundoNombre.getText().trim();
        String primerApell = primerApellido.getText().trim();
        String segundoApell = segundoApellido.getText().trim();
        String correoE = correo.getText().trim();
        String cedulaI = cedula.getText().trim();
        String edadA = edad.getText().trim();
        String celular = telefono.getText().trim();
        
        if (empleadoController.existeEmpleadoPorCedula(cedulaI)) {
        JOptionPane.showMessageDialog(this, "Ya existe un empleado registrado con esta cédula", "Error", JOptionPane.ERROR_MESSAGE);
        cedula.requestFocus();
        return;
    }

        Empleado empleado = new Empleado(idEmpleado, primerNombr, segundoNombr,
                primerApell, segundoApell, edadA,
                cedulaI, correoE, celular);

        boolean registrado = empleadoController.registrarEmpleado(empleado);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Empleado registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarEmpleados();
            cargarEmpleadosEnEliminar();
            cargarEmpleadosEnModificar();
           checkBoxTerminosYConcidiones.setSelected(false);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar empleado", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnAñadirEmpleadoActionPerformed

    private void limpiarCampos() {
        primerNombre.setText("");
        segundoNombre.setText("");
        primerApellido.setText("");
        segundoApellido.setText("");
        edad.setText("");
        cedula.setText("");
        correo.setText("");
        telefono.setText("");
    }

    private void validacionAñadirYModicicarEmpleado() {
        validarSoloLetras(primerNombre);
        validarSoloLetras(segundoNombre);
        validarSoloLetras(primerApellido);
        validarSoloLetras(segundoApellido);
        validarSoloEnteros(edad);
        validarSoloEnteros(telefono);
        validarSoloEnteros(cedula);
        validarSoloLetras(txtNuevoPrimerNombre);
        validarSoloLetras(Nombre2Field1);
        validarSoloLetras(Apellido1Field1);
        validarSoloLetras(txtNuevoSegundoApellido);
        validarSoloEnteros(txtNuevaEdad);
        validarSoloEnteros(txtNuevoTelefono);

    }

    private void validarSoloLetras(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String texto = campo.getText();
                if (!texto.matches("[a-zA-Z ]*")) {
                    JOptionPane.showMessageDialog(null, "Solo letras permitidas");
                    campo.setText(texto.replaceAll("[^a-zA-Z ]", ""));
                }
            }
        });
    }

    private void validarSoloEnteros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String texto = campo.getText();
                if (!texto.matches("\\d*")) {
                    JOptionPane.showMessageDialog(null, "Solo números enteros permitidos");
                    campo.setText(texto.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

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
            java.util.logging.Logger.getLogger(Administador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Administador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Administador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Administador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Administador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Apellido1Field1;
    private javax.swing.JPanel AñadirMoto;
    private javax.swing.JMenuItem Despedir;
    private javax.swing.JPanel EliminarEmpleado;
    private javax.swing.JPanel EliminarMoto;
    private javax.swing.JTextField MarcaMotos;
    private javax.swing.JPanel ModificarEmpleado;
    private javax.swing.JTabbedPane ModificarInfoAdmin;
    private javax.swing.JPanel ModificarMoto;
    private javax.swing.JMenuItem MotoEliminar;
    private javax.swing.JTextField Nombre2Field1;
    private javax.swing.JPanel VisualizarEmpleado;
    private javax.swing.JPanel VisualizarMoto1;
    private javax.swing.JPanel VisualizarMoto2;
    private javax.swing.JPanel añadirEmpleado;
    private javax.swing.JButton btnAñadirEmpleado;
    private javax.swing.JButton btnModificarEmpleado;
    private javax.swing.JTextField cedula;
    private javax.swing.JCheckBox checkBoxTerminosYConcidiones;
    private javax.swing.JComboBox<String> comboBoxTipoMoto2;
    private javax.swing.JTextField correo;
    private javax.swing.JTextField edad;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel modifcarEmpleado;
    private javax.swing.JMenuItem modificar;
    private javax.swing.JPanel panelExtraSinuso;
    private javax.swing.JTextField primerApellido;
    private javax.swing.JTextField primerNombre;
    private javax.swing.JTextField segundoApellido;
    private javax.swing.JTextField segundoNombre;
    private javax.swing.JTable tablaDeCarrito;
    private javax.swing.JTable tablaEliminar;
    private javax.swing.JTable tablaModificacion;
    private javax.swing.JScrollPane tablaMotos;
    private javax.swing.JTextField telefono;
    private javax.swing.JTextField txtNuevaCedula;
    private javax.swing.JTextField txtNuevaEdad;
    private javax.swing.JTextField txtNuevoCorreo;
    private javax.swing.JTextField txtNuevoPrimerNombre;
    private javax.swing.JTextField txtNuevoSegundoApellido;
    private javax.swing.JTextField txtNuevoTelefono;
    private javax.swing.JTable visualizarEmpleados;
    // End of variables declaration//GEN-END:variables
}
