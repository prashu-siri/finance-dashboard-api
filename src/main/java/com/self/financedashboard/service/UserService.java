package com.self.financedashboard.service;

import com.self.financedashboard.model.UserLogin;
import com.self.financedashboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String signUp(UserLogin userLogin) {
        boolean isExistingUser = checkIfEmailIdExists(userLogin.getEmailId());
        if(isExistingUser) {
            return "Email Id already exist";
        } else {
            userRepository.save(userLogin);
        }

        return "Registration successful. Sign in using the email Id";
    }

    private boolean checkIfEmailIdExists(String emailId) {
        Optional<UserLogin> user = userRepository.findByEmailId(emailId);
        return user.isPresent();
    }

    public String signIn(UserLogin userLogin) {
        boolean isExistingUser = checkIfEmailIdExists(userLogin.getEmailId());

        if (isExistingUser) {
            return UUID.randomUUID().toString();
        } else {
            return "Email Id does not exist";
        }
    }
}
