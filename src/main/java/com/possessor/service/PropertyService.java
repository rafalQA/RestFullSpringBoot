package com.possessor.service;

import com.possessor.exception.Message;
import com.possessor.exception.ValidationException;
import com.possessor.model.Currency;
import com.possessor.model.ForeignCurrency;
import com.possessor.model.Property;
import com.possessor.model.User;
import com.possessor.repository.PropertyRepository;
import com.possessor.repository.UserRepository;
import com.utility.LocaleCurrency;
import com.utility.MathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Rafal Piotrowicz on 31.12.2016.
 */

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LocaleCurrency localeCurrency;

    public Long addPropertyForUser(Long userId, Property property) {
        validateAdd(property);
        userValidate(userId);
        validateForDataBase(property);

        User user = userRepository.findOne(userId);
        property.setUser(user);

        return propertyRepository.save(property).getPropertyId();
    }

    public BigDecimal getPropertyValueInForeignCurrency(Long id, String foreignCurrency, Locale locale) {

        validForeignCurrency(foreignCurrency);

        Double rate = getForeignCurrencyRate(foreignCurrency, locale);

        BigDecimal baseValue = getPropertyValue(id);

        return MathHelper.getRounded(baseValue.multiply(new BigDecimal(rate)));
    }

    public void deletePropertyOwnedByUser(Long id) {
        Property property = propertyRepository.findAll().stream()
                .filter(x -> x.getUser().getUserId().equals(id)).findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("No suchProperty with %d", id)));

        propertyRepository.delete(property.getPropertyId());
        Assert.isTrue(!propertyRepository.exists(property.getPropertyId()),
                String.format("Property %s It was not deleted", property.getPropertyId()));
    }

    public List<Property> getAllPropertiesForUser(Long id) {
        return propertyRepository.findAll().stream()
                .filter(x -> x.getUser().getUserId().equals(id)).collect(Collectors.toList());
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    private BigDecimal getPropertyValue(Long id) {
        Property property = getPropertyByID(id);

        return property.getValue();
    }

    private Double getForeignCurrencyRate(String foreignCurrency, Locale locale) {
        UriComponents uriComponents = buildAndGetUriComponents(locale);
        Currency currency = getForeignCurrency(uriComponents);

        return getRate(foreignCurrency, currency);
    }

    private Double getRate(String foreignCurrency, Currency currency) {

        return (Double) currency.getRates().getAdditionalProperties().entrySet().stream()
                .filter(x -> x.getKey().equals(foreignCurrency)).findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("In Api there is no currency: %s", foreignCurrency)))
                .getValue();
    }

    private Property getPropertyByID(Long id) {
        return propertyRepository.findAll().stream()
                .filter(x -> x.getPropertyId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no property with Id: " + id));
    }

    private Currency getForeignCurrency(UriComponents uriComponents) {
        return restTemplate.getForObject(uriComponents.toUriString(), Currency.class);
    }

    private UriComponents buildAndGetUriComponents(Locale locale) {
        return UriComponentsBuilder.newInstance().
                scheme("http").host("api.fixer.io").path("latest")
                .queryParam("base", localeCurrency.getLocaleCurrencyCode(locale)).build();
    }

    private void validateAdd(Property property) {
        List<IllegalArgumentException> exceptions = new LinkedList<>();

        if (property.getPropertyId() != null) {
            exceptions.add(new IllegalArgumentException("id " + Message.NOT_ALLOWED));
        }

        if (property.getName() == null) {
            exceptions.add(new IllegalArgumentException("name " + Message.MAY_NOT_BE_NULL));
        }

        if (property.getName() != null && property.getName().equals("")) {
            exceptions.add(new IllegalArgumentException("name " + Message.MAY_NOT_BE_EMPTY));
        }

        if (property.getUser() != null) {
            exceptions.add(new IllegalArgumentException("user" + Message.NOT_ALLOWED));
        }

        if (property.getValue() == null) {
            exceptions.add(new IllegalArgumentException("value " + Message.MAY_NOT_BE_NULL));
        }

        if (property.getValue() != null && property.getValue().equals(BigDecimal.ZERO)) {
            exceptions.add(new IllegalArgumentException("value " + Message.MAY_NOT_BE_ZERO));
        }

        if (!exceptions.isEmpty()) {
            throw new ValidationException(exceptions);
        }
    }

    private void validateForDataBase(Property property) {
        if (propertyRepository.findByNameAndValueAndUser(
                property.getName(), property.getValue(), property.getUser()) != null) {
            throw new IllegalArgumentException("product " + Message.ALREADY_EXIST);
        }
    }

    private void userValidate(Long id) {
        if (userRepository.findOne(id) == null) {
            throw new IllegalArgumentException("user with id :" + id + "" + Message.NOT_FOUND);
        }
    }

    private void validForeignCurrency(String foreignCurrency) {
        boolean is = Arrays.stream(ForeignCurrency.values())
                .anyMatch(f -> f.name().equals(foreignCurrency));

        if (!is) {
            throw new IllegalArgumentException("allowed currency code is: " + Arrays.stream(ForeignCurrency.values())
                    .map(ForeignCurrency::name).collect(Collectors.joining("; ")));
        }
    }
}
