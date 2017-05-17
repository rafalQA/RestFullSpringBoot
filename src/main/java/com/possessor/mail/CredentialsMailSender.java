package com.possessor.mail;

import com.possessor.model.User;
import com.utility.FreeMarkerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

/**
 * Created by rpiotrowicz on 2017-02-13.
 */
@Service
public class CredentialsMailSender {

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendCredentials(User user) {
        this.mailSender.send(mimeMessage -> {
            mimeMessage.setSubject("Yours Credentials");
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            mimeMessage.setContent(FreeMarkerHelper.getMergedTemplateWithUserRoot(user, "credentialMail.ftl"),
                    "text/html; charset=utf-8");
        });
    }
}

