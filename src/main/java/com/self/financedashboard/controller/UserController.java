package com.self.financedashboard.controller;

import com.self.financedashboard.model.ApiResponse;
import com.self.financedashboard.model.ErrorResponse;
import com.self.financedashboard.model.UserLogin;
import com.self.financedashboard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
@Slf4j
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping(value = "signUp")
    public ResponseEntity<?> signUp(@RequestBody UserLogin userLogin) {
        try {
            ApiResponse response = userService.signUp(userLogin);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("Error while sign up", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @PostMapping(value = "signIn")
    public ResponseEntity<?> signIn(@RequestBody UserLogin userLogin) {
        try {
            ApiResponse response = userService.signIn(userLogin);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("Error while sign in. Try after sometime", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
