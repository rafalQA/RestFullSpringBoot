package com.possesor.repository;

import com.possesor.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Rafal Piotrowicz on 19.12.2016.
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
