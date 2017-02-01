package com.possessor.controller;

import com.possessor.model.ForeignCurrency;
import com.possessor.model.Property;
import com.possessor.service.PropertyService;
import com.possessor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Rafal Piotrowicz on 23.12.2016.
 */

@RestController
public class PropertyController {

    @Autowired
    private UserService userRepository;
    @Autowired
    private PropertyService propertyService;

    @RequestMapping(method = RequestMethod.PUT, value = "/user/{id}/property")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addPropertyForUser(@PathVariable Long id, @RequestBody Property property) {
       return propertyService.addPropertyForUser(id, property);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}/property")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePropertyWhichHasUser(@PathVariable Long id) {
        propertyService.deletePropertyWithUser(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}/properties")
    @ResponseStatus(HttpStatus.OK)
    public List<Property> getAllPropertiesForUser(@PathVariable Long id) {
        return propertyService.getAllPropertiesForUser(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/properties")
    @ResponseStatus(HttpStatus.OK)
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getValueByForeignCurrency(@PathVariable Long id,
                                                @RequestParam("currency") ForeignCurrency currency) {

       return propertyService.getPropertyValueInForeignCurrency(id,currency);
    }
}
