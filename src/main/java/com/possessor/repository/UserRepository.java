package com.possessor.repository;

import com.possessor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Rafal Piotrowicz on 19.12.2016.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByAccountUsernameAndAccountPassword(String username, String password);
    Optional<User> findByAccountUsername(String username);
    boolean existsByEmail(String email);
}
