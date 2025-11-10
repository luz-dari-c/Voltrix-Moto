package Utilidades;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Email {
    private static final String REMITENTE = "imsharlok@gmail.com";
    private static final String CONTRASENA = "njhxtexcgookhslv";

    public static boolean enviarCodigo(String destinatario, String codigo) {
        // Validar parámetros
        if (destinatario == null || destinatario.trim().isEmpty() || codigo == null || codigo.trim().isEmpty()) {
            System.err.println("Error: Destinatario o código vacío");
            return false;
        }

        // Configuración de propiedades
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.connectiontimeout", "10000"); // 10 segundos timeout
        props.put("mail.smtp.timeout", "10000"); // 10 segundos timeout

        try {
            // Crear sesión
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(REMITENTE, CONTRASENA);
                }
            });

            // Crear mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMITENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Código de Verificación - VOLTRIX Moto");
            
            String contenido = crearContenidoCorreo(codigo);
            message.setText(contenido);

            // Enviar mensaje
            Transport.send(message);
            System.out.println("✅ Correo enviado exitosamente a: " + destinatario);
            return true;
            
        } catch (AuthenticationFailedException e) {
            System.err.println("❌ Error de autenticación: " + e.getMessage());
            return false;
        } catch (MessagingException e) {
            System.err.println("❌ Error al enviar correo: " + e.getMessage());
            // Mostrar código en consola como fallback
            mostrarCodigoEnConsola(destinatario, codigo);
            return false;
        } catch (Exception e) {
            System.err.println("❌ Error inesperado: " + e.getMessage());
            mostrarCodigoEnConsola(destinatario, codigo);
            return false;
        }
    }
    
    private static String crearContenidoCorreo(String codigo) {
        return "Estimado usuario,\n\n" +
               "Su código de verificación para recuperar contraseña es:\n\n" +
               "🔒 " + codigo + "\n\n" +
               "Este código expirará en 10 minutos.\n\n" +
               "Si no solicitó este código, ignore este mensaje.\n\n" +
               "Atentamente,\nEquipo VOLTRIX Moto";
    }
    
    private static void mostrarCodigoEnConsola(String destinatario, String codigo) {
        System.out.println("\n=== 🚨 CORREO NO ENVIADO - MOSTRANDO CÓDIGO EN CONSOLA ===");
        System.out.println("Para: " + destinatario);
        System.out.println("Código de verificación: " + codigo);
        System.out.println("=== FIN CÓDIGO ===\n");
    }
}