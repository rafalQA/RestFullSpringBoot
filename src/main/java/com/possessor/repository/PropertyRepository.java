package com.possessor.repository;

import com.possessor.model.Property;
import com.possessor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by Rafal Piotrowicz on 23.12.2016.
 */

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Property findByNameAndValueAndUser(String name, BigDecimal value, User user);
}

