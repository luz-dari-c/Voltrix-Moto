package Controller;

import DAO.UsuarioDAO;
import Model.Entities.Usuario;
import Utilidades.Email;
import java.awt.Color;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import javax.swing.JTextField;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private String codigoRecuperacion;
    private String emailRecuperacion;

    // Patrones de validación
    private static final Pattern PATRON_SOLO_LETRAS = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    private static final Pattern PATRON_SOLO_NUMEROS = Pattern.compile("^[0-9]+$");
    private static final Pattern PATRON_EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PATRON_CONTRASENA = Pattern.compile("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{6,}$");

    public UsuarioController() {
        this.usuarioDAO = UsuarioDAO.getInstance();
    }

    public String registrarUsuario(Usuario usuario) {
        // Validar campos obligatorios
        List<String> errores = validarCamposRegistro(usuario);

        if (!errores.isEmpty()) {
            return construirMensajeError(errores);
        }

        // Validar email duplicado
        if (usuarioDAO.existeEmail(usuario.getEmail())) {
            return "El correo electrónico ya está registrado.";
        }

        // Validar cédula duplicada
        if (usuarioDAO.existeCedula(usuario.getCedula())) {
            return "La cédula ya está registrada.";
        }

        // Registrar usuario
        if (usuarioDAO.registrarUsuario(usuario)) {
            return "OK";
        } else {
            return "Error al registrar el usuario.";
        }
    }

    private List<String> validarCamposRegistro(Usuario usuario) {
        List<String> errores = new ArrayList<>();

        // Validar primer nombre
        if (usuario.getPrimerNombre() == null || usuario.getPrimerNombre().trim().isEmpty()) {
            errores.add("Primer nombre: Campo obligatorio");
        } else if (!PATRON_SOLO_LETRAS.matcher(usuario.getPrimerNombre().trim()).matches()) {
            errores.add("Primer nombre: Solo se permiten letras y espacios");
        } else if (usuario.getPrimerNombre().trim().length() < 2) {
            errores.add("Primer nombre: Debe tener al menos 2 caracteres");
        }

        // Validar segundo nombre (opcional)
        if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().trim().isEmpty()) {
            if (!PATRON_SOLO_LETRAS.matcher(usuario.getSegundoNombre().trim()).matches()) {
                errores.add("Segundo nombre: Solo se permiten letras y espacios");
            } else if (usuario.getSegundoNombre().trim().length() < 2) {
                errores.add("Segundo nombre: Debe tener al menos 2 caracteres");
            }
        }

        // Validar primer apellido
        if (usuario.getPrimerApellido() == null || usuario.getPrimerApellido().trim().isEmpty()) {
            errores.add("Primer apellido: Campo obligatorio");
        } else if (!PATRON_SOLO_LETRAS.matcher(usuario.getPrimerApellido().trim()).matches()) {
            errores.add("Primer apellido: Solo se permiten letras y espacios");
        } else if (usuario.getPrimerApellido().trim().length() < 2) {
            errores.add("Primer apellido: Debe tener al menos 2 caracteres");
        }

        // Validar segundo apellido
        if (usuario.getSegundoApellido() == null || usuario.getSegundoApellido().trim().isEmpty()) {
            errores.add("Segundo apellido: Campo obligatorio");
        } else if (!PATRON_SOLO_LETRAS.matcher(usuario.getSegundoApellido().trim()).matches()) {
            errores.add("Segundo apellido: Solo se permiten letras y espacios");
        } else if (usuario.getSegundoApellido().trim().length() < 2) {
            errores.add("Segundo apellido: Debe tener al menos 2 caracteres");
        }

        // Validar cédula
        if (usuario.getCedula() == null || usuario.getCedula().trim().isEmpty()) {
            errores.add("Cédula: Campo obligatorio");
        } else if (!PATRON_SOLO_NUMEROS.matcher(usuario.getCedula().trim()).matches()) {
            errores.add("Cédula: Solo se permiten números");
        } else if (usuario.getCedula().trim().length() < 6 || usuario.getCedula().trim().length() > 15) {
            errores.add("Cédula: Debe tener entre 6 y 15 dígitos");
        }

        // Validar email
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            errores.add("Correo electrónico: Campo obligatorio");
        } else if (!PATRON_EMAIL.matcher(usuario.getEmail().trim()).matches()) {
            errores.add("Correo electrónico: Formato de email inválido");
        }

        // Validar contraseña
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            errores.add("Contraseña: Campo obligatorio");
        } else if (!PATRON_CONTRASENA.matcher(usuario.getPassword().trim()).matches()) {
            errores.add("Contraseña: Debe tener al menos 6 caracteres y puede incluir letras, números y símbolos especiales");
        } else if (usuario.getPassword().trim().length() < 6) {
            errores.add("Contraseña: Debe tener al menos 6 caracteres");
        }

        return errores;
    }

    private String construirMensajeError(List<String> errores) {
        if (errores.size() == contarCamposObligatorios(errores)) {
            return "No se puede registrar. Todos los campos son obligatorios.";
        } else {
            StringBuilder mensaje = new StringBuilder("No se puede registrar. Errores encontrados:\n\n");
            for (String error : errores) {
                mensaje.append("• ").append(error).append("\n");
            }
            return mensaje.toString();
        }
    }

    private int contarCamposObligatorios(List<String> errores) {
        int count = 0;
        for (String error : errores) {
            if (error.contains("Campo obligatorio")) {
                count++;
            }
        }
        return count;
    }

    public String login(String email, String password) {
        // Validar campos vacíos
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return "Por favor, ingrese email y contraseña.";
        }

        // Validar formato de email
        if (!PATRON_EMAIL.matcher(email.trim()).matches()) {
            return "Formato de email inválido.";
        }

        // Validar longitud de contraseña
        if (password.trim().length() < 6) {
            return "La contraseña debe tener al menos 6 caracteres.";
        }

        Usuario usuario = usuarioDAO.login(email, password);
        if (usuario != null) {
            return "OK";
        } else {
            return "Credenciales incorrectas. Verifique su email y contraseña.";
        }
    }

    public Usuario getUsuarioLogeado(String email, String password) {
        return usuarioDAO.login(email, password);
    }

    // Métodos para validación en tiempo real (desde los eventos KeyPressed)
    public String validarCampoTexto(String texto, String nombreCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            return null; // Campo vacío, no mostrar error hasta que sea obligatorio
        }

        if (!PATRON_SOLO_LETRAS.matcher(texto.trim()).matches()) {
            return nombreCampo + ": Solo se permiten letras y espacios";
        }

        if (texto.trim().length() < 2) {
            return nombreCampo + ": Debe tener al menos 2 caracteres";
        }

        return null; // Sin errores
    }

    public String validarCampoNumerico(String texto, String nombreCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            return null; // Campo vacío, no mostrar error hasta que sea obligatorio
        }

        if (!PATRON_SOLO_NUMEROS.matcher(texto.trim()).matches()) {
            return nombreCampo + ": Solo se permiten números";
        }

        if (texto.trim().length() < 6 || texto.trim().length() > 15) {
            return nombreCampo + ": Debe tener entre 6 y 15 dígitos";
        }

        return null; // Sin errores
    }

    public String validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null; // Campo vacío, no mostrar error hasta que sea obligatorio
        }

        if (!PATRON_EMAIL.matcher(email.trim()).matches()) {
            return "Correo electrónico: Formato de email inválido";
        }

        return null; // Sin errores
    }

    public String validarContrasena(String contrasena) {
        if (contrasena == null || contrasena.trim().isEmpty()) {
            return null; // Campo vacío, no mostrar error hasta que sea obligatorio
        }

        if (contrasena.trim().length() < 6) {
            return "Contraseña: Debe tener al menos 6 caracteres";
        }

        if (!PATRON_CONTRASENA.matcher(contrasena.trim()).matches()) {
            return "Contraseña: Caracteres no permitidos detectados";
        }

        return null; // Sin errores
    }

    // Método para filtrar entrada en tiempo real (usar en KeyTyped event)
    public boolean permitirCaracterCampoTexto(char caracter) {
        return Character.isLetter(caracter) || Character.isWhitespace(caracter) || caracter == 'á' || caracter == 'é'
                || caracter == 'í' || caracter == 'ó' || caracter == 'ú' || caracter == 'Á' || caracter == 'É'
                || caracter == 'Í' || caracter == 'Ó' || caracter == 'Ú' || caracter == 'ñ' || caracter == 'Ñ';
    }

    public boolean permitirCaracterCampoNumerico(char caracter) {
        return Character.isDigit(caracter);
    }

    // Métodos para recuperación de contraseña
    public String enviarCodigoRecuperacion(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Por favor, ingrese su correo electrónico.";
        }

        // Validar formato de email
        if (!PATRON_EMAIL.matcher(email.trim()).matches()) {
            return "Formato de email inválido.";
        }

        // Verificar si el email existe
        if (!usuarioDAO.existeEmail(email)) {
            return "El correo ingresado no está registrado.";
        }

        try {
            // Generar código de 5 dígitos
            this.codigoRecuperacion = generarCodigo();
            this.emailRecuperacion = email.trim().toLowerCase();

            // Enviar código por email
            Email.enviarCodigo(email, codigoRecuperacion);
            return "OK";

        } catch (Exception e) {
            System.err.println("Error en envío de correo: " + e.getMessage());
            // Fallback: mostrar código en consola para desarrollo
            System.out.println("CÓDIGO DE VERIFICACIÓN para " + email + ": " + codigoRecuperacion);
            return "OK";
        }
    }

    public boolean verificarCodigo(String codigoIngresado) {
        if (codigoRecuperacion == null || emailRecuperacion == null) {
            return false;
        }
        return codigoRecuperacion.equals(codigoIngresado);
    }

    public String actualizarContrasena(String nuevaContrasena, String confirmacionContrasena) {
        if (nuevaContrasena == null || nuevaContrasena.trim().isEmpty()
                || confirmacionContrasena == null || confirmacionContrasena.trim().isEmpty()) {
            return "Ambos campos de contraseña son obligatorios.";
        }

        if (!nuevaContrasena.equals(confirmacionContrasena)) {
            return "Las contraseñas no coinciden.";
        }

        String errorContrasena = validarContrasena(nuevaContrasena);
        if (errorContrasena != null) {
            return errorContrasena;
        }

        if (usuarioDAO.actualizarContrasena(emailRecuperacion, nuevaContrasena)) {
            // Limpiar datos de recuperación
            codigoRecuperacion = null;
            emailRecuperacion = null;
            return "OK";
        } else {
            return "Error al actualizar la contraseña.";
        }
    }

    private String generarCodigo() {
        Random random = new Random();
        int codigo = 10000 + random.nextInt(90000); // Genera número entre 10000 y 99999
        return String.valueOf(codigo);
    }

    public void limpiarRecuperacion() {
        this.codigoRecuperacion = null;
        this.emailRecuperacion = null;
    }

    // En el UsuarioController, modifica los métodos de validación en tiempo real:
    public class ValidacionHelper {

        private static final Color COLOR_ERROR = new Color(220, 53, 69); // Rojo suave
        private static final Color COLOR_EXITO = new Color(40, 167, 69); // Verde suave

        public static void mostrarError(JTextField campo, String mensaje) {
            campo.setToolTipText(mensaje);
            campo.setForeground(COLOR_ERROR);
            // Cambiar el color del texto en lugar del borde
        }

        public static void mostrarExito(JTextField campo) {
            campo.setToolTipText(null);
            campo.setForeground(Color.BLACK);
        }

        public static void mostrarAdvertencia(JTextField campo, String mensaje) {
            campo.setToolTipText(mensaje);
            campo.setForeground(new Color(255, 193, 7)); // Amarillo/naranja
        }
    }

    // Agrega estos métodos al UsuarioController:
    public boolean validarEmailExistente(String email) {
        return usuarioDAO.existeEmail(email);
    }

    public String actualizarContrasena(String email, String nuevaContrasena, String confirmacionContrasena) {
        // Validaciones básicas
        if (nuevaContrasena == null || nuevaContrasena.trim().isEmpty()
                || confirmacionContrasena == null || confirmacionContrasena.trim().isEmpty()) {
            return "Ambos campos de contraseña son obligatorios.";
        }

        if (!nuevaContrasena.equals(confirmacionContrasena)) {
            return "Las contraseñas no coinciden.";
        }

        // Validar fortaleza de contraseña
        String errorContrasena = validarContrasena(nuevaContrasena);
        if (errorContrasena != null) {
            return errorContrasena;
        }

        // Actualizar en la base de datos
        if (usuarioDAO.actualizarContrasena(email, nuevaContrasena)) {
            return "OK";
        } else {
            return "Error al actualizar la contraseña. Contacte al administrador.";
        }
    }
}
