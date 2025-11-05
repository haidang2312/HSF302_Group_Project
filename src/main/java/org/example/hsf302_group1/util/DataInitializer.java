package org.example.hsf302_group1.util;

import jakarta.annotation.PostConstruct;
import org.example.hsf302_group1.entity.UserAccount;
import org.example.hsf302_group1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;


    @PostConstruct
    public void initData() {
        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(
                    new UserAccount(null, "manager1", "123", 1),
                    new UserAccount(null, "staff1", "123", 2),
                    new UserAccount(null, "staff2", "123", 2),
                    new UserAccount(null, "guest1", "123", 3)
            ));
        }
    }
}
