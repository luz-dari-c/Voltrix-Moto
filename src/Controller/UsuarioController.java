package Controller;

import Model.Entities.Usuario;
import DAO.UsuarioDAO;
import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        // Usar Singleton en lugar de new
         System.out.println("🆕 Creando nuevo UsuarioController");
        this.usuarioDAO = UsuarioDAO.getInstance();
        System.out.println("UsuarioDAO obtenido (Singleton)");
    }

     public boolean registrarUsuario(Usuario usuario) {
        System.out.println("\n🎯 CONTROLADOR: Iniciando registro de usuario");
        System.out.println("Usuario recibido: " + (usuario != null ? usuario.getEmail() : "NULL"));
        
        if (usuario == null) {
            System.out.println("❌ Usuario es NULL");
            return false;
        }

        if (!usuario.validarCamposObligatorios()) {
            System.out.println("❌ Campos obligatorios no válidos");
            return false;
        }

        System.out.println("✅ Validaciones básicas pasadas");

        boolean resultado = usuarioDAO.registrarUsuario(usuario);
        System.out.println("Resultado del registro en DAO: " + resultado);
        return resultado;
    }

     public Usuario login(String email, String password) {
        System.out.println("\n🎯 CONTROLADOR: Iniciando login");
        System.out.println("Email: " + email);
        
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("❌ Credenciales vacías");
            return null;
        }
        
        Usuario resultado = usuarioDAO.login(email.trim(), password.trim());
        System.out.println("Resultado del login: " + (resultado != null ? "ÉXITO" : "FALLO"));
        return resultado;
    }

    public boolean existeEmail(String email) {
        return email != null && usuarioDAO.existeEmail(email.trim());
    }

    public boolean existeCedula(String cedula) {
        return cedula != null && usuarioDAO.existeCedula(cedula.trim());
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.obtenerTodos();
    }

    public boolean actualizarUsuario(Usuario usuario) {
        if (usuario == null) {
            return false;
        }
        return usuarioDAO.actualizarUsuario(usuario);
    }

    // --- Validaciones específicas del controlador ---
    private boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.length() >= 5;
    }

    private boolean validarCedula(String cedula) {
        return cedula != null && cedula.matches("\\d+") && cedula.length() >= 6;
    }

    private boolean validarTelefono(String telefono) {
        return telefono != null && telefono.matches("\\d+") && telefono.length() >= 7;
    }

    private boolean validarPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
