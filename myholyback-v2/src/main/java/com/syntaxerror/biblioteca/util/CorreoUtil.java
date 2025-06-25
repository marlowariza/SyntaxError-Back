package com.syntaxerror.biblioteca.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class CorreoUtil {

    private static final PropertiesLoader mailProps = new PropertiesLoader("mail.properties");

    private static final String HOST = mailProps.get("mail.host");
    private static final String PORT = mailProps.get("mail.port");
    private static final String USERNAME = mailProps.get("mail.username");
    private static final String PASSWORD = mailProps.get("mail.password");
    private static final String FROM = mailProps.get("mail.from");

    /**
     * Envía un correo de texto plano o HTML.
     */
    public static void enviarCorreo(String destino, String asunto, String contenido) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*"); // para pruebas: acepta cualquier certificado SSL
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
        message.setSubject(asunto);
        message.setContent(contenido, "text/html; charset=utf-8");

        Transport.send(message);
    }

    /**
     * Método helper: envía correo de forma ASÍNCRONA.
     */
    //Por si demora mucho el solicitar, en soap no se interrumpe el proceso al parecer. En las pruebs en los test sí.
    public static void enviarCorreoAsync(String destino, String asunto, String contenido) {
        CompletableFuture.runAsync(() -> {
            try {
                enviarCorreo(destino, asunto, contenido);
            } catch (Exception e) {
                System.err.println("⚠️ No se pudo enviar el correo: " + e.getMessage());
            }
        });
    }
}
