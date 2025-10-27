package DAO;

import Model.Entities.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final String RUTA_RECURSO_CLASSPATH =  "src/Resources/Data/usuarios.json";
    private static final String DIR_PERSISTENCIA = "Resources" + File.separator + "data";
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
        File archivoPersistencia = new File(RUTA_PERSISTENCIA);

        if (archivoPersistencia.exists() && archivoPersistencia.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivoPersistencia))) {
                usuariosCargados = leerJSON(reader);
                System.out.println("DAO DEBUG: Carga exitosa desde RUTA DE PERSISTENCIA. Usuarios: " + usuariosCargados.size());
                if (!usuariosCargados.isEmpty()) {
                     return usuariosCargados;
                }
            } catch (IOException | JsonSyntaxException e) {
                System.err.println("DAO ERROR: Fallo al leer desde persistencia. " + e.getMessage());
            }
        }

        try (InputStream is = getClass().getResourceAsStream(RUTA_RECURSO_CLASSPATH)) {
            if (is != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    usuariosCargados = leerJSON(reader);
                }
                System.out.println("DAO DEBUG: Carga exitosa desde CLASSPATH. Usuarios: " + usuariosCargados.size());
            }
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("DAO ERROR: Error al leer el archivo JSON desde Classpath: " + e.getMessage());
        }
        
        if (usuariosCargados.isEmpty()) {
            usuariosCargados = new ArrayList<>();
        }

        new File(DIR_PERSISTENCIA).mkdirs(); 
        System.out.println("DAO DEBUG: Carga finalizada. Total: " + usuariosCargados.size());
        return usuariosCargados;
    }
    
    private List<Usuario> leerJSON(BufferedReader reader) throws JsonSyntaxException {
        Type listType = new TypeToken<List<Usuario>>() {}.getType();
        List<Usuario> lista = gson.fromJson(reader, listType);
        return lista != null ? lista : new ArrayList<>();
    }

    private synchronized void guardarUsuarios(List<Usuario> lista) {
        File archivo = new File(RUTA_PERSISTENCIA);
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(lista, writer);
            System.out.println("DAO DEBUG: Usuarios guardados en JSON. Ruta: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("DAO ERROR: Error al escribir el archivo JSON: " + e.getMessage());
        }
    }
    
    public synchronized boolean registrarUsuario(Usuario usuario) {
        if (existeEmail(usuario.getEmail()) || existeCedula(usuario.getCedula())) {
            return false;
        }
        usuarios.add(usuario);
        guardarUsuarios(this.usuarios);
        return true;
    }

    public Usuario login(String email, String password) {
        // email y password ya llegan trimmados y en minúsculas (email) desde el Controller
        String emailLimpio = email; 
        String passwordLimpia = password;

        for (Usuario usuario : usuarios) {
            
            // 🔴 INICIO DEBUG EXTENSO
            String dbEmail = (usuario.getEmail() != null) ? usuario.getEmail().trim().toLowerCase() : "NULL";
            String dbPassword = (usuario.getPassword() != null) ? usuario.getPassword().trim() : "NULL";
            
            boolean emailMatch = dbEmail.equals(emailLimpio);
            boolean passwordMatch = dbPassword.equals(passwordLimpia);
            
            System.out.println("----------------------------------------------------------------------");
            System.out.println("DAO DEBUG LOGIN: Comparando usuario: " + usuario.getPrimerNombre());
            System.out.println("   EMAIL (DB):  '" + dbEmail + "' | Longitud: " + dbEmail.length());
            System.out.println("   EMAIL (USER):'" + emailLimpio + "' | Longitud: " + emailLimpio.length());
            System.out.println("   EMAIL MATCH: " + emailMatch);

            if (emailMatch) {
                System.out.println("   PASSWORD (DB):  '" + dbPassword + "' | Longitud: " + dbPassword.length());
                System.out.println("   PASSWORD (USER):'" + passwordLimpia + "' | Longitud: " + passwordLimpia.length());
                System.out.println("   PASSWORD MATCH: " + passwordMatch);
                if (passwordMatch) {
                    System.out.println("DAO DEBUG LOGIN: ¡COINCIDENCIA ENCONTRADA!");
                    return usuario; 
                }
            } else {
                 System.out.println("   PASSWORD: No se compara por fallo en EMAIL.");
            }
            System.out.println("----------------------------------------------------------------------");
            // 🔴 FIN DEBUG EXTENSO
        }
        return null;
    }
    
    // Resto de métodos (existeEmail, existeCedula, etc.) se mantienen igual.
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
    
    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }
}