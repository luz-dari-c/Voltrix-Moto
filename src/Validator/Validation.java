/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

/**
 * Clase de utilidad para validaciones de campos de texto Implementa buenas
 * prácticas y patrones de diseño
 */
public class Validation {

    // Patrones de validación
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(gmail\\.com|hotmail\\.com|unicolombo\\.edu\\.co)$"
    );
    private static final Pattern LETTERS_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    private static final Pattern NUMBERS_PATTERN = Pattern.compile("^[0-9]+$");

    // Colores para feedback visual
    private static final Color ERROR_COLOR = new Color(255, 200, 200);
    private static final Color SUCCESS_COLOR = new Color(200, 255, 200);
    private static final Color DEFAULT_COLOR = Color.WHITE;

    /**
     * Valida que un texto contenga solo letras y espacios
     */
    public static boolean validarNombre(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        return LETTERS_PATTERN.matcher(texto.trim()).matches();
    }

    /**
     * Valida que un texto contenga solo números
     */
    public static boolean validarNumeros(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        return NUMBERS_PATTERN.matcher(texto.trim()).matches();
    }

    /**
     * Valida el formato de correo electrónico
     */
    public static boolean validarCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(correo.trim().toLowerCase()).matches();
    }

    /**
     * Configura un JTextField para aceptar solo letras en tiempo real
     */
    public static void configurarCampoLetras(JTextField textField) {
        // DocumentFilter para restringir entrada
        ((PlainDocument) textField.getDocument()).setDocumentFilter(new javax.swing.text.DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string == null) {
                    return;
                }

                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (LETTERS_PATTERN.matcher(newText).matches()) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text == null) {
                    return;
                }

                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

                if (LETTERS_PATTERN.matcher(newText).matches()) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        // Feedback visual
        agregarFeedbackVisual(textField, "Solo se permiten letras");
    }

    /**
     * Configura un JTextField para aceptar solo números en tiempo real
     */
    public static void configurarCampoNumeros(JTextField textField) {
        // DocumentFilter para restringir entrada
        ((PlainDocument) textField.getDocument()).setDocumentFilter(new javax.swing.text.DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string == null) {
                    return;
                }

                if (string.matches("[0-9]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text == null) {
                    return;
                }

                if (text.matches("[0-9]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        // Feedback visual
        agregarFeedbackVisual(textField, "Solo se permiten números");
    }

    /**
     * Configura un JTextField para validar correo electrónico con focus lost
     */
    public static void configurarCampoCorreo(JTextField textField) {
        // Validación al perder el foco
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String correo = textField.getText().trim();

                if (!correo.isEmpty() && !validarCorreo(correo)) {
                    textField.setBackground(ERROR_COLOR);
                    JOptionPane.showMessageDialog(textField,
                            "Correo inválido. Debe terminar en: @gmail.com, @hotmail.com o @unicolombo.edu.co",
                            "Error de Validación",
                            JOptionPane.ERROR_MESSAGE);
                    textField.requestFocusInWindow();
                    textField.selectAll();
                } else {
                    textField.setBackground(correo.isEmpty() ? DEFAULT_COLOR : SUCCESS_COLOR);
                }
            }
        });

        // Restaurar color al ganar foco
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBackground(DEFAULT_COLOR);
            }
        });

        // Feedback visual continuo
        agregarFeedbackVisualCorreo(textField);
    }

    /**
     * Agrega feedback visual a un campo de texto
     */
    private static void agregarFeedbackVisual(JTextField textField, String mensajeError) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            private void actualizarColor() {
                String texto = textField.getText();
                if (texto.isEmpty()) {
                    textField.setBackground(DEFAULT_COLOR);
                    textField.setToolTipText(null);
                } else if (validarCampo(textField)) {
                    textField.setBackground(SUCCESS_COLOR);
                    textField.setToolTipText(null);
                } else {
                    textField.setBackground(ERROR_COLOR);
                    textField.setToolTipText(mensajeError);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarColor();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarColor();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarColor();
            }

            private boolean validarCampo(JTextField field) {
                if (field.getDocument() instanceof PlainDocument) {
                    // Depende del tipo de campo configurado
                    String texto = field.getText();
                    if (field.getToolTipText() != null) {
                        if (field.getToolTipText().contains("letras")) {
                            return validarNombre(texto);
                        } else if (field.getToolTipText().contains("números")) {
                            return validarNumeros(texto);
                        }
                    }
                }
                return true;
            }
        });
    }

    /**
     * Feedback visual específico para correo
     */
    private static void agregarFeedbackVisualCorreo(JTextField textField) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            private void actualizarColor() {
                String correo = textField.getText().trim();
                if (correo.isEmpty()) {
                    textField.setBackground(DEFAULT_COLOR);
                    textField.setToolTipText(null);
                } else if (validarCorreo(correo)) {
                    textField.setBackground(SUCCESS_COLOR);
                    textField.setToolTipText("Correo válido");
                } else {
                    textField.setBackground(ERROR_COLOR);
                    textField.setToolTipText("Debe terminar en: @gmail.com, @hotmail.com o @unicolombo.edu.co");
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarColor();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarColor();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarColor();
            }
        });
    }

    /**
     * Método utilitario para validar todos los campos de un formulario
     */
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

    /**
     * Limpia y resetea los campos de texto
     */
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
