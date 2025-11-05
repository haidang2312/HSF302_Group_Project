package org.example.hsf302_group1.repository;

import org.example.hsf302_group1.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Integer> {
    Optional<UserAccount> findByUsername(String username);
}