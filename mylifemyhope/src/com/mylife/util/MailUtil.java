package com.mylife.util;

import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;


public class MailUtil {
	static Authenticator auth = new Authenticator() {
		 
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("13898624331@163.com", "13898624331klaus");
        }
 
    };
 
    public void runn(String str) {
 
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.from", "13898624331@163.com");
        Session session = Session.getInstance(props, auth);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom();
            msg.setRecipients(Message.RecipientType.TO, "13898624331@163.com");
            msg.setSubject("用户的反馈建议");
            msg.setSentDate(new Date());
            msg.setText(str);
            Transport.send(msg);
        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
        }
 
    }
}