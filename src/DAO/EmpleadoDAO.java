package DAO;

import Model.Entities.Empleado;
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
import javax.swing.JOptionPane;

public class EmpleadoDAO {

    private static final String RUTA_JSON = "src/Resources/Data/empleados.json";
    private final Gson gson;
        private static EmpleadoDAO instancia;


    public EmpleadoDAO() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        crearDirectoriosSiNoExisten();
    }
    

    public static synchronized EmpleadoDAO getInstance() {
        if (instancia == null) {
            instancia = new EmpleadoDAO();
        }
        return instancia;
    }

    public boolean registrarEmpleado(Empleado empleado) {
        if (empleado == null) {
            return false;
        }

        List<Empleado> empleadosLista = obtenerEmpleado();

        boolean existe = empleadosLista.stream()
                .anyMatch(e -> e.getIdentificacion().equals(empleado.getIdentificacion()));

        if (existe) {
            return false;
        }

        empleadosLista.add(empleado);
        guardarTodos(empleadosLista);

        
        return true;
    }

    public List<Empleado> obtenerEmpleado() {
        File archivo = new File(RUTA_JSON);

        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(archivo)) {
            Empleado[] empleados = gson.fromJson(reader, Empleado[].class);
            if (empleados != null) {
                return new ArrayList<>(List.of(empleados));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private void crearDirectoriosSiNoExisten() {
        File archivo = new File(RUTA_JSON);
        archivo.getParentFile().mkdirs();
    }

    public boolean eliminarEmpleado(String identificacion) {

        List<Empleado> listaEmpleados = obtenerEmpleado();

        boolean eliminado
                = listaEmpleados.removeIf(empleados -> empleados.getIdentificacion().equals(identificacion));

        if (eliminado) {
            try (Writer writer = new FileWriter(RUTA_JSON)) {
                gson.toJson(listaEmpleados, writer);
                return true;
            } catch (IOException e) {
                System.err.println("Error al guardar los cambios después de eliminar: " + e.getMessage());
                return false;
            }
        }

        return eliminado;

    }

    public boolean actualizarEmpleado(
        String identificacionOriginal,
        String nuevaIdentificacion,
        String nuevoPrimerNombre,
        String nuevoSegundoNombre,
        String nuevoPrimerApellido,
        String nuevoSegundoApellido,
        String nuevaEdad,
        String nuevoCorreo,
        String nuevoTelefono) {

    List<Empleado> empleados = obtenerEmpleado();
    boolean encontrado = false;

    for (Empleado empleado : empleados) {
        if (empleado.getIdentificacion().equals(identificacionOriginal)) {
            encontrado = true;

            if (nuevaIdentificacion != null && !nuevaIdentificacion.trim().isEmpty()) {
                empleado.setIdentificacion(nuevaIdentificacion.trim());
            }

            if (nuevoPrimerNombre != null && !nuevoPrimerNombre.trim().isEmpty()) {
                empleado.setPrimerNombre(nuevoPrimerNombre.trim());
            }

            if (nuevoSegundoNombre != null && !nuevoSegundoNombre.trim().isEmpty()) {
                empleado.setSegundoNombre(nuevoSegundoNombre.trim());
            }

            if (nuevoPrimerApellido != null && !nuevoPrimerApellido.trim().isEmpty()) {
                empleado.setPrimerApellido(nuevoPrimerApellido.trim());
            }

            if (nuevoSegundoApellido != null && !nuevoSegundoApellido.trim().isEmpty()) {
                empleado.setSegundoApellido(nuevoSegundoApellido.trim());
            }

            if (nuevaEdad != null && !nuevaEdad.trim().isEmpty()) {
                empleado.setEdad(nuevaEdad.trim());
            }

            if (nuevoCorreo != null && !nuevoCorreo.trim().isEmpty()) {
                empleado.setCorreo(nuevoCorreo.trim());
            }

            if (nuevoTelefono != null && !nuevoTelefono.trim().isEmpty()) {
                empleado.setTelefono(nuevoTelefono.trim());
            }

            break; 
        }
    }

    if (!encontrado) {
        JOptionPane.showMessageDialog(null,
                "Empleado no encontrado: " + identificacionOriginal,
                "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    return guardarTodos(empleados);
}

    
    private boolean guardarTodos(List<Empleado> empleados) {
        try (Writer writer = new FileWriter(RUTA_JSON)) {
            gson.toJson(empleados, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo JSON: " + e.getMessage());
            return false;
        }
    }
    
        public List<Empleado> cargarTodos() {
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
            Type tipoListaEmpleado = new TypeToken<ArrayList<Empleado>>() {
            }.getType();
            List<Empleado> empleados = gson.fromJson(reader, tipoListaEmpleado);

          

            return empleados != null ? empleados : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Empleado obtenerEmpleadoPorCedula(String cedula) {
    if (cedula == null || cedula.trim().isEmpty()) {
        return null;
    }

    List<Empleado> empleados = cargarTodos(); 
    for (Empleado empleado : empleados) {
        if (empleado.getIdentificacion().equals(cedula.trim())) {
            return empleado;
        }
    }

    return null;
}

}
