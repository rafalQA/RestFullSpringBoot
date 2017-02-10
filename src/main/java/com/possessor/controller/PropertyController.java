package com.possessor.controller;

import com.possessor.model.Property;
import com.possessor.service.PropertyService;
import com.possessor.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rafal Piotrowicz on 23.12.2016.
 */

@RestController
public class PropertyController {

    @Autowired
    private UserService userRepository;
    @Autowired
    private PropertyService propertyService;

    @ApiOperation(value = "addPropertyForUser", nickname = "addPropertyForUser")
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{id}/properties")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addPropertyForUser(@PathVariable Long id, @RequestBody Property property) {

       return propertyService.addPropertyForUser(id, property);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/properties/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePropertyOwnedByUser(@PathVariable Long id) {
        propertyService.deletePropertyWithUser(id);
    }

    @ApiOperation(value = "getAllPropertiesForUser", nickname = "getAllPropertiesForUser")
    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}/properties")
    @ResponseStatus(HttpStatus.OK)
    public List<Property> getAllPropertiesForUser(@PathVariable Long id) {
        return propertyService.getAllPropertiesForUser(id);
    }

    @ApiOperation(value = "getAllProperties", nickname = "getAllProperties")
    @RequestMapping(method = RequestMethod.GET, value = "/properties")
    @ResponseStatus(HttpStatus.OK)
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @ApiOperation(value = "getValueByForeignCurrency", nickname = "getValueByForeignCurrency")
    @RequestMapping(method = RequestMethod.GET, value = "/properties/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getValueByForeignCurrency(@PathVariable Long id,
                                                @RequestParam("currency") String currency, Locale locale) {
       return propertyService.getPropertyValueInForeignCurrency(id,currency, locale);
    }
}
