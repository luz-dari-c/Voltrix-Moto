package Controller;

import DAO.EmpleadoDAO;
import Model.Entities.Empleado;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class EmpleadoController {

    private static final Pattern PATRON_SOLO_LETRAS = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final Pattern PATRON_SOLO_NUMEROS = Pattern.compile("^\\d+$");
    private static final Pattern PATRON_EMAIL = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");

    private static EmpleadoController instancia;
    private final EmpleadoDAO empleadoDAO;

    private EmpleadoController() {
        this.empleadoDAO = EmpleadoDAO.getInstance();
    }

    public static synchronized EmpleadoController getInstance() {
        if (instancia == null) {
            instancia = new EmpleadoController();
        }
        return instancia;
    }

    public boolean registrarEmpleado(Empleado empleado) {
        List<String> errores = validarCamposEmpleado(empleado);
        if (!errores.isEmpty()) {
            JOptionPane.showMessageDialog(null, String.join("\n", errores), "Errores en el registro", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return empleadoDAO.registrarEmpleado(empleado);
    }

    public List<Empleado> obtenerTodos() {
        return empleadoDAO.cargarTodos();
    }

    public boolean eliminarEmpleado(String identificacion) {
        if (identificacion == null || identificacion.isEmpty()) {
            return false;
        }
        return empleadoDAO.eliminarEmpleado(identificacion);
    }

  public List<String> actualizarEmpleado(
        String identificacionOriginal,
        String nuevaIdentificacion,
        String nuevoPrimerNombre,
        String nuevoSegundoNombre,
        String nuevoPrimerApellido,
        String nuevoSegundoApellido,
        String nuevaEdad,
        String nuevoCorreo,
        String nuevoTelefono) {

    List<String> errores = new ArrayList<>();

    if (identificacionOriginal == null || identificacionOriginal.trim().isEmpty()) {
        errores.add("Identificación original: Campo obligatorio");
        return errores;
    }

    if (nuevaIdentificacion != null && !nuevaIdentificacion.trim().isEmpty()) {
        String cedula = nuevaIdentificacion.trim();
        if (!PATRON_SOLO_NUMEROS.matcher(cedula).matches()) {
            errores.add("Cédula: Solo se permiten números");
        } else if (cedula.length() < 7 || cedula.length() > 10) {
            errores.add("Cédula: Debe tener entre 7 y 10 dígitos");
        }
    }

    if (nuevaEdad != null && !nuevaEdad.trim().isEmpty()) {
        try {
            int edad = Integer.parseInt(nuevaEdad.trim());
            if (edad < 18 || edad > 80) {
                errores.add("Edad: Debe estar entre 18 y 80 años.");
            }
        } catch (NumberFormatException e) {
            errores.add("Edad: Debe ser un número válido.");
        }
    }

    if (nuevoCorreo != null && !nuevoCorreo.trim().isEmpty()) {
        if (!PATRON_EMAIL.matcher(nuevoCorreo.trim()).matches()) {
            errores.add("Correo electrónico: Formato inválido. Use @gmail.com, @hotmail.com, @outlook.com o @unicolombo.edu.co");
        }
    }

    if (nuevoTelefono != null && !nuevoTelefono.trim().isEmpty()) {
        if (!PATRON_SOLO_NUMEROS.matcher(nuevoTelefono.trim()).matches()) {
            errores.add("Teléfono: Solo se permiten números");
        } else if (nuevoTelefono.trim().length() != 10) {
            errores.add("Teléfono: Debe tener exactamente 10 dígitos");
        }
    }

    if (!errores.isEmpty()) {
        System.out.println("Errores de validación: " + errores);
        return errores;
    }

    boolean actualizado = empleadoDAO.actualizarEmpleado(
            identificacionOriginal,
            nuevaIdentificacion != null && !nuevaIdentificacion.trim().isEmpty() ? nuevaIdentificacion : identificacionOriginal,
            nuevoPrimerNombre,
            nuevoSegundoNombre,
            nuevoPrimerApellido,
            nuevoSegundoApellido,
            nuevaEdad,
            nuevoCorreo,
            nuevoTelefono
    );

    if (!actualizado) {
        errores.add("No se pudo actualizar el empleado en la base de datos");
    }

    return errores;
}

  


    public Empleado buscarPorIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.isEmpty()) {
            return null;
        }
        List<Empleado> empleados = empleadoDAO.cargarTodos();
        return empleados.stream()
                .filter(e -> e.getIdentificacion().equals(identificacion))
                .findFirst()
                .orElse(null);
    }

    public String generarIdEmpleado() {
        List<Empleado> empleados = obtenerTodos();
        int max = 0;

        for (Empleado e : empleados) {
            String id = e.getIdEmpleado();
            if (id != null && id.startsWith("EMP")) {
                try {
                    int numero = Integer.parseInt(id.substring(3));
                    if (numero > max) {
                        max = numero;
                    }
                } catch (NumberFormatException ex) {
                }
            }
        }

        int nuevoNumero = max + 1;
        return String.format("EMP%03d", nuevoNumero);
    }

    private List<String> validarCamposEmpleado(Empleado empleado) {
        List<String> errores = new ArrayList<>();

        if (empleado.getPrimerNombre() == null || empleado.getPrimerNombre().trim().isEmpty()) {
            errores.add("Primer nombre: Campo obligatorio");
        } else if (!PATRON_SOLO_LETRAS.matcher(empleado.getPrimerNombre().trim()).matches()) {
            errores.add("Primer nombre: Solo se permiten letras y espacios");
        } else if (empleado.getPrimerNombre().trim().length() < 2) {
            errores.add("Primer nombre: Debe tener al menos 2 caracteres");
        }

        if (empleado.getSegundoNombre() != null && !empleado.getSegundoNombre().trim().isEmpty()) {
            if (!PATRON_SOLO_LETRAS.matcher(empleado.getSegundoNombre().trim()).matches()) {
                errores.add("Segundo nombre: Solo se permiten letras y espacios");
            } else if (empleado.getSegundoNombre().trim().length() < 2) {
                errores.add("Segundo nombre: Debe tener al menos 2 caracteres");
            }
        }

        if (empleado.getPrimerApellido() == null || empleado.getPrimerApellido().trim().isEmpty()) {
            errores.add("Primer apellido: Campo obligatorio");
        } else if (!PATRON_SOLO_LETRAS.matcher(empleado.getPrimerApellido().trim()).matches()) {
            errores.add("Primer apellido: Solo se permiten letras y espacios");
        } else if (empleado.getPrimerApellido().trim().length() < 2) {
            errores.add("Primer apellido: Debe tener al menos 2 caracteres");
        }

        if (empleado.getSegundoApellido() == null || empleado.getSegundoApellido().trim().isEmpty()) {
            errores.add("Segundo apellido: Campo obligatorio");
        } else if (!PATRON_SOLO_LETRAS.matcher(empleado.getSegundoApellido().trim()).matches()) {
            errores.add("Segundo apellido: Solo se permiten letras y espacios");
        } else if (empleado.getSegundoApellido().trim().length() < 2) {
            errores.add("Segundo apellido: Debe tener al menos 2 caracteres");
        }

        if (empleado.getEdad() == null || empleado.getEdad().trim().isEmpty()) {
            errores.add("Edad: Campo obligatorio");
        } else if (!PATRON_SOLO_NUMEROS.matcher(empleado.getEdad().trim()).matches()) {
            errores.add("Edad: Solo se permiten números");
        } else {
            int edad = Integer.parseInt(empleado.getEdad().trim());
            if (edad < 18 || edad > 80) {
                errores.add("Edad: Debe estar entre 18 y 80 años");
            }
        }

        if (empleado.getIdentificacion() == null || empleado.getIdentificacion().trim().isEmpty()) {
            errores.add("Identificación: Campo obligatorio");
        } else if (!PATRON_SOLO_NUMEROS.matcher(empleado.getIdentificacion().trim()).matches()) {
            errores.add("Identificación: Solo se permiten números");
        } else if (empleado.getIdentificacion().trim().length() < 7 || empleado.getIdentificacion().trim().length() > 10) {
            errores.add("Identificación: Debe tener entre 7 y 10 dígitos");
        }

        if (empleado.getCorreo() == null || empleado.getCorreo().trim().isEmpty()) {
            errores.add("Correo electrónico: Campo obligatorio");
        } else if (!PATRON_EMAIL.matcher(empleado.getCorreo().trim()).matches()) {
            errores.add("Correo electrónico: Formato de email inválido");
        }

        if (empleado.getTelefono() == null || empleado.getTelefono().trim().isEmpty()) {
            errores.add("Teléfono: Campo obligatorio");
        } else if (!PATRON_SOLO_NUMEROS.matcher(empleado.getTelefono().trim()).matches()) {
            errores.add("Teléfono: Solo se permiten números");
        } else if (empleado.getTelefono().trim().length() != 10) {
            errores.add("Teléfono: Debe tener exactamente 10 dígitos");
        }

        return errores;
    }

    public boolean existeEmpleadoPorCedula(String cedula) {
        return empleadoDAO.obtenerEmpleadoPorCedula(cedula) != null;
    }

}
