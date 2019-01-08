package com.baomidou.springwind.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtil {
    private static Session session;
    private static final String from = "615960774@qq.com";
    private static final String password = "vpeblcbmevpjbbad";
    private static final String company="校园租";
    public static Boolean sendMail(String address,String title,String msg) throws MessagingException, UnsupportedEncodingException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.host","smtp.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.timeout","2000");
        Session session = getSession(properties);
        Transport ts = session.getTransport();
        ts.connect(from,password);
        Message message = createMail(session,address,title,msg);
        //Transport.send(message);
        ts.sendMessage(message,message.getAllRecipients());
        ts.close();
        return true;
    }

    private static MimeMessage createMail(Session session,String address,String title,String msg) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setHeader("Content-Type","html/text");
        message.setFrom(new InternetAddress(from,company));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(address));
        message.setSubject(title);
        message.setText(msg);
        return message;
    }

    private static Session getSession(Properties properties){
        if(session == null){
            synchronized (Session.class){
                if(session==null){
                    session = Session.getInstance(properties);
                }
            }
        }
        return session;
    }
}
