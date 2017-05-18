package servicesTest;

import com.possessor.dto.DtoUser;
import com.possessor.service.CredentialsMailSender;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.subethamail.wiser.Wiser;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * Created by rpiotrowicz on 2017-05-17.
 */

@RunWith(MockitoJUnitRunner.class)
public class CredentialsMailSenderTests {

    @Spy
    private JavaMailSenderImpl javaMailSender;

    @InjectMocks
    private CredentialsMailSender credentialsMailSender = new CredentialsMailSender();

    private DtoUser user;
    private Wiser wiser;

    @Before
    public void setUp() {
        javaMailSender.setPort(50);

        wiser = new Wiser();
        wiser.setPort(50);
        wiser.start();

        user = new DtoUser();
        user.setEmail("m.agent.rafal@gmail.com");
        user.setUsername("Rafal");
        user.setPassword("Pogoda");
    }

    @Test
    public void sendCredentialsTest() throws IOException {
        credentialsMailSender.sendCredentials(user);

        MimeMessage receivedMessage = null;
        String actualSendMailContent = "";

        try {
            receivedMessage = wiser.getMessages().get(0).getMimeMessage();
            actualSendMailContent = receivedMessage.getContent().toString();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        Assert.assertTrue("Fake smtp server didn't received message", receivedMessage != null);

        Assert.assertTrue("Mail content doesn't contain user password",
                actualSendMailContent.contains("Pogoda"));

        Assert.assertTrue("Mail content doesn't contain user name",
                actualSendMailContent.contains("Rafal"));

        try {
            Assert.assertEquals("Mail doesn't send to correct recipient",
                    receivedMessage.getRecipients(Message.RecipientType.TO)[0].toString(), "m.agent.rafal@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @After
    public void stopWiser() {
        wiser.stop();
    }
}
