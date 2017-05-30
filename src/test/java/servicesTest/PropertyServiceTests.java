package servicesTest;

import com.possessor.model.Account;
import com.possessor.model.Property;
import com.possessor.model.User;
import com.possessor.repository.PropertyRepository;
import com.possessor.repository.UserRepository;
import com.possessor.service.PropertyService;
import com.utility.LocaleCurrency;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Created by rpiotrowicz on 2017-05-26.
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertyServiceTests {

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private UserRepository userRepository;
    @Spy
    RestTemplate restTemplate;
    @Spy
    LocaleCurrency localeCurrency;


    @InjectMocks
    private PropertyService propertyService = new PropertyService();

    private User user;
    private Property property;
    private String currency = "en";


    @Before
    public void setUp(){
        user = new User();
        user.setUserId(34343L);
        user.setEmail("m.agent.rafal@gmail.com");
        Account beginAccount = new Account();
        beginAccount.setUsername("Franek");
        beginAccount.setPassword("park");
        user.setAccount(beginAccount);

        property = new Property();
        property.setPropertyId(3L);
        property.setName("TestCup");
        property.setValue(new BigDecimal(2345.78));
    }

    @Test
    public void addPropertyForUserTest(){
        when(userRepository.findOne(anyLong())).thenReturn(user);
        when(propertyRepository.save(property)).thenReturn(property);

        long actualPropertyId = propertyService.addPropertyForUser(34343L, property);

        Assert.assertTrue(property.getPropertyId() == actualPropertyId);
        Assert.assertEquals(property.getUser(), user);
    }

    @Test
    public void getPropertyValueInForeignCurrency(){

        List<Property> properties = new ArrayList<>();
        properties.add(property);

        when(propertyRepository.findAll()).thenReturn(properties);

        BigDecimal actualValue = propertyService.getPropertyValueInForeignCurrency(property.getPropertyId(),"GBP",
                new Locale("pl", "PL"));

        BigDecimal expectedValue = new BigDecimal("488.79");

        Assert.assertEquals(expectedValue, actualValue);
    }
}
