package com.possessor.service;

import com.possessor.model.User;
import com.possessor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Rafal Piotrowicz on 31.12.2016.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Long addUser(User user) {

        String unencrypted = user.getAccount().getPassword();

        encodeUserAccountPassword(user, unencrypted);

        return userRepository.save(user).getUserId();
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public boolean userExist(long id) {
        return userRepository.exists(id);
    }

    private void encodeUserAccountPassword(User user, String unencrypted) {
        user.getAccount().setPassword(passwordEncoder.encode(unencrypted));
    }
}
