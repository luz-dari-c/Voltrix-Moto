package DAO;

import Model.Entities.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final String RUTA_PERSISTENCIA = "src/Resources/Data/usuarios.json";
    private Gson gson;
    private List<Usuario> usuarios;
    private static UsuarioDAO instancia;

    private UsuarioDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.usuarios = cargarUsuarios();
    }

    public static synchronized UsuarioDAO getInstance() {
        if (instancia == null) {
            instancia = new UsuarioDAO();
        }
        return instancia;
    }

    private List<Usuario> cargarUsuarios() {
        List<Usuario> usuariosCargados = new ArrayList<>();
        File archivo = new File(RUTA_PERSISTENCIA);

        if (archivo.exists() && archivo.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                Type listType = new TypeToken<List<Usuario>>() {}.getType();
                usuariosCargados = gson.fromJson(reader, listType);
                if (usuariosCargados == null) {
                    usuariosCargados = new ArrayList<>();
                }
            } catch (IOException | JsonSyntaxException e) {
                System.err.println("Error al cargar usuarios: " + e.getMessage());
                usuariosCargados = new ArrayList<>();
            }
        }
        return usuariosCargados;
    }

    private synchronized void guardarUsuarios() {
        File archivo = new File(RUTA_PERSISTENCIA);
        // Crear directorios si no existen
        archivo.getParentFile().mkdirs();
        
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    public synchronized boolean registrarUsuario(Usuario usuario) {
        if (existeEmail(usuario.getEmail()) || existeCedula(usuario.getCedula())) {
            return false;
        }
        usuarios.add(usuario);
        guardarUsuarios();
        return true;
    }

    public Usuario login(String email, String password) {
        String emailLimpio = email.trim().toLowerCase();
        String passwordLimpia = password.trim();

        for (Usuario usuario : usuarios) {
            String dbEmail = usuario.getEmail() != null ? usuario.getEmail().trim().toLowerCase() : "";
            String dbPassword = usuario.getPassword() != null ? usuario.getPassword().trim() : "";
            
            if (dbEmail.equals(emailLimpio) && dbPassword.equals(passwordLimpia)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean existeEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return usuarios.stream()
                .anyMatch(usuario -> email.trim().equalsIgnoreCase(usuario.getEmail()));
    }

    public boolean existeCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) return false;
        return usuarios.stream()
                .anyMatch(usuario -> cedula.trim().equals(usuario.getCedula()));
    }

    public Usuario buscarPorCorreo(String correo) {
        String correoLimpio = correo.trim().toLowerCase();
        return usuarios.stream()
                .filter(usuario -> usuario.getEmail().trim().equalsIgnoreCase(correoLimpio))
                .findFirst()
                .orElse(null);
    }

    public synchronized boolean actualizarContrasena(String email, String nuevaContrasena) {
        Usuario usuario = buscarPorCorreo(email);
        if (usuario != null) {
            usuario.setPassword(nuevaContrasena);
            guardarUsuarios();
            return true;
        }
        return false;
    }

    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }
}