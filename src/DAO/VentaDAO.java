package DAO;

import Model.Entities.Venta;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class VentaDAO {

    private static final String RUTA_JSON = "src/Resources/Data/ventas.json";
    private static VentaDAO instancia;
    private final Gson gson;

    private VentaDAO() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        crearDirectoriosSiNoExisten();
    }

    public static synchronized VentaDAO getInstancia() {
        if (instancia == null) {
            instancia = new VentaDAO();
        }
        return instancia;
    }

    private void crearDirectoriosSiNoExisten() {
        File archivo = new File(RUTA_JSON);
        archivo.getParentFile().mkdirs();
    }

    public List<Venta> cargarTodas() {
        File archivo = new File(RUTA_JSON);
        archivo.getParentFile().mkdirs();

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                guardarTodas(new ArrayList<>());
                return new ArrayList<>();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de ventas: " + e.getMessage());
                return new ArrayList<>();
            }
        }

        if (archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(RUTA_JSON)) {
            Type tipoLista = new TypeToken<ArrayList<Venta>>() {}.getType();
            List<Venta> ventas = gson.fromJson(reader, tipoLista);
            return ventas != null ? ventas : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo de ventas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private boolean guardarTodas(List<Venta> ventas) {
        try (Writer writer = new FileWriter(RUTA_JSON)) {
            gson.toJson(ventas, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar ventas: " + e.getMessage());
            return false;
        }
    }

    public boolean guardarVenta(Venta venta) {
        List<Venta> ventas = cargarTodas();
        int nuevoId = generarNuevoId(ventas);
        venta.setIdVenta(String.valueOf(nuevoId));
        ventas.add(venta);
        return guardarTodas(ventas);
    }

    public Venta buscarPorId(String idVenta) {
        List<Venta> ventas = cargarTodas();
        for (Venta v : ventas) {
            if (v.getIdVenta().equals(idVenta)) {
                return v;
            }
        }
        return null;
    }

    public boolean eliminarVenta(String idVenta) {
        List<Venta> ventas = cargarTodas();
        boolean eliminado = ventas.removeIf(v -> v.getIdVenta().equals(idVenta));
        if (eliminado) {
            guardarTodas(ventas);
        }
        return eliminado;
    }

    public boolean actualizarVenta(Venta ventaActualizada) {
        List<Venta> ventas = cargarTodas();
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getIdVenta().equals(ventaActualizada.getIdVenta())) {
                ventas.set(i, ventaActualizada);
                return guardarTodas(ventas);
            }
        }
        return false;
    }

    public int generarNuevoId(List<Venta> ventas) {
        if (ventas.isEmpty()) {
            return 1;
        }
        int maxId = 0;
        for (Venta v : ventas) {
            try {
                int idNum = Integer.parseInt(v.getIdVenta());
                if (idNum > maxId) {
                    maxId = idNum;
                }
            } catch (NumberFormatException e) {
            }
        }
        return maxId + 1;
    }
}
