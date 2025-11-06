package DAO;

import Model.Entities.Carrito;
import Utilidades.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class CarritoDAO {

    private static final String RUTA_JSON = "src/Resources/Data/carritos.json";
    private final Gson gson;
    private static CarritoDAO instancia;

    private CarritoDAO() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        crearDirectoriosSiNoExisten();
    }

    public static synchronized CarritoDAO getInstancia() {
        if (instancia == null) {
            instancia = new CarritoDAO();
        }
        return instancia;
    }

    private void crearDirectoriosSiNoExisten() {
        File archivo = new File(RUTA_JSON);
        archivo.getParentFile().mkdirs();
    }

    public List<Carrito> cargarTodos() {
        File archivo = new File(RUTA_JSON);
        archivo.getParentFile().mkdirs();

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                guardarTodos(new ArrayList<>());
                return new ArrayList<>();
            } catch (IOException e) {
                System.err.println("Error al crear archivo JSON: " + e.getMessage());
                return new ArrayList<>();
            }
        }

        if (archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(RUTA_JSON)) {
            Type tipoLista = new TypeToken<ArrayList<Carrito>>() {}.getType();
            List<Carrito> carritos = gson.fromJson(reader, tipoLista);
            return carritos != null ? carritos : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private boolean guardarTodos(List<Carrito> carritos) {
        try (Writer writer = new FileWriter(RUTA_JSON)) {
            gson.toJson(carritos, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
            return false;
        }
    }

    public boolean guardarCarrito(Carrito carrito) {
        List<Carrito> carritos = cargarTodos();
        int nuevoId = generarNuevoId(carritos);
        carrito.setId(String.valueOf(nuevoId));
        carritos.add(carrito);
        return guardarTodos(carritos);
    }

    public Carrito buscarPorId(String id) {
        List<Carrito> carritos = cargarTodos();
        for (Carrito c : carritos) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public boolean eliminarCarrito(String id) {
        List<Carrito> carritos = cargarTodos();
        boolean eliminado = carritos.removeIf(c -> c.getId().equals(id));
        if (eliminado) {
            guardarTodos(carritos);
        }
        return eliminado;
    }

    public boolean actualizarCarrito(Carrito carritoActualizado) {
        List<Carrito> carritos = cargarTodos();
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getId().equals(carritoActualizado.getId())) {
                carritos.set(i, carritoActualizado);
                return guardarTodos(carritos);
            }
        }
        return false;
    }

    public int generarNuevoId(List<Carrito> carritos) {
        if (carritos.isEmpty()) {
            return 1;
        }
        int maxId = 0;
        for (Carrito c : carritos) {
            try {
                int idNum = Integer.parseInt(c.getId());
                if (idNum > maxId) {
                    maxId = idNum;
                }
            } catch (NumberFormatException e) {
            }
        }
        return maxId + 1;
    }
   

}
