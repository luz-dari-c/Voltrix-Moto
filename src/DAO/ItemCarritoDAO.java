package DAO;

import Model.Entities.ItemCarrito;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemCarritoDAO {

    private static final String RUTA_JSON = "src/Resources/Data/items.json";
    private final Gson gson;
    private static ItemCarritoDAO instancia;

    
    public ItemCarritoDAO() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        crearDirectoriosSiNoExisten();

    }
    
    public static synchronized ItemCarritoDAO getInstancia() {
        if (instancia == null) {
            instancia = new ItemCarritoDAO();
        }
        return instancia;
    }


    public List<ItemCarrito> cargarTodos() {
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
            Type tipoListaItem = new TypeToken<ArrayList<ItemCarrito>>() {
            }.getType();
            List<ItemCarrito> items = gson.fromJson(reader, tipoListaItem);

            return items != null ? items : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private boolean guardarTodos(List<ItemCarrito> items) {
        try (Writer writer = new FileWriter(RUTA_JSON)) {
            gson.toJson(items, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
            return false;
        }
    }

    private void crearDirectoriosSiNoExisten() {
        File archivo = new File(RUTA_JSON);
        archivo.getParentFile().mkdirs();
    }

   public boolean guardarItem(ItemCarrito item) {
    List<ItemCarrito> items = cargarTodos();

    int nuevoId = generarNuevoId(items);
    item.setId(String.valueOf(nuevoId));

    BigDecimal subtotal = item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad()));
    item.setSubtotal(subtotal);

    items.add(item);
    guardarTodos(items);
    return true;
}


    public boolean eliminarItem(String id) {
        List<ItemCarrito> items = cargarTodos();
        boolean eliminado = items.removeIf(i -> i.getId().equals(id));

        if (eliminado) {
            guardarTodos(items);
        }

        return eliminado;
    }

    public boolean actualizarCantidad(String id, int nuevaCantidad) {
        List<ItemCarrito> items = cargarTodos();
        for (ItemCarrito i : items) {
            if (i.getId().equals(id)) {
                i.setCantidad(nuevaCantidad);
                guardarTodos(items);
                return true;
            }
        }
        return false;
    }

    public boolean limpiarCarrito() {
        try (Writer writer = new FileWriter(RUTA_JSON)) {
            gson.toJson(new ArrayList<>(), writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error al limpiar el carrito: " + e.getMessage());
            return false;
        }
    }

    public ItemCarrito buscarPorId(String id) {
        List<ItemCarrito> items = cargarTodos();
        for (ItemCarrito item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public BigDecimal calcularTotal() {
        List<ItemCarrito> items = cargarTodos();
        BigDecimal total = BigDecimal.ZERO;

        for (ItemCarrito item : items) {
            if (item.getSubtotal() != null) {
                total = total.add(item.getSubtotal());
            } else if (item.getPrecioUnitario() != null) {
                BigDecimal subtotal = item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad()));
                total = total.add(subtotal);
            }
        }

        return total;
    }

    public int generarNuevoId(List<ItemCarrito> items) {
        if (items.isEmpty()) {
            return 1;
        }

        int maxId = 0;
        for (ItemCarrito item : items) {
            try {
                int idNum = Integer.parseInt(item.getId());
                if (idNum > maxId) {
                    maxId = idNum;
                }
            } catch (NumberFormatException e) {
            }
        }

        return maxId + 1;
    }

}
