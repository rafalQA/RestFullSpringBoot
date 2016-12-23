package com.possesor.repository;

import com.possesor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Rafal Piotrowicz on 19.12.2016.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
