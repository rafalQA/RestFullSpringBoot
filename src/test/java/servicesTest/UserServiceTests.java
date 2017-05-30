package servicesTest;

import com.possessor.model.Account;
import com.possessor.model.User;
import com.possessor.repository.UserRepository;
import com.possessor.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by rpiotrowicz on 2017-05-16.
 */


@RunWith(MockitoJUnitRunner.class)
public class
UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService = new UserService();

    private User beginUser;
    private User endUser;

    @Before
    public void setUp() {
        beginUser = new User();
        beginUser.setEmail("m.agent.rafal@gmail.com");
        Account beginAccount = new Account();
        beginAccount.setUsername("Franek");
        beginAccount.setPassword("park");
        beginUser.setAccount(beginAccount);

        endUser = new User();
        endUser.setUserId(12345L);
    }

    @Test
    public void testAddUser() throws MessagingException, IOException {

        when(userRepository.save(any(User.class))).thenReturn(endUser);

        long actualId = userService.addUser(beginUser);

        Assert.assertEquals(12345L, actualId);
    }
}
