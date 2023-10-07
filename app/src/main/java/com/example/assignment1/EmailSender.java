package com.example.assignment1;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    private static final String USERNAME = "yiran.zhao997@qq.com";
    private static final String PASSWORD = "shokvepwqoevdgah";
    private static final String HOST = "smtp.qq.com";
    private static final String PORT = "465";
    private static final String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";

    private static Message createEmailMessage(Session session, String fromText, String toText, String ccText, String bccText, String subjectText, String messageText) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromText));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toText));
        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccText));
        message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccText));
        message.setSubject(subjectText);
        message.setContent(messageText, "text/plain;charset=gbk");
        return message;
    }

    private static Session authenticateSession(Properties props) {
        return Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }

    private static Properties setMailProperties() {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.socketFactory.class", SOCKET_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", PORT);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        return props;
    }
    public static void sendEmail(String fromText, String toText, String ccText, String bccText, String subjectText, String messageText){
        try {
            Properties props = setMailProperties();
            Session session = authenticateSession(props);

            Message message = createEmailMessage(session, fromText, toText, ccText, bccText, subjectText, messageText);

            Transport transport = session.getTransport();
            transport.connect(HOST, USERNAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            System.out.println("There was an error while trying to send the email.");
            System.out.println("Error message: " + ex.getMessage());
        }
    }
}
