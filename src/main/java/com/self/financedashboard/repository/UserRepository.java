package com.self.financedashboard.repository;

import com.self.financedashboard.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserLogin, Integer> {
    Optional<UserLogin> findByEmailId(String emailId);
}
