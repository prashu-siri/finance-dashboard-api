package com.self.financedashboard.service;

import com.self.financedashboard.model.ApiResponse;
import com.self.financedashboard.model.UserLogin;
import com.self.financedashboard.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApiResponse signUp(UserLogin userLogin) {
        ApiResponse response = new ApiResponse();
        UserLogin existingUser = checkIfEmailIdExists(userLogin.getEmailId());
        if(existingUser != null) {
            response.setMessage("Email Id already exist");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(null);
        } else {
            userRepository.save(userLogin);
            Map<String, Object> result = new HashMap<>();
            result.put("name", userLogin.getName());
            result.put("item", UUID.randomUUID().toString());

            response.setData(result);
            response.setMessage("User registered successfully");
            response.setStatus(HttpStatus.OK);
        }

        return response;
    }

    private UserLogin checkIfEmailIdExists(String emailId) {
        Optional<UserLogin> user = userRepository.findByEmailId(emailId);
        return user.orElse(null);
    }

    public ApiResponse signIn(UserLogin userLogin) {
        ApiResponse response = new ApiResponse();
        UserLogin existingUser = checkIfEmailIdExists(userLogin.getEmailId());

        if (existingUser != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("name", existingUser.getName());
            result.put("item", UUID.randomUUID().toString());

            response.setData(result);
            response.setMessage("User registered successfully");
            response.setStatus(HttpStatus.OK);
        } else {
            response.setMessage("Email Id does not exist");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(null);
        }

        return response;
    }
}
