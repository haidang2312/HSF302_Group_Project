package org.example.hsf302_group1.service;

import org.example.hsf302_group1.entity.UserAccount;
import org.example.hsf302_group1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserAccount> login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }
}