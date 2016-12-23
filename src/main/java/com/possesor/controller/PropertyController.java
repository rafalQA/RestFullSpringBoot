package com.possesor.controller;

import com.possesor.model.Property;
import com.possesor.model.User;
import com.possesor.repository.PropertyRepository;
import com.possesor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Rafal Piotrowicz on 23.12.2016.
 */

@RestController
public class PropertyController {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyController(UserRepository userRepository, PropertyRepository propertyRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/user/{id}/property")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPropertyForUser(@PathVariable Long id, @RequestBody Property property) {
        User user = userRepository.findOne(id);
        user.getProperties().add(property);
        userRepository.save(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}/property")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePropertyWhichHasUser(@PathVariable Long id) {
       Optional<Property> property = propertyRepository.findAll().stream()
                .filter(x -> x.getUser().getId().equals(id)).findFirst();

        if(property.isPresent()){
            propertyRepository.delete(property.get().getId());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}/properties")
    @ResponseStatus(HttpStatus.OK)
    public List<Property> getAllPropertiesForUser(@PathVariable Long id) {
        return propertyRepository.findAll().stream()
                .filter(x -> x.getUser().getId().equals(id)).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/properties")
    @ResponseStatus(HttpStatus.OK)
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
}
