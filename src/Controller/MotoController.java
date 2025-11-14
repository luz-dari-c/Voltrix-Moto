package Controller;

import DAO.MotoDAO;
import Model.Constants.*;
import Model.Entities.Moto;
import Validator.Validation;
import java.awt.Color;
import java.awt.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class MotoController {

    private final MotoDAO motoDAO;
    private static MotoController instancia;

    public MotoController() {
        this.motoDAO = MotoDAO.getInstancia();
    }

    public static MotoController getInstancia() {
        if (instancia == null) {
            instancia = new MotoController();
        }
        return instancia;
    }

    public boolean guardarMoto(Moto moto) {
        if (moto == null) {
            System.err.println("No se puede guardar una moto nula");
            return false;
        }
        return motoDAO.guardarMoto(moto);
    }

    public boolean eliminarMoto(int idMoto) {
        if (idMoto <= 0) {
            System.err.println("El ID de la moto no es valido");
            return false;
        }
        return motoDAO.eliminarMoto(idMoto);
    }

    public boolean actualizarPorPlacaBase(
            String placaBaseSeleccionada,
            String nuevoModelo,
            Boolean nuevaParrilla,
            Boolean nuevoMaletero,
            Integer nuevoCilindraje,
            // Motor
            TipoMotor nuevoTipoMotor,
            Integer nuevaCilindradaMotor,
            Integer nuevaPotenciaMotor,
            // Chasis
            MaterialChasis nuevoMaterialChasis,
            TipoChasis nuevoTipoChasis,
            // Asiento
            MaterialAsiento nuevoMaterialAsiento,
            CapacidadAsiento nuevaCapacidadAsiento,
            // Transmisión
            TipoTransmision nuevoTipoTransmision,
            TipoVelocidades nuevasVelocidades,
            // Llanta
            String tipoLlantaSeleccionada,
            MedidaLlanta nuevaMedidaLlanta,
            String nuevaMarcaLlanta,
            MaterialLlanta nuevoMaterialLlanta,
            String nuevoModeloLlanta,
            // Freno
            String tipoFrenoSeleccionado,
            String nuevaMarcaFreno,
            MaterialFreno nuevoMaterialFreno,
            String nuevoModeloFreno
    ) {
        if (placaBaseSeleccionada == null || placaBaseSeleccionada.equals("Seleccionar")) {
            System.err.println("Debes seleccionar una placa base válida.");
            return false;
        }

        if (!placaBaseSeleccionada.startsWith("BSE-")) {
            System.err.println("La placa seleccionada debe pertenecer a una moto base (BSE-...).");
            return false;
        }

        boolean hayCambios
                = (nuevoModelo != null && !nuevoModelo.isEmpty())
                || nuevaParrilla != null || nuevoMaletero != null
                || nuevoCilindraje != null
                || nuevoTipoMotor != null || nuevaCilindradaMotor != null || nuevaPotenciaMotor != null
                || nuevoMaterialChasis != null || nuevoTipoChasis != null
                || nuevoMaterialAsiento != null || nuevaCapacidadAsiento != null
                || nuevoTipoTransmision != null || nuevasVelocidades != null
                || (tipoLlantaSeleccionada != null && !tipoLlantaSeleccionada.equals("Seleccionar"))
                || (tipoFrenoSeleccionado != null && !tipoFrenoSeleccionado.equals("Seleccionar"));

        if (!hayCambios) {
            JOptionPane.showMessageDialog(
                    null,
                    "No se realizó ningún cambio. Todos los campos están vacíos o sin selección.",
                    "Sin cambios",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        return motoDAO.actualizarPorPlacaBase(
                placaBaseSeleccionada,
                nuevoModelo,
                nuevaParrilla,
                nuevoMaletero,
                nuevoCilindraje,
                nuevoTipoMotor,
                nuevaCilindradaMotor,
                nuevaPotenciaMotor,
                nuevoMaterialChasis,
                nuevoTipoChasis,
                nuevoMaterialAsiento,
                nuevaCapacidadAsiento,
                nuevoTipoTransmision,
                nuevasVelocidades,
                tipoLlantaSeleccionada,
                nuevaMedidaLlanta,
                nuevaMarcaLlanta,
                nuevoMaterialLlanta,
                nuevoModeloLlanta,
                tipoFrenoSeleccionado,
                nuevaMarcaFreno,
                nuevoMaterialFreno,
                nuevoModeloFreno
        );
    }

    public boolean actualizarMotoIndividual(
            int idMoto,
            String nuevaMarca,
            TipoColorMoto nuevoColor
    ) {
        if (idMoto <= 0) {
            System.err.println("El ID de la moto no es valido");
            return false;
        }

        if (nuevaMarca == null && nuevoColor == null) {
            System.err.println("Debe proporcionar al menos una marca o color para actualizar");
            return false;
        }

        return motoDAO.actualizarMotoIndividual(idMoto, nuevaMarca, nuevoColor);
    }

    public List<Moto> listarMotos() {
        return motoDAO.cargarTodas();
    }

    public Moto buscarPorId(int idMoto) {
        if (idMoto <= 0) {
            System.err.println("El ID de la moto no es valido");
            return null;
        }

        return motoDAO.cargarTodas().stream()
                .filter(m -> m.getIdMoto() == idMoto)
                .findFirst()
                .orElse(null);
    }

    public List<Moto> obtenerMotosDisponiblesPorTipo(TipoMoto tipo) {
        return motoDAO.obtenerMotosPorTipoYDisponibles(tipo);
    }

    public Map<TipoColorMoto, Integer> contarPorColor(TipoMoto tipo) {
        List<Moto> disponibles = motoDAO.obtenerMotosPorTipoYDisponibles(tipo);
        Map<TipoColorMoto, Integer> conteo = new HashMap<>();

        for (Moto moto : disponibles) {
            TipoColorMoto color = moto.getTipoColorMoto();
            conteo.put(color, conteo.getOrDefault(color, 0) + 1);
        }

        for (TipoColorMoto color : TipoColorMoto.values()) {
            conteo.putIfAbsent(color, 0);
        }

        return conteo;
    }

    public boolean disminuirMotoPorColorYTipo(TipoMoto tipo, TipoColorMoto color) {
        List<Moto> disponibles = motoDAO.obtenerMotosPorTipoYDisponibles(tipo);

        for (Moto moto : disponibles) {
            if (moto.getTipoColorMoto() == color && moto.getEstado() == EstadoMoto.DISPONIBLE) {
                moto.setEstado(EstadoMoto.VENDIDO);
                motoDAO.actualizarMoto(moto);
                return true;
            }
        }

        return false;
    }

    public boolean actualizarMoto(Moto motoSeleccionada) {
        if (motoSeleccionada == null) {
            System.out.println("No hay ninguna moto para actualizar (Null)");

        } else {
            motoDAO.actualizarMoto(motoSeleccionada);
            return true;
        }

        return false;
    }

    public Moto obtenerMotoBase() {
        return motoDAO.obtenerMotoBase();
    }

    public Moto obtenerMotoBasePorTipo(TipoMoto tipo) {
        return motoDAO.obtenerMotoBasePorTipo(tipo);
    }

    public boolean duplicarMotoPorTipo(
            TipoMoto tipoMoto,
            TipoColorMoto nuevoColor,
            String nuevaMarca,
            int cantidad
    ) {
        if (tipoMoto == null) {
            System.err.println("El tipo de moto no puede ser nulo.");
            return false;
        }

        if (cantidad <= 0) {
            System.err.println("La cantidad debe ser un número positivo mayor que 0.");
            return false;
        }

        return motoDAO.duplicarMotoPorTipo(tipoMoto, nuevoColor, nuevaMarca, cantidad);
    }

    public boolean agregarMotosDesdeUI(
            String tipoSeleccionado,
            String colorSeleccionado,
            String marcaSeleccionada,
            int cantidad,
            JTextField campoMarca,
            JSpinner spinnerCantidad,
            Component parent
    ) {
        try {
            if (tipoSeleccionado == null || tipoSeleccionado.equals("Seleccionar")
                    || colorSeleccionado == null || colorSeleccionado.equals("Seleccionar")
                    || marcaSeleccionada == null || marcaSeleccionada.isEmpty()) {

                JOptionPane.showMessageDialog(parent,
                        "Debes completar todos los campos antes de continuar.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Validación de formato de marca
            if (!Validation.validarMarca(marcaSeleccionada)) {
                campoMarca.setBackground(new Color(255, 200, 200));
                JOptionPane.showMessageDialog(parent,
                        "La marca solo puede contener letras, números, espacios, guiones, puntos y &.",
                        "Error en marca",
                        JOptionPane.ERROR_MESSAGE);
                campoMarca.requestFocus();
                return false;
            } else {
                campoMarca.setBackground(new Color(200, 255, 200));
            }
            if (!Validation.validarSpinnerNumerico(spinnerCantidad,
                    "La cantidad debe ser numérica y mayor que cero.")) {
                return false;
            }

            TipoMoto tipoMoto = TipoMoto.valueOf(tipoSeleccionado.toUpperCase());
            TipoColorMoto tipoColorMoto = TipoColorMoto.valueOf(colorSeleccionado.toUpperCase());

            // Llamada real al DAO
            boolean exito = duplicarMotoPorTipo(tipoMoto, tipoColorMoto, marcaSeleccionada, cantidad);

            if (exito) {
                JOptionPane.showMessageDialog(parent,
                        "Motos añadidas correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent,
                        "Error al añadir las motos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            return exito;

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(parent,
                    "El tipo o color seleccionado no existe en los enumeradores.",
                    "Error de datos",
                    JOptionPane.ERROR_MESSAGE);
            return false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,
                    "Error inesperado: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean modificarMotoBaseDesdeUI(
            Component parent,
            JComboBox comboBoxMotoBasePlaca,
            JTextField txtNuevoModeloMoto,
            JComboBox TieneParrillaNueva,
            JComboBox TieneMaleteroNueva,
            JTextField NuevoCilindrajeMoto,
            JComboBox NuevoTipoMotor,
            JTextField NuevaPotenciaMotor,
            JTextField NuevaCilindradaMotor,
            JComboBox comboBoxTipoLlanta,
            JComboBox NuevaMedidaLlantaCombo,
            JTextField txtNuevaMarcaLlanta,
            JComboBox NuevoMaterialLlantaCombo,
            JTextField txtNuevoModeloLlanta,
            JComboBox NuevoMaterialChasis,
            JComboBox NuevoTipoChasis,
            JComboBox comboBoxTipoFreno,
            JTextField txtNuevaMarcaFreno,
            JComboBox NuevoMaterialFreno,
            JTextField txtNuevoModeloFreno,
            JComboBox NuevaCapacidadAsiento,
            JComboBox NuevoMaterialAsiento,
            JComboBox NuevoTipoTransmision,
            JComboBox NuevaVelocidadesTransmision
    ) {

        try {

            if (comboBoxMotoBasePlaca.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(parent, "Debes seleccionar una placa base válida.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            String placaBase = comboBoxMotoBasePlaca.getSelectedItem().toString();
            String nuevoModelo = txtNuevoModeloMoto.getText().trim();

            Boolean nuevaParrilla = null;
            String seleccionParrilla = (String) TieneParrillaNueva.getSelectedItem();
            if (seleccionParrilla != null && !seleccionParrilla.equalsIgnoreCase("Seleccionar")) {
                nuevaParrilla = seleccionParrilla.equalsIgnoreCase("Si");
            }

            Boolean nuevoMaletero = null;
            String seleccionMaletero = (String) TieneMaleteroNueva.getSelectedItem();
            if (seleccionMaletero != null && !seleccionMaletero.equalsIgnoreCase("Seleccionar")) {
                nuevoMaletero = seleccionMaletero.equalsIgnoreCase("Si");
            }

            Integer nuevoCilindraje = null;
            if (!NuevoCilindrajeMoto.getText().trim().isEmpty()) {
                try {
                    nuevoCilindraje = Integer.parseInt(NuevoCilindrajeMoto.getText().trim());
                    if (nuevoCilindraje <= 0 || nuevoCilindraje < 50 || nuevoCilindraje > 2000) {
                        JOptionPane.showMessageDialog(parent, "Cilindraje inválido.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(parent, "Cilindraje debe ser un número.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

            // Motor
            TipoMotor nuevoTipoMotor = NuevoTipoMotor.getSelectedIndex() > 0
                    ? TipoMotor.valueOf(NuevoTipoMotor.getSelectedItem().toString().toUpperCase())
                    : null;

            Integer nuevaPotenciaMotor = null;
            if (!NuevaPotenciaMotor.getText().trim().isEmpty()) {
                try {
                    nuevaPotenciaMotor = Integer.parseInt(NuevaPotenciaMotor.getText().trim());
                    if (nuevaPotenciaMotor <= 0 || nuevaPotenciaMotor < 1 || nuevaPotenciaMotor > 300) {
                        JOptionPane.showMessageDialog(parent, "Potencia inválida.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(parent, "Potencia debe ser un número.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

            Integer nuevaCilindrada = null;
            if (!NuevaCilindradaMotor.getText().trim().isEmpty()) {
                try {
                    nuevaCilindrada = Integer.parseInt(NuevaCilindradaMotor.getText().trim());
                    if (nuevaCilindrada <= 0 || nuevaCilindrada < 50 || nuevaCilindrada > 2000) {
                        JOptionPane.showMessageDialog(parent, "Cilindrada inválida.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(parent, "Cilindrada inválida.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

            // Llanta
            String tipoLlanta = comboBoxTipoLlanta.getSelectedIndex() > 0
                    ? comboBoxTipoLlanta.getSelectedItem().toString()
                    : null;

            MedidaLlanta nuevaMedidaLlanta = NuevaMedidaLlantaCombo.getSelectedIndex() > 0
                    ? MedidaLlanta.valueOf(NuevaMedidaLlantaCombo.getSelectedItem().toString().toUpperCase())
                    : null;

            String nuevaMarcaLlanta = txtNuevaMarcaLlanta.getText().trim().isEmpty()
                    ? null : txtNuevaMarcaLlanta.getText().trim();

            if (nuevaMarcaLlanta != null && !Validation.validarMarca(nuevaMarcaLlanta)) {
                txtNuevaMarcaLlanta.setBackground(new Color(255, 200, 200));
                JOptionPane.showMessageDialog(parent, "Marca llanta inválida.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                txtNuevaMarcaLlanta.requestFocus();
                return false;
            }

            MaterialLlanta nuevoMaterialLlanta = NuevoMaterialLlantaCombo.getSelectedIndex() > 0
                    ? MaterialLlanta.valueOf(NuevoMaterialLlantaCombo.getSelectedItem().toString().toUpperCase())
                    : null;

            String nuevoModeloLlanta = txtNuevoModeloLlanta.getText().trim().isEmpty()
                    ? null : txtNuevoModeloLlanta.getText().trim();

            // Chasis
            MaterialChasis nuevoMaterialChasis = NuevoMaterialChasis.getSelectedIndex() > 0
                    ? MaterialChasis.valueOf(NuevoMaterialChasis.getSelectedItem().toString().toUpperCase())
                    : null;

            TipoChasis nuevoTipoChasis = NuevoTipoChasis.getSelectedIndex() > 0
                    ? TipoChasis.valueOf(NuevoTipoChasis.getSelectedItem().toString().toUpperCase())
                    : null;

            // Freno
            String tipoFreno = comboBoxTipoFreno.getSelectedIndex() > 0
                    ? comboBoxTipoFreno.getSelectedItem().toString()
                    : null;

            String nuevaMarcaFreno = txtNuevaMarcaFreno.getText().trim().isEmpty()
                    ? null : txtNuevaMarcaFreno.getText().trim();

            if (nuevaMarcaFreno != null && !Validation.validarMarca(nuevaMarcaFreno)) {
                txtNuevaMarcaFreno.setBackground(new Color(255, 200, 200));
                JOptionPane.showMessageDialog(parent, "Marca freno inválida.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                txtNuevaMarcaFreno.requestFocus();
                return false;
            }

            MaterialFreno nuevoMaterialFreno = NuevoMaterialFreno.getSelectedIndex() > 0
                    ? MaterialFreno.valueOf(NuevoMaterialFreno.getSelectedItem().toString().toUpperCase())
                    : null;

            String nuevoModeloFreno = txtNuevoModeloFreno.getText().trim().isEmpty()
                    ? null : txtNuevoModeloFreno.getText().trim();

            // Asiento
            CapacidadAsiento nuevaCapacidadAsiento = NuevaCapacidadAsiento.getSelectedIndex() > 0
                    ? CapacidadAsiento.valueOf(NuevaCapacidadAsiento.getSelectedItem().toString().toUpperCase())
                    : null;

            MaterialAsiento nuevoMaterialAsiento = NuevoMaterialAsiento.getSelectedIndex() > 0
                    ? MaterialAsiento.valueOf(NuevoMaterialAsiento.getSelectedItem().toString().toUpperCase())
                    : null;

            // Transmisión
            TipoTransmision nuevoTipoTransmision = NuevoTipoTransmision.getSelectedIndex() > 0
                    ? TipoTransmision.valueOf(NuevoTipoTransmision.getSelectedItem().toString().toUpperCase())
                    : null;

            TipoVelocidades nuevasVelocidades = NuevaVelocidadesTransmision.getSelectedIndex() > 0
                    ? TipoVelocidades.valueOf(NuevaVelocidadesTransmision.getSelectedItem().toString().toUpperCase())
                    : null;

            boolean exito = actualizarPorPlacaBase(
                    placaBase,
                    nuevoModelo,
                    nuevaParrilla,
                    nuevoMaletero,
                    nuevoCilindraje,
                    nuevoTipoMotor,
                    nuevaCilindrada,
                    nuevaPotenciaMotor,
                    nuevoMaterialChasis,
                    nuevoTipoChasis,
                    nuevoMaterialAsiento,
                    nuevaCapacidadAsiento,
                    nuevoTipoTransmision,
                    nuevasVelocidades,
                    tipoLlanta,
                    nuevaMedidaLlanta,
                    nuevaMarcaLlanta,
                    nuevoMaterialLlanta,
                    nuevoModeloLlanta,
                    tipoFreno,
                    nuevaMarcaFreno,
                    nuevoMaterialFreno,
                    nuevoModeloFreno
            );

            if (exito) {
                JOptionPane.showMessageDialog(parent,
                        "Moto base y unidades del mismo tipo actualizadas correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            return exito;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,
                    "Error inesperado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean modificarMotoIndividualDesdeUI(
        Component parent,
        JLabel txtIdMotoModificar,
        JTextField txtNuevaMarcaMoto,
        JComboBox comboBoxNuevoColorMoto1
) {
    try {
        if (txtIdMotoModificar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Debes ingresar un ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int idMoto;
        try {
            idMoto = Integer.parseInt(txtIdMotoModificar.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "ID inválido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String nuevaMarca = txtNuevaMarcaMoto.getText().trim().isEmpty()
                ? null : txtNuevaMarcaMoto.getText().trim();

        if (nuevaMarca != null && !Validation.validarMarca(nuevaMarca)) {
            txtNuevaMarcaMoto.setBackground(new Color(255, 200, 200));
            JOptionPane.showMessageDialog(parent, "Marca inválida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtNuevaMarcaMoto.requestFocus();
            return false;
        }

        TipoColorMoto nuevoColor = null;
        if (comboBoxNuevoColorMoto1.getSelectedIndex() > 0) {
            try {
                nuevoColor = TipoColorMoto.valueOf(comboBoxNuevoColorMoto1.getSelectedItem().toString().toUpperCase());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Color inválido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if (nuevaMarca == null && nuevoColor == null) {
            JOptionPane.showMessageDialog(parent,
                    "Debes ingresar al menos una modificación.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        boolean exito = actualizarMotoIndividual(idMoto, nuevaMarca, nuevoColor);

        if (exito) {
            JOptionPane.showMessageDialog(parent, "Moto modificada correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }

        return exito;

    } catch (Exception e) {
        JOptionPane.showMessageDialog(parent, "Error inesperado: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}

}
