package ViewComponents;

import ViewResourses.Button;
import ViewResourses.MyPasswordField;
import ViewResourses.MyTextField;
import DAO.UsuarioDAO;
import Model.Entities.Usuario;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;

public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

    private UsuarioDAO usuarioDAO;
    
    // Campos para registro
    private MyTextField txtPrimerNombre;
    private MyTextField txtSegundoNombre;
    private MyTextField txtPrimerApellido;
    private MyTextField txtSegundoApellido;
    private MyTextField txtCedula;
    private MyTextField txtTelefono;
    private MyTextField txtEmailRegister;
    private MyPasswordField txtPassRegister;
    private MyTextField txtDireccion;
    
    // Campos para login
    private MyTextField txtEmailLogin;
    private MyPasswordField txtPassLogin;

    public PanelLoginAndRegister() {
        initComponents();
        usuarioDAO = new UsuarioDAO();
        initRegister();
        initLogin();
        login.setVisible(false);
        register.setVisible(true);
    }

    private void initRegister() {
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]20[]5[]5[]5[]5[]5[]5[]5[]5[]10[]25[]push"));
        
        JLabel label = new JLabel("Create Account");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(7, 164, 121));
        register.add(label);
        
        // Primera fila de nombres - Mismo tamaño para todos (70%)
        txtPrimerNombre = new MyTextField();
        txtPrimerNombre.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/user.png")));
        txtPrimerNombre.setHint("First Name*");
        register.add(txtPrimerNombre, "w 70%");
        
        txtSegundoNombre = new MyTextField();
        txtSegundoNombre.setHint("Second Name");
        register.add(txtSegundoNombre, "w 70%");
        
        // Segunda fila de apellidos - Mismo tamaño para todos (70%)
        txtPrimerApellido = new MyTextField();
        txtPrimerApellido.setHint("First Last Name*");
        register.add(txtPrimerApellido, "w 70%");
        
        txtSegundoApellido = new MyTextField();
        txtSegundoApellido.setHint("Second Last Name");
        register.add(txtSegundoApellido, "w 70%");
        
        // Campos obligatorios - Mismo tamaño para todos (70%)
        txtCedula = new MyTextField();
        txtCedula.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/id.png")));
        txtCedula.setHint("ID Number*");
        register.add(txtCedula, "w 70%");
        
        txtTelefono = new MyTextField();
        txtTelefono.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/phone.png")));
        txtTelefono.setHint("Phone*");
        register.add(txtTelefono, "w 70%");
        
        txtEmailRegister = new MyTextField();
        txtEmailRegister.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/mail.png")));
        txtEmailRegister.setHint("Email*");
        register.add(txtEmailRegister, "w 70%");
        
        txtPassRegister = new MyPasswordField();
        txtPassRegister.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/pass.png")));
        txtPassRegister.setHint("Password*");
        register.add(txtPassRegister, "w 70%");
        
        // Campo opcional - Mismo tamaño (70%)
        txtDireccion = new MyTextField();
        txtDireccion.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/location.png")));
        txtDireccion.setHint("Address (Optional)");
        register.add(txtDireccion, "w 70%");
        
        // Botón SIGN UP con más espacio arriba (añadido 10px de separación)
        Button cmdRegister = new Button();
        cmdRegister.setBackground(new Color(7, 164, 121));
        cmdRegister.setForeground(new Color(250, 250, 250));
        cmdRegister.setText("SIGN UP");
        cmdRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                registrarUsuario();
            }
        });
        register.add(cmdRegister, "w 40%, h 40");
    }

    private void initLogin() {
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        
        JLabel label = new JLabel("Sign In");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(7, 164, 121));
        login.add(label);
        
        // Campos login - Mismo tamaño (70%)
        txtEmailLogin = new MyTextField();
        txtEmailLogin.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/mail.png")));
        txtEmailLogin.setHint("Email");
        login.add(txtEmailLogin, "w 70%");
        
        txtPassLogin = new MyPasswordField();
        txtPassLogin.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/pass.png")));
        txtPassLogin.setHint("Password");
        login.add(txtPassLogin, "w 70%");
        
        JButton cmdForget = new JButton("Forgot your password?");
        cmdForget.setForeground(new Color(100, 100, 100));
        cmdForget.setFont(new Font("sansserif", 1, 12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdForget);
        
        Button cmdLogin = new Button();
        cmdLogin.setBackground(new Color(7, 164, 121));
        cmdLogin.setForeground(new Color(250, 250, 250));
        cmdLogin.setText("SIGN IN");
        cmdLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                iniciarSesion();
            }
        });
        login.add(cmdLogin, "w 40%, h 40");
    }

    private void registrarUsuario() {
        // Obtener valores de los campos
        String primerNombre = txtPrimerNombre.getText().trim();
        String segundoNombre = txtSegundoNombre.getText().trim();
        String primerApellido = txtPrimerApellido.getText().trim();
        String segundoApellido = txtSegundoApellido.getText().trim();
        String cedula = txtCedula.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmailRegister.getText().trim();
        String password = new String(txtPassRegister.getPassword()).trim();
        String direccion = txtDireccion.getText().trim();
        
        // Validaciones
        if (primerNombre.isEmpty() || primerApellido.isEmpty() || cedula.isEmpty() || 
            telefono.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields (*)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID number must contain only numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!telefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Phone number must contain only numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario(
            primerNombre, 
            segundoNombre.isEmpty() ? null : segundoNombre,
            primerApellido,
            segundoApellido.isEmpty() ? null : segundoApellido,
            cedula,
            telefono,
            email,
            password,
            direccion.isEmpty() ? null : direccion
        );
        
        // Registrar usuario
        if (usuarioDAO.registrarUsuario(nuevoUsuario)) {
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposRegistro();
        } else {
            if (usuarioDAO.existeEmail(email)) {
                JOptionPane.showMessageDialog(this, "Email already exists", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "ID number already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void iniciarSesion() {
        String email = txtEmailLogin.getText().trim();
        String password = new String(txtPassLogin.getPassword()).trim();
        
        // Validaciones básicas
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Intentar login
        Usuario usuario = usuarioDAO.login(email, password);
        
        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Welcome " + usuario.getNombreCompleto() + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposLogin();
            
            // Aquí puedes abrir la ventana principal de la aplicación
            // MainApp mainApp = new MainApp(usuario);
            // mainApp.setVisible(true);
            // Window window = SwingUtilities.getWindowAncestor(this);
            // window.dispose();
            
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCamposRegistro() {
        txtPrimerNombre.setText("");
        txtSegundoNombre.setText("");
        txtPrimerApellido.setText("");
        txtSegundoApellido.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
        txtEmailRegister.setText("");
        txtPassRegister.setText("");
        txtDireccion.setText("");
    }

    private void limpiarCamposLogin() {
        txtEmailLogin.setText("");
        txtPassLogin.setText("");
    }

    public void showRegister(boolean show) {
        if (show) {
            register.setVisible(true);
            login.setVisible(false);
            limpiarCamposRegistro();
        } else {
            register.setVisible(false);
            login.setVisible(true);
            limpiarCamposLogin();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}
