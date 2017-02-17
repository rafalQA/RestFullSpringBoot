package com.possessor.service;

import com.possessor.exception.UserValidator;
import com.possessor.mail.CredentialsMailSender;
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
    private CredentialsMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserValidator userValidator;

    public Long addUser(User user) {
        validUser(user);

        String unencrypted = user.getAccount().getPassword();

        encodeUserAccountPassword(user, unencrypted);

        Long userId = userRepository.save(user).getUserId();

        sendMailToUserWithDecodeUnencrypted(user, unencrypted, userId);

        return userId;
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    private void encodeUserAccountPassword(User user, String unencrypted) {
        user.getAccount().setPassword(
                passwordEncoder.encode(unencrypted));
    }

    private void sendMailToUserWithDecodeUnencrypted(User user, String unencrypted, Long userId) {
        if (userId > 0) {
            user.getAccount().setPassword(unencrypted);
            mailSender.sendCredentials(user);
        }
    }

    private void validUser(User user) {
        userValidator.validateAdd(user);
        userValidator.validForDataBase(user);
    }
}
