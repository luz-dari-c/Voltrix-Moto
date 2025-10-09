package DAO;

import Model.Entities.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final String ARCHIVO_JSON = "src/Resources/Data/usuarios.json";
    private Gson gson;
    private List<Usuario> usuarios;

    // --- Patrón Singleton ---
    private static UsuarioDAO instancia;

    private UsuarioDAO() {
        System.out.println("=== INICIALIZANDO UsuarioDAO ===");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.usuarios = cargarUsuarios();
        
        // DEBUG: Mostrar información del archivo
        File archivo = new File(ARCHIVO_JSON);
        System.out.println("Ruta absoluta del archivo: " + archivo.getAbsolutePath());
        System.out.println("El archivo existe: " + archivo.exists());
        System.out.println("Tamaño del archivo: " + (archivo.exists() ? archivo.length() + " bytes" : "N/A"));
        System.out.println("Usuarios cargados en memoria: " + this.usuarios.size());
        System.out.println("=== FIN INICIALIZACIÓN UsuarioDAO ===\n");
    }

    public static synchronized UsuarioDAO getInstance() {
        if (instancia == null) {
            instancia = new UsuarioDAO();
        }
        return instancia;
    }
    // -------------------------

    private List<Usuario> cargarUsuarios() {
        System.out.println("=== CARGANDO USUARIOS DESDE ARCHIVO ===");
        File archivo = new File(ARCHIVO_JSON);
        
        // DEBUG: Información antes de crear directorios
        System.out.println("Ruta del archivo: " + archivo.getAbsolutePath());
        System.out.println("Directorio padre: " + archivo.getParentFile().getAbsolutePath());
        System.out.println("Directorio padre existe: " + archivo.getParentFile().exists());
        
        // Crear carpeta "data" si no existe
        boolean directoriosCreados = archivo.getParentFile().mkdirs();
        System.out.println("Directorios creados: " + directoriosCreados);
        
        if (!archivo.exists()) {
            System.out.println("El archivo NO existe. Creando archivo vacío...");
            guardarUsuarios(new ArrayList<>());
            System.out.println("Archivo vacío creado. Existe ahora: " + archivo.exists());
            return new ArrayList<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            System.out.println("Leyendo archivo JSON...");
            Type listType = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> usuariosCargados = gson.fromJson(reader, listType);
            
            System.out.println("Usuarios cargados del JSON: " + 
                (usuariosCargados != null ? usuariosCargados.size() : "null"));
            
            if (usuariosCargados != null) {
                for (Usuario u : usuariosCargados) {
                    System.out.println(" - " + u.getEmail() + " (" + u.getCedula() + ")");
                }
            }
            
            List<Usuario> resultado = usuariosCargados != null ? new ArrayList<>(usuariosCargados) : new ArrayList<>();
            System.out.println("Total usuarios en lista final: " + resultado.size());
            System.out.println("=== FIN CARGA USUARIOS ===\n");
            return resultado;
            
        } catch (IOException e) {
            System.err.println("ERROR CRÍTICO al cargar usuarios: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private synchronized void guardarUsuarios() {
        System.out.println("=== GUARDANDO USUARIOS (lista interna) ===");
        guardarUsuarios(this.usuarios);
    }

    private synchronized void guardarUsuarios(List<Usuario> lista) {
        System.out.println("=== EJECUTANDO guardarUsuarios() ===");
        File archivo = new File(ARCHIVO_JSON);
        System.out.println("Guardando en: " + archivo.getAbsolutePath());
        System.out.println("Número de usuarios a guardar: " + lista.size());
        
        for (Usuario u : lista) {
            System.out.println(" - " + u.getEmail() + " (" + u.getCedula() + ")");
        }
        
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(lista, writer);
            System.out.println("✅ Guardado exitoso. Archivo escrito.");
            System.out.println("Tamaño del archivo después de guardar: " + archivo.length() + " bytes");
        } catch (IOException e) {
            System.err.println("❌ ERROR al guardar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("=== FIN GUARDADO ===\n");
    }

    public synchronized boolean registrarUsuario(Usuario usuario) {
        System.out.println("=== REGISTRANDO NUEVO USUARIO ===");
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Cédula: " + usuario.getCedula());
        
        if (existeEmail(usuario.getEmail())) {
            System.out.println("❌ FALLO: Email ya existe");
            return false;
        }
        
        if (existeCedula(usuario.getCedula())) {
            System.out.println("❌ FALLO: Cédula ya existe");
            return false;
        }

        usuarios.add(usuario);
        System.out.println("Usuario agregado a lista en memoria. Total: " + usuarios.size());
        
        guardarUsuarios();
        System.out.println("✅ Registro exitoso");
        return true;
    }

    public Usuario login(String email, String password) {
        System.out.println("=== INTENTO DE LOGIN ===");
        System.out.println("Email: " + email);
        System.out.println("Usuarios en memoria: " + usuarios.size());
        
        Usuario resultado = usuarios.stream()
                .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email)
                        && usuario.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        
        System.out.println("Login " + (resultado != null ? "EXITOSO" : "FALLIDO"));
        return resultado;
    }

    public boolean existeEmail(String email) {
        boolean existe = usuarios.stream()
                .anyMatch(usuario -> usuario.getEmail().equalsIgnoreCase(email));
        System.out.println("Verificando email '" + email + "': " + (existe ? "EXISTE" : "NO EXISTE"));
        return existe;
    }

    public boolean existeCedula(String cedula) {
        boolean existe = usuarios.stream()
                .anyMatch(usuario -> usuario.getCedula().equals(cedula));
        System.out.println("Verificando cédula '" + cedula + "': " + (existe ? "EXISTE" : "NO EXISTE"));
        return existe;
    }

    public List<Usuario> obtenerTodos() {
        System.out.println("Obteniendo todos los usuarios: " + usuarios.size());
        return new ArrayList<>(usuarios);
    }

    public synchronized boolean actualizarUsuario(Usuario usuarioActualizado) {
        System.out.println("=== ACTUALIZANDO USUARIO ===");
        System.out.println("Cédula a actualizar: " + usuarioActualizado.getCedula());
        
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCedula().equals(usuarioActualizado.getCedula())) {
                usuarios.set(i, usuarioActualizado);
                guardarUsuarios();
                System.out.println("✅ Actualización exitosa");
                return true;
            }
        }
        System.out.println("❌ Usuario no encontrado para actualizar");
        return false;
    }
}