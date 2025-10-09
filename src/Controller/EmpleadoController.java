package Controller;

import DAO.EmpleadoDAO;
import Model.Entities.Empleado;
import java.util.List;

public class EmpleadoController {
    
    private final EmpleadoDAO empleadoDAO;
    private static EmpleadoController instancia;
    
    private EmpleadoController() {
        this.empleadoDAO = EmpleadoDAO.getInstancia();
    }
    
    public static synchronized EmpleadoController getInstancia() {
        if (instancia == null) {
            instancia = new EmpleadoController();
        }
        return instancia;
    }
    
    public boolean registrarEmpleado(
            String identificacion,
            String primerNombre,
            String segundoNombre,
            String primerApellido,
            String segundoApellido,
            String edad,
            String correo,
            String telefono) {
        
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria");
        }
        
        if (primerNombre == null || primerNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El primer nombre es obligatorio");
        }
        
        if (primerApellido == null || primerApellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El primer apellido es obligatorio");
        }
        
        if (edad == null || edad.trim().isEmpty()) {
            throw new IllegalArgumentException("La edad es obligatoria");
        }
        
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }
        
        try {
            int edadNum = Integer.parseInt(edad.trim());
            if (edadNum <= 0 || edadNum > 120) {
                throw new IllegalArgumentException("La edad debe ser un número válido entre 1 y 120");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser un número válido");
        }
        
        if (!correo.trim().contains("@") || !correo.trim().contains(".")) {
            throw new IllegalArgumentException("El formato del correo no es válido");
        }
        
        if (!telefono.trim().matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono debe contener solo números");
        }
        
        String nuevoId = empleadoDAO.generarNuevoId();

        
        Empleado empleado = new Empleado(
        null,                   
        nuevoId,                                       
        primerNombre.trim(),
        segundoNombre != null ? segundoNombre.trim() : "",
        primerApellido.trim(),
        segundoApellido != null ? segundoApellido.trim() : "",
        edad.trim(),
        identificacion.trim(),
        correo.trim().toLowerCase(),
        telefono.trim()
    );
        
        return empleadoDAO.registrarEmpleado(empleado);
    }
    
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoDAO.obtenerEmpleado();
    }
    
    public Empleado buscarEmpleadoPorIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria");
        }
        
        List<Empleado> empleados = empleadoDAO.obtenerEmpleado();
        return empleados.stream()
                .filter(e -> e.getIdentificacion().equals(identificacion.trim()))
                .findFirst()
                .orElse(null);
    }
    
    public boolean actualizarEmpleado(
            String identificacionOriginal,
            String nuevoPrimerNombre,
            String nuevoSegundoNombre,
            String nuevoPrimerApellido,
            String nuevoSegundoApellido,
            String nuevaEdad,
            String nuevoCorreo,
            String nuevoTelefono) {
        
        if (identificacionOriginal == null || identificacionOriginal.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación original es obligatoria");
        }
        
        if (nuevoPrimerNombre == null || nuevoPrimerNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El primer nombre es obligatorio");
        }
        
        if (nuevoPrimerApellido == null || nuevoPrimerApellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El primer apellido es obligatorio");
        }
        
        if (nuevaEdad == null || nuevaEdad.trim().isEmpty()) {
            throw new IllegalArgumentException("La edad es obligatoria");
        }
        
        if (nuevoCorreo == null || nuevoCorreo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        
        if (nuevoTelefono == null || nuevoTelefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }
        
        try {
            int edadNum = Integer.parseInt(nuevaEdad.trim());
            if (edadNum <= 0 || edadNum > 120) {
                throw new IllegalArgumentException("La edad debe ser un número válido entre 1 y 120");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser un número válido");
        }
        
        if (!nuevoCorreo.trim().contains("@") || !nuevoCorreo.trim().contains(".")) {
            throw new IllegalArgumentException("El formato del correo no es válido");
        }
        
        if (!nuevoTelefono.trim().matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono debe contener solo números");
        }
        
        return empleadoDAO.actualizarEmpleado(
                identificacionOriginal.trim(),
                nuevoPrimerNombre.trim(),
                nuevoSegundoNombre != null ? nuevoSegundoNombre.trim() : "",
                nuevoPrimerApellido.trim(),
                nuevoSegundoApellido != null ? nuevoSegundoApellido.trim() : "",
                nuevaEdad.trim(),
                nuevoCorreo.trim().toLowerCase(),
                nuevoTelefono.trim()
        );
    }
    
    public boolean eliminarEmpleado(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria");
        }
        
        return empleadoDAO.eliminarEmpleado(identificacion.trim());
    }
    
    public boolean existeEmpleado(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            return false;
        }
        
        List<Empleado> empleados = empleadoDAO.obtenerEmpleado();
        return empleados.stream()
                .anyMatch(e -> e.getIdentificacion().equals(identificacion.trim()));
    }
    
    public List<Empleado> obtenerEmpleados() {
        return empleadoDAO.cargarTodos();
    }
    
    public boolean validarCamposObligatorios(String identificacion, String primerNombre, 
                                           String primerApellido, String edad, 
                                           String correo, String telefono) {
        return identificacion != null && !identificacion.trim().isEmpty() &&
               primerNombre != null && !primerNombre.trim().isEmpty() &&
               primerApellido != null && !primerApellido.trim().isEmpty() &&
               edad != null && !edad.trim().isEmpty() &&
               correo != null && !correo.trim().isEmpty() &&
               telefono != null && !telefono.trim().isEmpty();
    }
}
