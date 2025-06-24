package com.syntaxerror.biblioteca.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class CorreoUtil {

    // Configuración SMTP del remitente
    private static final String HOST = "smtp.gmail.com";  
    private static final String PORT = "587";
    private static final String USERNAME = "myholylib.system@gmail.com"; //CUENTA REMITENTE
    private static final String PASSWORD = "zneq wrif uxzh vdij";        // Clave de aplicación, NO tu clave normal

    /**
     * Envía un correo de texto plano.
     * @param destino correo del destinatario
     * @param asunto asunto del mensaje
     * @param contenido cuerpo del mensaje
     * @throws MessagingException si hay error al enviar
     */
    public static void enviarCorreo(String destino, String asunto, String contenido) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS recomendado
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
        message.setSubject(asunto);
        message.setText(contenido); // Para HTML: setContent(contenido, "text/html")

        Transport.send(message);
    }
}
