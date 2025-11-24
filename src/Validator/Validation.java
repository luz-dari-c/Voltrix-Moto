package Validator;

import javax.swing.*;

import java.awt.*;

import java.util.regex.Pattern;

public class Validation {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(gmail\\.com|hotmail\\.com|unicolombo\\.edu\\.co)$"
    );
    private static final Pattern LETTERS_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    private static final Pattern NUMBERS_PATTERN = Pattern.compile("^[0-9]+$");

    private static final Color ERROR_COLOR = new Color(255, 200, 200);
    private static final Color SUCCESS_COLOR = new Color(200, 255, 200);
    private static final Color DEFAULT_COLOR = Color.WHITE;

    public static boolean validarNombre(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        return LETTERS_PATTERN.matcher(texto.trim()).matches();
    }

    public static boolean validarNumeros(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        return NUMBERS_PATTERN.matcher(texto.trim()).matches();
    }

    public static boolean validarCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(correo.trim().toLowerCase()).matches();
    }

    public static boolean validarFormulario(JTextField... campos) {
        for (JTextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                campo.setBackground(ERROR_COLOR);
                JOptionPane.showMessageDialog(campo,
                        "Todos los campos son obligatorios",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE);
                campo.requestFocusInWindow();
                return false;
            }
        }
        return true;
    }

    public static void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
            campo.setBackground(DEFAULT_COLOR);
            campo.setToolTipText(null);
        }
    }

    public static boolean validarSpinnerNumerico(JSpinner spinner, String mensaje) {
        try {
            Object valor = spinner.getValue();

            if (valor instanceof Number) {
                int numero = ((Number) valor).intValue();

                if (numero <= 0) {
                    spinner.getEditor().getComponent(0).setBackground(new Color(255, 200, 200));
                    JOptionPane.showMessageDialog(null, mensaje, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    spinner.requestFocusInWindow();
                    return false;
                } else {
                    spinner.getEditor().getComponent(0).setBackground(new Color(200, 255, 200));
                    return true;
                }
            } else {
                spinner.getEditor().getComponent(0).setBackground(new Color(255, 200, 200));
                JOptionPane.showMessageDialog(null,
                        "El valor del spinner debe ser numérico.",
                        "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
                spinner.requestFocusInWindow();
                return false;
            }
        } catch (Exception e) {
            spinner.getEditor().getComponent(0).setBackground(new Color(255, 200, 200));
            JOptionPane.showMessageDialog(null,
                    "Valor inválido en el spinner.",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
            spinner.requestFocusInWindow();
            return false;
        }
    }

    public static boolean validarMarca(String marca) {
        return marca != null && marca.matches("[A-Za-z0-9\\-\\.&\\s]+");
    }

    public static boolean validarMinimoLetras(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            return false;
        }

        String texto = marca.trim();

        if (!texto.matches("[A-Za-z0-9\\-\\.&\\s]+")) {
            return false;
        }

        long letras = texto.chars()
                .filter(Character::isLetter)
                .count();

        return letras >= 3;
    }

}
