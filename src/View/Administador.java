package View;

import Controller.EmpleadoController;
import Controller.MotoController;
import DAO.EmpleadoDAO;
import Model.Entities.Empleado;
import Model.Entities.Moto;
import Model.Entities.PartesMoto;
import Utilidades.ModernTopMenu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Administador extends javax.swing.JFrame {

    EmpleadoController empleadoController = EmpleadoController.getInstance();
    private Empleado empleadoSeleccionado;
    MotoController motoController = MotoController.getInstancia();

    public Administador() {
        initComponents();
        visualizarEmpleados.setComponentPopupMenu(null);
        TablaListarMotos.setComponentPopupMenu(jPopupMenu4);
        TablaModificarMotos.setComponentPopupMenu(jPopupMenu5);

        añadirEmpleado.setUI(null);
        this.setLocationRelativeTo(null);
        cargarEmpleados();
        cargarEmpleadosEnEliminar();
        cargarEmpleadosEnModificar();
        validacionAñadirYModicicarEmpleado();
        cargarMotosEnTabla();
        cargarMotosEnTablaModificar();
        cargarMotosEnTablaEliminar();

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

        if (mainPanel != null) {
            getContentPane().remove(mainPanel);
            getContentPane().add(menuSuperior, BorderLayout.NORTH);
            getContentPane().add(mainPanel, BorderLayout.CENTER);
        } else {
            getContentPane().add(menuSuperior, BorderLayout.NORTH);
        }

        pack();
        setLocationRelativeTo(null);

        MotoEliminar.addActionListener(e -> {
            JTable tabla = (JTable) jPopupMenu1.getInvoker();

            JTable tablaReal = (JTable) tablaMotos.getViewport().getView();
            if (tabla != tablaReal) {
                return;
            }

            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una moto primero.");
                return;
            }

            String estado = tabla.getValueAt(filaSeleccionada, 6).toString();
            if (!estado.equalsIgnoreCase("DISPONIBLE")) {
                JOptionPane.showMessageDialog(this, "Solo puedes eliminar motos con estado DISPONIBLE.", "Acción no permitida", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro que deseas eliminar esta moto?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                int idMoto = (int) tabla.getValueAt(filaSeleccionada, 0);
                MotoController controller = new MotoController();

                boolean eliminado = controller.eliminarMoto(idMoto);

                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Moto eliminada correctamente.");
                    cargarMotosEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar la moto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        PartesMoto.addActionListener(e -> {
            int filaSeleccionada = TablaListarMotos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una moto primero.");
                return;
            }

            int idMoto = (int) TablaListarMotos.getValueAt(filaSeleccionada, 0);
            Moto moto = motoController.buscarPorId(idMoto);

            if (moto == null) {
                JOptionPane.showMessageDialog(this, "No se encontró información para esta moto.");
                return;
            }

            StringBuilder detalles = new StringBuilder();
            detalles.append("ID de la moto: ").append(idMoto).append("\n\n");

            PartesMoto partes = moto.getPartesMoto();
            if (partes == null) {
                JOptionPane.showMessageDialog(this, "Esta moto no tiene partes registradas aún.");
                return;
            }

            if (partes.getMotor() != null) {
                detalles.append("MOTOR\n");
                detalles.append("  Tipo: ").append(partes.getMotor().getTipo()).append("\n");
                detalles.append("  Cilindrada: ").append(partes.getMotor().getCilindrada()).append(" cc\n");
                detalles.append("  Potencia: ").append(partes.getMotor().getPotencia()).append(" HP\n\n");
            }

            if (partes.getTransmision() != null) {
                detalles.append("TRANSMISIÓN\n");
                detalles.append("  Tipo: ").append(partes.getTransmision().getTipoTransmision()).append("\n");
                detalles.append("  Velocidades: ").append(partes.getTransmision().getVelocidades()).append("\n\n");
            }

            if (partes.getChasis() != null) {
                detalles.append("CHASIS\n");
                detalles.append("  Material: ").append(partes.getChasis().getMaterial()).append("\n");
                detalles.append("  Tipo: ").append(partes.getChasis().getTipo()).append("\n\n");
            }

            if (partes.getFrenoDelantero() != null) {
                detalles.append("FRENO DELANTERO\n");
                detalles.append("  Marca: ").append(partes.getFrenoDelantero().getMarca()).append("\n");
                detalles.append("  Material: ").append(partes.getFrenoDelantero().getMaterial()).append("\n\n");
            }

            if (partes.getFrenoTrasero() != null) {
                detalles.append("FRENO TRASERO\n");
                detalles.append("  Marca: ").append(partes.getFrenoTrasero().getMarca()).append("\n");
                detalles.append("  Material: ").append(partes.getFrenoTrasero().getMaterial()).append("\n\n");
            }

            if (partes.getLlantaDelantera() != null) {
                detalles.append("LLANTA DELANTERA\n");
                detalles.append("  Marca: ").append(partes.getLlantaDelantera().getMarca()).append("\n");
                detalles.append("  Modelo: ").append(partes.getLlantaDelantera().getModelo()).append("\n");
                detalles.append("  Medida: ").append(partes.getLlantaDelantera().getMedida()).append("\n\n");
            }

            if (partes.getLlantaTrasera() != null) {
                detalles.append("LLANTA TRASERA\n");
                detalles.append("  Marca: ").append(partes.getLlantaTrasera().getMarca()).append("\n");
                detalles.append("  Modelo: ").append(partes.getLlantaTrasera().getModelo()).append("\n");
                detalles.append("  Medida: ").append(partes.getLlantaTrasera().getMedida()).append("\n\n");
            }

            if (partes.getAsiento() != null) {
                detalles.append("ASIENTO\n");
                detalles.append("  Material: ").append(partes.getAsiento().getMaterial()).append("\n");
                detalles.append("  Capacidad: ").append(partes.getAsiento().getCapacidad()).append("\n\n");
            }

            JTextArea areaTexto = new JTextArea(detalles.toString());
            areaTexto.setEditable(false);
            areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 13));
            areaTexto.setBackground(new Color(245, 245, 245));
            areaTexto.setMargin(new Insets(10, 10, 10, 10));

            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(400, 350));

            JOptionPane.showMessageDialog(this, scroll, "Detalles de la Moto", JOptionPane.PLAIN_MESSAGE);

        });

        PartesMoto2.addActionListener(e -> {
            int filaSeleccionada = TablaModificarMotos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una moto primero.");
                return;
            }

            int idMoto = (int) TablaModificarMotos.getValueAt(filaSeleccionada, 0);
            Moto moto = motoController.buscarPorId(idMoto);

            if (moto == null) {
                JOptionPane.showMessageDialog(this, "No se encontró información para esta moto.");
                return;
            }

            StringBuilder detalles = new StringBuilder();
            detalles.append("ID de la moto: ").append(idMoto).append("\n\n");

            PartesMoto partes = moto.getPartesMoto();
            if (partes == null) {
                JOptionPane.showMessageDialog(this, "Esta moto no tiene partes registradas aún.");
                return;
            }

            if (partes.getMotor() != null) {
                detalles.append("MOTOR\n");
                detalles.append("  Tipo: ").append(partes.getMotor().getTipo()).append("\n");
                detalles.append("  Cilindrada: ").append(partes.getMotor().getCilindrada()).append(" cc\n");
                detalles.append("  Potencia: ").append(partes.getMotor().getPotencia()).append(" HP\n\n");
            }

            if (partes.getTransmision() != null) {
                detalles.append("TRANSMISIÓN\n");
                detalles.append("  Tipo: ").append(partes.getTransmision().getTipoTransmision()).append("\n");
                detalles.append("  Velocidades: ").append(partes.getTransmision().getVelocidades()).append("\n\n");
            }

            if (partes.getChasis() != null) {
                detalles.append("CHASIS\n");
                detalles.append("  Material: ").append(partes.getChasis().getMaterial()).append("\n");
                detalles.append("  Tipo: ").append(partes.getChasis().getTipo()).append("\n\n");
            }

            if (partes.getFrenoDelantero() != null) {
                detalles.append("FRENO DELANTERO\n");
                detalles.append("  Marca: ").append(partes.getFrenoDelantero().getMarca()).append("\n");
                detalles.append("  Material: ").append(partes.getFrenoDelantero().getMaterial()).append("\n\n");
            }

            if (partes.getFrenoTrasero() != null) {
                detalles.append("FRENO TRASERO\n");
                detalles.append("  Marca: ").append(partes.getFrenoTrasero().getMarca()).append("\n");
                detalles.append("  Material: ").append(partes.getFrenoTrasero().getMaterial()).append("\n\n");
            }

            if (partes.getLlantaDelantera() != null) {
                detalles.append("LLANTA DELANTERA\n");
                detalles.append("  Marca: ").append(partes.getLlantaDelantera().getMarca()).append("\n");
                detalles.append("  Modelo: ").append(partes.getLlantaDelantera().getModelo()).append("\n");
                detalles.append("  Medida: ").append(partes.getLlantaDelantera().getMedida()).append("\n\n");
            }

            if (partes.getLlantaTrasera() != null) {
                detalles.append("LLANTA TRASERA\n");
                detalles.append("  Marca: ").append(partes.getLlantaTrasera().getMarca()).append("\n");
                detalles.append("  Modelo: ").append(partes.getLlantaTrasera().getModelo()).append("\n");
                detalles.append("  Medida: ").append(partes.getLlantaTrasera().getMedida()).append("\n\n");
            }

            if (partes.getAsiento() != null) {
                detalles.append("ASIENTO\n");
                detalles.append("  Material: ").append(partes.getAsiento().getMaterial()).append("\n");
                detalles.append("  Capacidad: ").append(partes.getAsiento().getCapacidad()).append("\n\n");
            }

            JTextArea areaTexto = new JTextArea(detalles.toString());
            areaTexto.setEditable(false);
            areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 13));
            areaTexto.setBackground(new Color(245, 245, 245));
            areaTexto.setMargin(new Insets(10, 10, 10, 10));

            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(400, 350));

            JOptionPane.showMessageDialog(this, scroll, "Detalles de la Moto", JOptionPane.PLAIN_MESSAGE);

        });

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

    private void cargarMotosEnTablaEliminar() {
        MotoController controller = new MotoController();
        List<Moto> motos = controller.listarMotos();

        JTable tabla = (JTable) tablaMotos.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();

        model.setRowCount(0);

        for (Moto m : motos) {
            if (m.getPlaca() != null && m.getPlaca().startsWith("BSE-")) {
                continue;
            }

            model.addRow(new Object[]{
                m.getIdMoto(),
                m.getModelo(),
                m.getMarca(),
                m.getTipoMoto(),
                m.getCilindraje(),
                m.getPrecio(),
                m.getEstado()
            });
        }
    }

    private void cargarMotosEnTabla() {
        MotoController controller = new MotoController();
        List<Moto> motos = controller.listarMotos();

        DefaultTableModel model = (DefaultTableModel) TablaListarMotos.getModel();

        model.setRowCount(0);

        for (Moto m : motos) {
            model.addRow(new Object[]{
                m.getIdMoto(),
                m.getTipoMoto(),
                m.getMarca(),
                m.getModelo(),
                m.getTipoColorMoto(),
                m.getCilindraje(),
                m.getPrecio(),
                m.getFechaIngreso(),
                m.isTieneParrilla() ? "Sí" : "No",
                m.isTieneMaletero() ? "Sí" : "No",
                m.getPlaca(),
                m.getEstado()
            });
        }
    }

    private void cargarMotosEnTablaModificar() {
        MotoController controller = new MotoController();
        List<Moto> motos = controller.listarMotos();

        DefaultTableModel model = (DefaultTableModel) TablaModificarMotos.getModel();

        model.setRowCount(0);

        for (Moto m : motos) {
            model.addRow(new Object[]{
                m.getIdMoto(),
                m.getTipoMoto(),
                m.getMarca(),
                m.getModelo(),
                m.getTipoColorMoto(),
                m.getCilindraje(),
                m.getPrecio(),
                m.getFechaIngreso(),
                m.isTieneParrilla() ? "Sí" : "No",
                m.isTieneMaletero() ? "Sí" : "No",
                m.getPlaca(),
                m.getEstado()
            });
        }
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
        jPopupMenu4 = new javax.swing.JPopupMenu();
        PartesMoto = new javax.swing.JMenuItem();
        jPopupMenu5 = new javax.swing.JPopupMenu();
        PartesMoto2 = new javax.swing.JMenuItem();
        ModificarMotoItem = new javax.swing.JMenuItem();
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
        comboBoxColorMoto = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        MarcaMotos = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        SpinnerCantidadMotos = new javax.swing.JSpinner();
        jLabel51 = new javax.swing.JLabel();
        comboBoxTipoMoto3 = new javax.swing.JComboBox<>();
        EliminarMoto = new javax.swing.JPanel();
        tablaMotos = new javax.swing.JScrollPane();
        tablaDeCarrito = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        ModificarMoto = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaModificarMotos = new javax.swing.JTable();
        jLabel52 = new javax.swing.JLabel();
        VisualizarMoto1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaListarMotos = new javax.swing.JTable();
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
        PanelParaModificarMoto = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtIdMotoModificar = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        txtNuevaMarcaMoto = new javax.swing.JTextField();
        jSeparator19 = new javax.swing.JSeparator();
        comboBoxTipoFreno = new javax.swing.JComboBox<>();
        jLabel55 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        comboBoxNuevoColorMoto1 = new javax.swing.JComboBox<>();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        comboBoxMotoBasePlaca = new javax.swing.JComboBox<>();
        comboBoxTipoLlanta = new javax.swing.JComboBox<>();
        jLabel80 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        NuevaMedidaLlantaCombo = new javax.swing.JComboBox<>();
        txtNuevaMarcaLlanta = new javax.swing.JTextField();
        jSeparator20 = new javax.swing.JSeparator();
        NuevoMaterialLlantaCombo = new javax.swing.JComboBox<>();
        txtNuevoModeloLlanta = new javax.swing.JTextField();
        jSeparator21 = new javax.swing.JSeparator();
        txtNuevoModeloMoto = new javax.swing.JTextField();
        jSeparator22 = new javax.swing.JSeparator();
        NuevoCilindrajeMoto = new javax.swing.JTextField();
        jSeparator23 = new javax.swing.JSeparator();
        TieneMaleteroNueva = new javax.swing.JComboBox<>();
        TieneParrillaNueva = new javax.swing.JComboBox<>();
        txtNuevaMarcaFreno = new javax.swing.JTextField();
        jSeparator24 = new javax.swing.JSeparator();
        txtNuevoModeloFreno = new javax.swing.JTextField();
        jSeparator25 = new javax.swing.JSeparator();
        NuevoMaterialChasis = new javax.swing.JComboBox<>();
        NuevoTipoMotor = new javax.swing.JComboBox<>();
        NuevoTipoChasis = new javax.swing.JComboBox<>();
        NuevaCilindradaMotor = new javax.swing.JTextField();
        jSeparator26 = new javax.swing.JSeparator();
        NuevaPotenciaMotor = new javax.swing.JTextField();
        jSeparator27 = new javax.swing.JSeparator();
        NuevoMaterialFreno = new javax.swing.JComboBox<>();
        NuevoTipoTransmision = new javax.swing.JComboBox<>();
        NuevaCapacidadAsiento = new javax.swing.JComboBox<>();
        NuevoMaterialAsiento = new javax.swing.JComboBox<>();
        BotonModificarMotoBase = new javax.swing.JButton();
        BotonModificarMotoIndividual1 = new javax.swing.JButton();
        NuevaVelocidadesTransmision = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();

        MotoEliminar.setText("Eliminar esta moto");
        MotoEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MotoEliminarActionPerformed(evt);
            }
        });
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

        PartesMoto.setText("Ver partes");
        PartesMoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PartesMotoActionPerformed(evt);
            }
        });
        jPopupMenu4.add(PartesMoto);

        PartesMoto2.setText("Ver partes");
        PartesMoto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PartesMoto2ActionPerformed(evt);
            }
        });
        jPopupMenu5.add(PartesMoto2);

        ModificarMotoItem.setText("Modificar");
        ModificarMotoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarMotoItemActionPerformed(evt);
            }
        });
        jPopupMenu5.add(ModificarMotoItem);

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
        VisualizarMoto2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, -1, 80));

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Seleccione el color de la moto:");
        VisualizarMoto2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 260, 30));

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Ingrese el tipo de moto:");
        VisualizarMoto2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, 30));

        comboBoxColorMoto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Verde", "Rojo", "Negro", "Blanco" }));
        VisualizarMoto2.add(comboBoxColorMoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 170, 30));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Cantidad de motos a añadir:");
        VisualizarMoto2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 240, -1));

        MarcaMotos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MarcaMotosActionPerformed(evt);
            }
        });
        VisualizarMoto2.add(MarcaMotos, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 176, 140, 30));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Añadir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        VisualizarMoto2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 280, -1, -1));
        VisualizarMoto2.add(SpinnerCantidadMotos, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, 90, -1));

        jLabel51.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setText("Marca:");
        VisualizarMoto2.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 70, -1));

        comboBoxTipoMoto3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "SemiAutomatica", "Boxer", "SemiDeportiva", "Deportiva", "Chopper", "Naked", "SuperSport", "Scooter" }));
        VisualizarMoto2.add(comboBoxTipoMoto3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 170, 30));

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
                "Id", "Modelo", "Marca", "Tipo moto", "Cilindraje", "Precio", "Estado"
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

        ModificarMoto.setBackground(new java.awt.Color(255, 255, 255));
        ModificarMoto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaModificarMotos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Tipo moto", "Marca", "Modelo", "Color", "Cilindraje", "Precio", "Fecha ingreso", "Tiene parrilla", "Tiene maletero", "Placa", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(TablaModificarMotos);
        if (TablaModificarMotos.getColumnModel().getColumnCount() > 0) {
            TablaModificarMotos.getColumnModel().getColumn(0).setResizable(false);
            TablaModificarMotos.getColumnModel().getColumn(1).setResizable(false);
            TablaModificarMotos.getColumnModel().getColumn(2).setResizable(false);
            TablaModificarMotos.getColumnModel().getColumn(3).setResizable(false);
            TablaModificarMotos.getColumnModel().getColumn(4).setResizable(false);
            TablaModificarMotos.getColumnModel().getColumn(5).setResizable(false);
            TablaModificarMotos.getColumnModel().getColumn(6).setResizable(false);
            TablaModificarMotos.getColumnModel().getColumn(7).setResizable(false);
            TablaModificarMotos.getColumnModel().getColumn(8).setResizable(false);
            TablaModificarMotos.getColumnModel().getColumn(9).setResizable(false);
            TablaModificarMotos.getColumnModel().getColumn(10).setResizable(false);
            TablaModificarMotos.getColumnModel().getColumn(11).setResizable(false);
        }

        ModificarMoto.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 1100, 510));

        jLabel52.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 0));
        jLabel52.setText("Seleccione la moto que desea modificar ");
        ModificarMoto.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, -1, 50));

        ModificarInfoAdmin.addTab("tab7", ModificarMoto);

        VisualizarMoto1.setBackground(new java.awt.Color(255, 255, 255));
        VisualizarMoto1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("LISTA DE MOTOS:");
        VisualizarMoto1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, 80));

        TablaListarMotos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Tipo moto", "Marca", "Modelo", "Color", "Cilindraje", "Precio", "Fecha ingreso", "Tiene parrilla", "Tiene maletero", "Placa", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(TablaListarMotos);
        if (TablaListarMotos.getColumnModel().getColumnCount() > 0) {
            TablaListarMotos.getColumnModel().getColumn(0).setResizable(false);
            TablaListarMotos.getColumnModel().getColumn(1).setResizable(false);
            TablaListarMotos.getColumnModel().getColumn(2).setResizable(false);
            TablaListarMotos.getColumnModel().getColumn(3).setResizable(false);
            TablaListarMotos.getColumnModel().getColumn(4).setResizable(false);
            TablaListarMotos.getColumnModel().getColumn(5).setResizable(false);
            TablaListarMotos.getColumnModel().getColumn(6).setResizable(false);
            TablaListarMotos.getColumnModel().getColumn(7).setResizable(false);
            TablaListarMotos.getColumnModel().getColumn(8).setResizable(false);
            TablaListarMotos.getColumnModel().getColumn(9).setResizable(false);
            TablaListarMotos.getColumnModel().getColumn(10).setResizable(false);
            TablaListarMotos.getColumnModel().getColumn(11).setResizable(false);
        }

        VisualizarMoto1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 1100, 510));

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

        PanelParaModificarMoto.setBackground(new java.awt.Color(255, 255, 255));
        PanelParaModificarMoto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel53.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 0, 0));
        jLabel53.setText("Llanta:");
        PanelParaModificarMoto.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, -1, -1));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(txtIdMotoModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 190, 20));

        jLabel56.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 0, 0));
        jLabel56.setText("Id de la moto seleccionada:");
        jPanel4.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        PanelParaModificarMoto.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 360, 40));

        jLabel54.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 0, 0));
        jLabel54.setText("Nuevo color:");
        PanelParaModificarMoto.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, -1, 20));

        jLabel57.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 0, 0));
        PanelParaModificarMoto.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 340, -1, 20));

        txtNuevaMarcaMoto.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevaMarcaMoto.setBorder(null);
        txtNuevaMarcaMoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevaMarcaMotoActionPerformed(evt);
            }
        });
        PanelParaModificarMoto.add(txtNuevaMarcaMoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 280, 30));

        jSeparator19.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator19.setForeground(new java.awt.Color(0, 0, 0));
        PanelParaModificarMoto.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 280, 20));

        comboBoxTipoFreno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Freno Delantero", "Freno Trasero", "Ambos" }));
        PanelParaModificarMoto.add(comboBoxTipoFreno, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 180, 160, 20));

        jLabel55.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 0));
        jLabel55.setText("LOS CAMPOS QUE PERMANEZCAN IGUAL NO SERÁN MODIFICADOS.");
        PanelParaModificarMoto.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, -1, -1));

        jLabel58.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 0, 0));
        jLabel58.setText("Los cambios aplicados se reflejarán en todas las unidades disponibles del mismo tipo de moto.");
        PanelParaModificarMoto.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 100, -1, 20));

        jLabel59.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 0, 0));
        jLabel59.setText("Modificar moto individual");
        PanelParaModificarMoto.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, -1, -1));

        jLabel60.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 0, 0));
        jLabel60.setText("Actualiza la información de una sola moto seleccionada (marca o color).");
        PanelParaModificarMoto.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, 20));

        jLabel61.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(0, 0, 0));
        jLabel61.setText("Nueva marca:");
        PanelParaModificarMoto.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, 20));

        comboBoxNuevoColorMoto1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Verde", "Rojo", "Negro", "Blanco" }));
        PanelParaModificarMoto.add(comboBoxNuevoColorMoto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 280, 30));

        jLabel62.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(0, 0, 0));
        jLabel62.setText("Placa de la moto base*:");
        PanelParaModificarMoto.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, -1, 20));

        jLabel63.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 0, 0));
        jLabel63.setText("Nuevo modelo:");
        PanelParaModificarMoto.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, -1, 20));

        jLabel64.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(0, 0, 0));
        jLabel64.setText("Tiene parrilla:");
        PanelParaModificarMoto.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, -1, 20));

        jLabel65.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 0));
        jLabel65.setText("Tiene maletero:");
        PanelParaModificarMoto.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, -1, 20));

        jLabel66.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 0, 0));
        jLabel66.setText("Nuevo modelo:");
        PanelParaModificarMoto.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 430, -1, 20));

        jLabel67.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 0, 0));
        jLabel67.setText("Nuevo cilindraje:");
        PanelParaModificarMoto.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 270, -1, 20));

        jLabel68.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(0, 0, 0));
        jLabel68.setText("Nueva Medida llanta:");
        PanelParaModificarMoto.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 340, -1, 20));

        jLabel69.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(0, 0, 0));
        jLabel69.setText("Actualizar moto base y unidades del mismo tipo");
        PanelParaModificarMoto.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 80, -1, -1));

        jLabel70.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 0));
        jLabel70.setText("Nueva marca:");
        PanelParaModificarMoto.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, -1, 20));

        jLabel71.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 0, 0));
        jLabel71.setText("Nuevo material:");
        PanelParaModificarMoto.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 400, -1, 20));

        jLabel72.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 0, 0));
        jLabel72.setText("Chasis:");
        PanelParaModificarMoto.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 470, -1, -1));

        jLabel75.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(0, 0, 0));
        jLabel75.setText("Nuevo material:");
        PanelParaModificarMoto.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 500, -1, 20));

        jLabel76.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(0, 0, 0));
        jLabel76.setText("Nuevo tipo:");
        PanelParaModificarMoto.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 530, -1, 20));

        jLabel73.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(0, 0, 0));
        jLabel73.setText("Motor:");
        PanelParaModificarMoto.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 570, -1, -1));

        jLabel77.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(0, 0, 0));
        jLabel77.setText("nueva potencia:");
        PanelParaModificarMoto.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 660, -1, 20));

        jLabel78.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 0, 0));
        jLabel78.setText("Nuevo tipo:");
        PanelParaModificarMoto.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 630, -1, 20));

        jLabel79.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(0, 0, 0));
        jLabel79.setText("nueva cilindrada:");
        PanelParaModificarMoto.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 600, -1, 20));

        jLabel74.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(0, 0, 0));
        jLabel74.setText("Freno:");
        PanelParaModificarMoto.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 180, -1, -1));

        jLabel81.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(0, 0, 0));
        jLabel81.setText("Nueva marca:");
        PanelParaModificarMoto.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 210, -1, 20));

        jLabel82.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(0, 0, 0));
        jLabel82.setText("Nuevo material:");
        PanelParaModificarMoto.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 240, -1, 20));

        jLabel83.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(0, 0, 0));
        jLabel83.setText("Nuevo modelo:");
        PanelParaModificarMoto.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 270, -1, 20));

        comboBoxMotoBasePlaca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "BSE-DEPORTIVA", "BSE-NAKED", "BSE-SEMIAUTOMATICA", "BSE-SEMIDEPORTIVA", "BSE-BOXER", "BSE-CHOPPER", "BSE-SUPERSPORT", "BSE-SCOOTER" }));
        PanelParaModificarMoto.add(comboBoxMotoBasePlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 130, 280, 30));

        comboBoxTipoLlanta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Llanta Delantera", "Llanta Trasera", "Ambas" }));
        PanelParaModificarMoto.add(comboBoxTipoLlanta, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 310, 180, 20));

        jLabel80.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(0, 0, 0));
        jLabel80.setText("Asiento:");
        PanelParaModificarMoto.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 320, -1, -1));

        jLabel84.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(0, 0, 0));
        jLabel84.setText("Nueva capacidad:");
        PanelParaModificarMoto.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 350, -1, 20));

        jLabel85.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(0, 0, 0));
        jLabel85.setText("Nuevo material:");
        PanelParaModificarMoto.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 380, -1, 20));

        jLabel86.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(0, 0, 0));
        jLabel86.setText("Transmision:");
        PanelParaModificarMoto.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 430, -1, -1));

        jLabel87.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(0, 0, 0));
        jLabel87.setText("Nuevo tipo:");
        PanelParaModificarMoto.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 460, -1, 20));

        jLabel88.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 0, 0));
        jLabel88.setText("Velocidades:");
        PanelParaModificarMoto.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 490, -1, 20));

        NuevaMedidaLlantaCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "R17_120_70", "R17_180_55", "R16_100_80" }));
        PanelParaModificarMoto.add(NuevaMedidaLlantaCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 340, 160, -1));

        txtNuevaMarcaLlanta.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevaMarcaLlanta.setBorder(null);
        txtNuevaMarcaLlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevaMarcaLlantaActionPerformed(evt);
            }
        });
        PanelParaModificarMoto.add(txtNuevaMarcaLlanta, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, 190, 20));

        jSeparator20.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator20.setForeground(new java.awt.Color(0, 0, 0));
        PanelParaModificarMoto.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 390, 190, 20));

        NuevoMaterialLlantaCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "CAUCHO", "ALEACION", "COMPUESTO", "ACERO" }));
        PanelParaModificarMoto.add(NuevoMaterialLlantaCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 400, 190, -1));

        txtNuevoModeloLlanta.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevoModeloLlanta.setBorder(null);
        txtNuevoModeloLlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoModeloLlantaActionPerformed(evt);
            }
        });
        PanelParaModificarMoto.add(txtNuevoModeloLlanta, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 430, 190, 20));

        jSeparator21.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator21.setForeground(new java.awt.Color(0, 0, 0));
        PanelParaModificarMoto.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 450, 190, 20));

        txtNuevoModeloMoto.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevoModeloMoto.setBorder(null);
        txtNuevoModeloMoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoModeloMotoActionPerformed(evt);
            }
        });
        PanelParaModificarMoto.add(txtNuevoModeloMoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, 180, 20));

        jSeparator22.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator22.setForeground(new java.awt.Color(0, 0, 0));
        PanelParaModificarMoto.add(jSeparator22, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 180, 20));

        NuevoCilindrajeMoto.setForeground(new java.awt.Color(0, 0, 0));
        NuevoCilindrajeMoto.setBorder(null);
        NuevoCilindrajeMoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoCilindrajeMotoActionPerformed(evt);
            }
        });
        PanelParaModificarMoto.add(NuevoCilindrajeMoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 270, 180, 20));

        jSeparator23.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator23.setForeground(new java.awt.Color(0, 0, 0));
        PanelParaModificarMoto.add(jSeparator23, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 290, 180, 20));

        TieneMaleteroNueva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Si", "No" }));
        PanelParaModificarMoto.add(TieneMaleteroNueva, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 240, 180, -1));

        TieneParrillaNueva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Si", "No" }));
        PanelParaModificarMoto.add(TieneParrillaNueva, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, 180, -1));

        txtNuevaMarcaFreno.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevaMarcaFreno.setBorder(null);
        txtNuevaMarcaFreno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevaMarcaFrenoActionPerformed(evt);
            }
        });
        PanelParaModificarMoto.add(txtNuevaMarcaFreno, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 210, 190, 20));

        jSeparator24.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator24.setForeground(new java.awt.Color(0, 0, 0));
        PanelParaModificarMoto.add(jSeparator24, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 230, 190, 20));

        txtNuevoModeloFreno.setForeground(new java.awt.Color(0, 0, 0));
        txtNuevoModeloFreno.setBorder(null);
        txtNuevoModeloFreno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoModeloFrenoActionPerformed(evt);
            }
        });
        PanelParaModificarMoto.add(txtNuevoModeloFreno, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 270, 190, 20));

        jSeparator25.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator25.setForeground(new java.awt.Color(0, 0, 0));
        PanelParaModificarMoto.add(jSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 290, 190, 20));

        NuevoMaterialChasis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "ACERO", "ALUMINIO", "FIBRA_CARBONO" }));
        PanelParaModificarMoto.add(NuevoMaterialChasis, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 500, 180, -1));

        NuevoTipoMotor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "DOS_TIEMPOS", "CUATRO_TIEMPOS" }));
        PanelParaModificarMoto.add(NuevoTipoMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 630, 180, -1));

        NuevoTipoChasis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "TUBULAR", "PERIMETRAL", "MULTITUBULAR" }));
        PanelParaModificarMoto.add(NuevoTipoChasis, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 530, 180, -1));

        NuevaCilindradaMotor.setForeground(new java.awt.Color(0, 0, 0));
        NuevaCilindradaMotor.setBorder(null);
        NuevaCilindradaMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaCilindradaMotorActionPerformed(evt);
            }
        });
        PanelParaModificarMoto.add(NuevaCilindradaMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 600, 180, 20));

        jSeparator26.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator26.setForeground(new java.awt.Color(0, 0, 0));
        PanelParaModificarMoto.add(jSeparator26, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 620, 180, 20));

        NuevaPotenciaMotor.setForeground(new java.awt.Color(0, 0, 0));
        NuevaPotenciaMotor.setBorder(null);
        NuevaPotenciaMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaPotenciaMotorActionPerformed(evt);
            }
        });
        PanelParaModificarMoto.add(NuevaPotenciaMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 660, 180, 20));

        jSeparator27.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator27.setForeground(new java.awt.Color(0, 0, 0));
        PanelParaModificarMoto.add(jSeparator27, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 680, 180, 20));

        NuevoMaterialFreno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "ACERO", "CARBONO", "CERAMICO", "COMPUESTO", "FUNDICION" }));
        PanelParaModificarMoto.add(NuevoMaterialFreno, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 240, 190, -1));

        NuevoTipoTransmision.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "MANUAL", "AUTOMATICA" }));
        PanelParaModificarMoto.add(NuevoTipoTransmision, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 460, 170, -1));

        NuevaCapacidadAsiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "MONOPLAZA", "BIPLAZA" }));
        PanelParaModificarMoto.add(NuevaCapacidadAsiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 350, 170, -1));

        NuevoMaterialAsiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "CUERO", "SINTETICO", "TELA" }));
        PanelParaModificarMoto.add(NuevoMaterialAsiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 380, 170, -1));

        BotonModificarMotoBase.setText("Modificar Moto");
        BotonModificarMotoBase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonModificarMotoBaseActionPerformed(evt);
            }
        });
        PanelParaModificarMoto.add(BotonModificarMotoBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(943, 550, 140, 30));

        BotonModificarMotoIndividual1.setText("Modificar Moto individual");
        BotonModificarMotoIndividual1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonModificarMotoIndividual1ActionPerformed(evt);
            }
        });
        PanelParaModificarMoto.add(BotonModificarMotoIndividual1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 303, -1, 30));

        NuevaVelocidadesTransmision.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "UNA", "DOS", "TRES", "CUATRO", "CINCO", "SEIS" }));
        PanelParaModificarMoto.add(NuevaVelocidadesTransmision, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 490, 170, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelParaModificarMoto.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 10, 620));

        ModificarInfoAdmin.addTab("tab12", PanelParaModificarMoto);

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
                && nuevaCedula.equals(empleadoSeleccionado.getIdentificacion())) {

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String tipoSeleccionado = (String) comboBoxTipoMoto3.getSelectedItem();
            String colorSeleccionado = (String) comboBoxColorMoto.getSelectedItem();
            String marcaSeleccionada = MarcaMotos.getText().trim();
            int cantidad = (int) SpinnerCantidadMotos.getValue();

            boolean exito = MotoController.getInstancia().agregarMotosDesdeUI(
                    tipoSeleccionado,
                    colorSeleccionado,
                    marcaSeleccionada,
                    cantidad,
                    MarcaMotos,
                    SpinnerCantidadMotos,
                    this
            );

            if (exito) {
                limpiarCamposMoto();
                cargarMotosEnTabla();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

        }    }//GEN-LAST:event_jButton1ActionPerformed

    private void MarcaMotosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MarcaMotosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MarcaMotosActionPerformed

    private void PartesMotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PartesMotoActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_PartesMotoActionPerformed

    private void MotoEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MotoEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MotoEliminarActionPerformed

    private void PartesMoto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PartesMoto2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PartesMoto2ActionPerformed

    private void ModificarMotoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarMotoItemActionPerformed
        JTable tabla = (JTable) jPopupMenu5.getInvoker();
        if (tabla != TablaModificarMotos) {
            return;
        }

        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una moto primero.");
            return;
        }

        int idMoto = (int) tabla.getValueAt(filaSeleccionada, 0);
        Moto moto = motoController.buscarPorId(idMoto);

        if (moto == null) {
            JOptionPane.showMessageDialog(this, "No se encontró información para esta moto.");
            return;
        }

        ModificarInfoAdmin.setSelectedIndex(10);

        txtIdMotoModificar.setText(String.valueOf(moto.getIdMoto()));        // TODO add your handling code here:
    }//GEN-LAST:event_ModificarMotoItemActionPerformed

    private void txtNuevaMarcaMotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevaMarcaMotoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevaMarcaMotoActionPerformed

    private void txtNuevaMarcaLlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevaMarcaLlantaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevaMarcaLlantaActionPerformed

    private void txtNuevoModeloLlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoModeloLlantaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoModeloLlantaActionPerformed

    private void txtNuevoModeloMotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoModeloMotoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoModeloMotoActionPerformed

    private void NuevoCilindrajeMotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoCilindrajeMotoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevoCilindrajeMotoActionPerformed

    private void txtNuevaMarcaFrenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevaMarcaFrenoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevaMarcaFrenoActionPerformed

    private void txtNuevoModeloFrenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoModeloFrenoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoModeloFrenoActionPerformed

    private void NuevaCilindradaMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevaCilindradaMotorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevaCilindradaMotorActionPerformed

    private void NuevaPotenciaMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevaPotenciaMotorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevaPotenciaMotorActionPerformed

    private void BotonModificarMotoBaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonModificarMotoBaseActionPerformed
        try {
            boolean exito = motoController.modificarMotoBaseDesdeUI(
                    this,
                    comboBoxMotoBasePlaca,
                    txtNuevoModeloMoto,
                    TieneParrillaNueva,
                    TieneMaleteroNueva,
                    NuevoCilindrajeMoto,
                    // motor
                    NuevoTipoMotor,
                    NuevaPotenciaMotor,
                    NuevaCilindradaMotor,
                    // llanta
                    comboBoxTipoLlanta,
                    NuevaMedidaLlantaCombo,
                    txtNuevaMarcaLlanta,
                    NuevoMaterialLlantaCombo,
                    txtNuevoModeloLlanta,
                    // chasis
                    NuevoMaterialChasis,
                    NuevoTipoChasis,
                    // freno
                    comboBoxTipoFreno,
                    txtNuevaMarcaFreno,
                    NuevoMaterialFreno,
                    txtNuevoModeloFreno,
                    // asiento
                    NuevaCapacidadAsiento,
                    NuevoMaterialAsiento,
                    // transmisión
                    NuevoTipoTransmision,
                    NuevaVelocidadesTransmision
            );

            if (exito) {
                cargarMotosEnTablaModificar();
                cargarMotosEnTabla();
                limpiarCamposMotoBase();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }     // TODO add your handling code here:
    }//GEN-LAST:event_BotonModificarMotoBaseActionPerformed

    private void BotonModificarMotoIndividual1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonModificarMotoIndividual1ActionPerformed
        try {
            boolean exito = motoController.modificarMotoIndividualDesdeUI(
                    this,
                    txtIdMotoModificar,
                    txtNuevaMarcaMoto,
                    comboBoxNuevoColorMoto1
            );

            if (exito) {
                cargarMotosEnTablaModificar();
                cargarMotosEnTabla();
                limpiarCamposMotoIndividual();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonModificarMotoIndividual1ActionPerformed

    private void limpiarCamposMotoBase() {
        comboBoxMotoBasePlaca.setSelectedIndex(0);
        txtNuevoModeloMoto.setText("");
        TieneParrillaNueva.setSelectedIndex(0);
        TieneMaleteroNueva.setSelectedIndex(0);
        NuevoCilindrajeMoto.setText("");

        // Motor
        NuevoTipoMotor.setSelectedIndex(0);
        NuevaPotenciaMotor.setText("");
        NuevaCilindradaMotor.setText("");

        // Llanta
        comboBoxTipoLlanta.setSelectedIndex(0);
        NuevaMedidaLlantaCombo.setSelectedIndex(0);
        txtNuevaMarcaLlanta.setText("");
        NuevoMaterialLlantaCombo.setSelectedIndex(0);
        txtNuevoModeloLlanta.setText("");

        // Chasis
        NuevoMaterialChasis.setSelectedIndex(0);
        NuevoTipoChasis.setSelectedIndex(0);

        // Freno
        comboBoxTipoFreno.setSelectedIndex(0);
        txtNuevaMarcaFreno.setText("");
        NuevoMaterialFreno.setSelectedIndex(0);
        txtNuevoModeloFreno.setText("");

        // Asiento
        NuevaCapacidadAsiento.setSelectedIndex(0);
        NuevoMaterialAsiento.setSelectedIndex(0);

        // Transmisión
        NuevoTipoTransmision.setSelectedIndex(0);
        NuevaVelocidadesTransmision.setSelectedIndex(0);
    }

    private void limpiarCamposMotoIndividual() {
        txtIdMotoModificar.setText("");
        txtNuevaMarcaMoto.setText("");
        comboBoxNuevoColorMoto1.setSelectedIndex(0);
    }

    private void limpiarCamposMoto() {
        comboBoxTipoMoto3.setSelectedIndex(0);
        comboBoxColorMoto.setSelectedIndex(0);
        MarcaMotos.setText("");
        SpinnerCantidadMotos.setValue(1);
    }

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
    private javax.swing.JButton BotonModificarMotoBase;
    private javax.swing.JButton BotonModificarMotoIndividual1;
    private javax.swing.JMenuItem Despedir;
    private javax.swing.JPanel EliminarEmpleado;
    private javax.swing.JPanel EliminarMoto;
    private javax.swing.JTextField MarcaMotos;
    private javax.swing.JPanel ModificarEmpleado;
    private javax.swing.JTabbedPane ModificarInfoAdmin;
    private javax.swing.JPanel ModificarMoto;
    private javax.swing.JMenuItem ModificarMotoItem;
    private javax.swing.JMenuItem MotoEliminar;
    private javax.swing.JTextField Nombre2Field1;
    private javax.swing.JComboBox<String> NuevaCapacidadAsiento;
    private javax.swing.JTextField NuevaCilindradaMotor;
    private javax.swing.JComboBox<String> NuevaMedidaLlantaCombo;
    private javax.swing.JTextField NuevaPotenciaMotor;
    private javax.swing.JComboBox<String> NuevaVelocidadesTransmision;
    private javax.swing.JTextField NuevoCilindrajeMoto;
    private javax.swing.JComboBox<String> NuevoMaterialAsiento;
    private javax.swing.JComboBox<String> NuevoMaterialChasis;
    private javax.swing.JComboBox<String> NuevoMaterialFreno;
    private javax.swing.JComboBox<String> NuevoMaterialLlantaCombo;
    private javax.swing.JComboBox<String> NuevoTipoChasis;
    private javax.swing.JComboBox<String> NuevoTipoMotor;
    private javax.swing.JComboBox<String> NuevoTipoTransmision;
    private javax.swing.JPanel PanelParaModificarMoto;
    private javax.swing.JMenuItem PartesMoto;
    private javax.swing.JMenuItem PartesMoto2;
    private javax.swing.JSpinner SpinnerCantidadMotos;
    private javax.swing.JTable TablaListarMotos;
    private javax.swing.JTable TablaModificarMotos;
    private javax.swing.JComboBox<String> TieneMaleteroNueva;
    private javax.swing.JComboBox<String> TieneParrillaNueva;
    private javax.swing.JPanel VisualizarEmpleado;
    private javax.swing.JPanel VisualizarMoto1;
    private javax.swing.JPanel VisualizarMoto2;
    private javax.swing.JPanel añadirEmpleado;
    private javax.swing.JButton btnAñadirEmpleado;
    private javax.swing.JButton btnModificarEmpleado;
    private javax.swing.JTextField cedula;
    private javax.swing.JCheckBox checkBoxTerminosYConcidiones;
    private javax.swing.JComboBox<String> comboBoxColorMoto;
    private javax.swing.JComboBox<String> comboBoxMotoBasePlaca;
    private javax.swing.JComboBox<String> comboBoxNuevoColorMoto1;
    private javax.swing.JComboBox<String> comboBoxTipoFreno;
    private javax.swing.JComboBox<String> comboBoxTipoLlanta;
    private javax.swing.JComboBox<String> comboBoxTipoMoto3;
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
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JPopupMenu jPopupMenu4;
    private javax.swing.JPopupMenu jPopupMenu5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
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
    private javax.swing.JLabel txtIdMotoModificar;
    private javax.swing.JTextField txtNuevaCedula;
    private javax.swing.JTextField txtNuevaEdad;
    private javax.swing.JTextField txtNuevaMarcaFreno;
    private javax.swing.JTextField txtNuevaMarcaLlanta;
    private javax.swing.JTextField txtNuevaMarcaMoto;
    private javax.swing.JTextField txtNuevoCorreo;
    private javax.swing.JTextField txtNuevoModeloFreno;
    private javax.swing.JTextField txtNuevoModeloLlanta;
    private javax.swing.JTextField txtNuevoModeloMoto;
    private javax.swing.JTextField txtNuevoPrimerNombre;
    private javax.swing.JTextField txtNuevoSegundoApellido;
    private javax.swing.JTextField txtNuevoTelefono;
    private javax.swing.JTable visualizarEmpleados;
    // End of variables declaration//GEN-END:variables
}
