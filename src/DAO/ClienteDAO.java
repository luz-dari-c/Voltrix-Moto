package DAO;

import Model.Entities.Cliente;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Base64;

public class ClienteDAO {

    private static final String RUTA_PERSISTENCIA = "src/Resources/Data/clientes.json";
    private static ClienteDAO instancia;
    private final Gson gson;
    private final List<Cliente> clientes;

    private ClienteDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.clientes = cargarClientes();
    }

    public static synchronized ClienteDAO getInstance() {
        if (instancia == null) {
            instancia = new ClienteDAO();
        }
        return instancia;
    }

    private String encriptar(String texto) {
        if (texto == null) return null;
        return Base64.getEncoder().encodeToString(texto.getBytes());
    }

    private String desencriptar(String texto) {
        if (texto == null) return null;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(texto);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            return texto;
        }
    }

    private List<Cliente> cargarClientes() {
        List<Cliente> lista = new ArrayList<>();
        File archivo = new File(RUTA_PERSISTENCIA);

        if (archivo.exists() && archivo.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                Type listType = new TypeToken<List<Cliente>>() {}.getType();
                lista = gson.fromJson(reader, listType);
                if (lista == null) lista = new ArrayList<>();
            } catch (IOException e) {
                System.err.println("Error al cargar clientes: " + e.getMessage());
            }
        }
        return lista;
    }

    private synchronized void guardarClientes() {
        File archivo = new File(RUTA_PERSISTENCIA);
        archivo.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(clientes, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar clientes: " + e.getMessage());
        }
    }

 public synchronized boolean registrarCliente(Cliente cliente) {
    if (buscarPorCedula(cliente.getCedula()) != null) {
        return false;
    }

    cliente.setNumeroTarjeta(encriptar(cliente.getNumeroTarjeta()));
    cliente.setCvv(encriptar(cliente.getCvv()));
    cliente.setPassword(encriptar(cliente.getPassword())); 

    clientes.add(cliente);
    guardarClientes();
    return true;
    
}


    
  public synchronized boolean actualizarTarjeta(String cedula, String numero, String nombre, String fecha, String cvv) {
    Cliente cliente = buscarPorCedula(cedula);
    if (cliente != null) {
        if (!esBase64(numero)) numero = encriptar(numero);
        if (!esBase64(cvv)) cvv = encriptar(cvv);

        cliente.setNumeroTarjeta(numero);
        cliente.setNombreTarjeta(nombre);
        cliente.setFechaExpiracion(fecha);
        cliente.setCvv(cvv);
        cliente.setTarjetaGuardada(true);

        guardarClientes();
        return true;
    }
    return false;
}


public Cliente buscarPorCedula(String cedula) {
    Cliente clienteOriginal = clientes.stream()
            .filter(c -> c.getCedula().equals(cedula))
            .findFirst()
            .orElse(null);

    if (clienteOriginal == null) {
        return null;
    }

   
    Cliente copia = new Cliente(
            clienteOriginal.getPrimerNombre(),
            clienteOriginal.getSegundoNombre(),
            clienteOriginal.getPrimerApellido(),
            clienteOriginal.getSegundoApellido(),
            clienteOriginal.getCedula(),
            clienteOriginal.getEmail(),
            desencriptar(clienteOriginal.getPassword()),
            desencriptar(clienteOriginal.getNumeroTarjeta()),
            clienteOriginal.getNombreTarjeta(),
            clienteOriginal.getFechaExpiracion(),
            desencriptar(clienteOriginal.getCvv()),
            clienteOriginal.isTarjetaGuardada()
    );

    return copia;
}

  
  private boolean esBase64(String texto) {
    if (texto == null) return false;
    try {
        Base64.getDecoder().decode(texto);
        return true;
    } catch (IllegalArgumentException e) {
        return false;
    }
}




    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes);
    }
}
