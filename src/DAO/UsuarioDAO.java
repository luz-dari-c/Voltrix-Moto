package DAO;

import Model.Entities.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final String ARCHIVO_JSON = "usuarios.json";
    private Gson gson;
    private List<Usuario> usuarios;

    public UsuarioDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.usuarios = cargarUsuarios();
    }

    private List<Usuario> cargarUsuarios() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_JSON))) {
            Type listType = new TypeToken<List<Usuario>>(){}.getType();
            List<Usuario> usuariosCargados = gson.fromJson(reader, listType);
            return usuariosCargados != null ? usuariosCargados : new ArrayList<>();
        } catch (IOException e) {
            // Si el archivo no existe, retornar lista vacía
            return new ArrayList<>();
        }
    }

    private void guardarUsuarios() {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registrarUsuario(Usuario usuario) {
        // Verificar si el email ya existe
        if (existeEmail(usuario.getEmail())) {
            return false;
        }
        
        // Verificar si la cédula ya existe
        if (existeCedula(usuario.getCedula())) {
            return false;
        }
        
        usuarios.add(usuario);
        guardarUsuarios();
        return true;
    }

    public Usuario login(String email, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean existeEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeCedula(String cedula) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                return true;
            }
        }
        return false;
    }

    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }

    public void actualizarUsuario(Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equals(usuarioActualizado.getEmail())) {
                usuarios.set(i, usuarioActualizado);
                guardarUsuarios();
                break;
            }
        }
    }
}