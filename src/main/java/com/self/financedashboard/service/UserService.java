package com.self.financedashboard.service;

import com.self.financedashboard.model.ApiResponse;
import com.self.financedashboard.model.UserLogin;
import com.self.financedashboard.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ApiResponse signUp(UserLogin userLogin) {
        ApiResponse response = new ApiResponse();
        UserLogin existingUser = checkIfEmailIdExists(userLogin.getEmailId());
        if(existingUser != null) {
            response.setMessage("Email Id already exist");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(null);
        } else {
            userLogin.setPassword(bCryptPasswordEncoder.encode(userLogin.getPassword()));
            UserLogin user = userRepository.save(userLogin);
            Map<String, Object> result = getDetails(user);

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
            Map<String, Object> result = getDetails(existingUser);

            if(bCryptPasswordEncoder.matches(userLogin.getPassword(), existingUser.getPassword())) {
                response.setData(result);
                response.setMessage("User authenticated");
                response.setStatus(HttpStatus.OK);
            } else {
                response.setData(null);
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                response.setMessage("Incorrect Password");
            }

        } else {
            response.setMessage("Email Id does not exist");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(null);
        }

        return response;
    }

    private Map<String, Object> getDetails(UserLogin userLogin) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", userLogin.getId());
        result.put("name", userLogin.getName());
        result.put("item", UUID.randomUUID().toString());

        return  result;
    }
}
