package Controller;

import Model.Entities.Usuario;
import DAO.UsuarioDAO;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = UsuarioDAO.getInstance();
    }

    
    public String registrarUsuario(Usuario usuario) {
        String validacion = validarCamposRegistro(usuario);
        
        if (!validacion.equals("OK")) {
            return validacion;
        }

        boolean resultado = usuarioDAO.registrarUsuario(usuario);
        return resultado ? "OK" : "Error: Correo o Cédula ya existente.";
    }
    
    private String validarCamposRegistro(Usuario usuario) {
        if (usuario == null) return "Error interno: Objeto de usuario nulo.";

        if (usuario.getPrimerNombre() == null || usuario.getPrimerNombre().trim().isEmpty()) 
            return "Error: El campo Primer Nombre está vacío.";
        if (usuario.getPrimerApellido() == null || usuario.getPrimerApellido().trim().isEmpty()) 
            return "Error: El campo Primer Apellido está vacío.";
        if (usuario.getCedula() == null || usuario.getCedula().trim().isEmpty()) 
            return "Error: El campo Cédula está vacío.";
        if (!validarCedula(usuario.getCedula())) 
            return "Error: La Cédula debe contener solo números y tener un mínimo de 5 dígitos.";
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) 
            return "Error: El campo Email está vacío.";
        if (!validarEmail(usuario.getEmail())) 
            return "Error: El formato del Email es inválido (ej: usuario@dominio.com).";
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) 
            return "Error: El campo Contraseña está vacío.";
        if (!validarPassword(usuario.getPassword())) 
            return "Error: La Contraseña debe tener un mínimo de 6 caracteres.";
        
        if (usuarioDAO.existeEmail(usuario.getEmail()) || usuarioDAO.existeCedula(usuario.getCedula())) {
            return "Error: Correo o Cédula ya existente."; 
        }
        
        return "OK"; 
    }
    
    
    public String login(String email, String password) {
        System.out.println("CONTROLADOR DEBUG: Iniciando login con credenciales limpias.");
        
        if (email == null || email.isEmpty()) {
            return "Error: El campo Email está vacío.";
        }
        if (password == null || password.isEmpty()) {
            return "Error: El campo Contraseña está vacío.";
        }
        
        String emailLimpio = email.toLowerCase(); 
        String passwordLimpia = password;

        Usuario resultado = usuarioDAO.login(emailLimpio, passwordLimpia);
        
        System.out.println("CONTROLADOR DEBUG: Resultado del login: " + (resultado != null ? "ÉXITO" : "FALLO"));

        if (resultado != null) {
            return "OK";
        } else {
            return "Error: Credenciales incorrectas (Email o Contraseña no coinciden).";
        }
    }
    
    
    public Usuario getUsuarioLogeado(String email, String password) {
        String emailLimpio = email.trim().toLowerCase();
        String passwordLimpia = password.trim();
        return usuarioDAO.login(emailLimpio, passwordLimpia);
    }

    
    private boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        Matcher matcher = pattern.matcher(email.trim());
        return matcher.matches();
    }
    private boolean validarCedula(String cedula) {
        return cedula.trim().matches("\\d+") && cedula.trim().length() >= 5;
    }
    private boolean validarPassword(String password) {
        return password.trim().length() >= 6;
    }
}