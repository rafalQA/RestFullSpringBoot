package com.possessor.service;

import com.possessor.dto.DtoUser;
import com.utility.FreeMarkerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.util.concurrent.Future;

/**
 * Created by rpiotrowicz on 2017-02-13.
 */
@Service
public class CredentialsMailSender {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Async
    public Future<Void> sendCredentials(DtoUser user) {
        this.mailSender.send(mimeMessage -> {
            mimeMessage.setSubject("Yours Credentials");
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            mimeMessage.setContent(FreeMarkerHelper.getMergedTemplateWithUserRoot(user, "credentialMail.ftl"),
                    "text/html; charset=utf-8");
        });

        return null;
    }
}

