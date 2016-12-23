package com.possesor.repository;

import com.possesor.model.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Rafal Piotrowicz on 23.12.2016.
 */

@Repository
public interface PropertyRepository extends CrudRepository<Property, Long>{

    @Override
    List<Property> findAll();
}
