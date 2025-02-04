package com.example.usermanagement.service;

import com.example.usermanagement.model.User;
import com.example.usermanagement.model.dto.LoginDTO;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Controller
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkLogin(LoginDTO loginDTO) {

        User user = Optional.ofNullable(userRepository.findOneByEmail(loginDTO.getEmail())).filter(p -> p.getPassword().equals(loginDTO.getPassword())).orElse(null);
        if (user != null) {
            userRepository.update(user.getId().toHexString(), Map.of("lastLogin", new Date()));
            return true;
        }
        return false;
    }
}
