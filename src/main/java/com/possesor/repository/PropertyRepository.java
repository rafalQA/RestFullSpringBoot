package com.possesor.repository;

import com.possesor.model.Property;
import com.possesor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Rafal Piotrowicz on 23.12.2016.
 */

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Property findPropertyIgnoreCaseAndValueIgnoreCase(String name, BigDecimal value, User user);
}

