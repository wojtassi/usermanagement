package com.example.usermanagement.controller;

import com.example.usermanagement.model.LoginResponse;
import com.example.usermanagement.model.dto.LoginDTO;
import com.example.usermanagement.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AuthenticationController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private final LoginService loginService;

    public AuthenticationController(LoginService loginService) {
        this.loginService = loginService;
    }
    @PostMapping("/login")
    @Operation(summary = "Verify email and password match user.")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
       if (!loginService.checkLogin(loginDTO)) {
           LoginResponse resp = LoginResponse.builder()
                   .status(HttpStatus.FORBIDDEN)
                   .message("Could not validate email or password")
                   .build();

           return new ResponseEntity<>(resp, HttpStatus.FORBIDDEN);
       }
        LoginResponse resp = LoginResponse.builder()
                .status(HttpStatus.OK)
                .message("Success")
                .build();
       return ResponseEntity.ok(resp);
    }
}
