
package DAO;



import Model.Entities.Factura;
import Utilidades.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {
    private static final String RUTA_JSON = "src/Resources/Data/facturas.json";
    
    private final Gson gson;

    public FacturaDAO() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }
    public List<Factura> cargarTodas() {
        try (Reader reader = new FileReader(RUTA_JSON)) {
            Type tipoLista = new TypeToken<ArrayList<Factura>>(){}.getType();
            List<Factura> facturas = gson.fromJson(reader, tipoLista);
            return facturas != null ? facturas : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void guardarFactura(Factura factura) {
        List<Factura> facturas = cargarTodas();
        facturas.add(factura);
        try (FileWriter writer = new FileWriter(RUTA_JSON)) {
            gson.toJson(facturas, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
