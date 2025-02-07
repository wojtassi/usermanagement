package com.example.usermanagement.service;

import com.example.usermanagement.model.User;
import com.example.usermanagement.model.dto.LoginDTO;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.util.PasswordEncryptor;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Controller
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    public LoginService(UserRepository userRepository, PasswordEncryptor passwordEncryptor) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    public boolean checkLogin(LoginDTO loginDTO) {
        String res = passwordEncryptor.encryptPassword(loginDTO.getPassword());
        User user = Optional.ofNullable(userRepository.findOneByEmail(loginDTO.getEmail())).filter(p -> p.getPassword().equals(passwordEncryptor.encryptPassword(loginDTO.getPassword()))).orElse(null);
        if (user != null) {
            userRepository.update(user.getId().toHexString(), Map.of("lastLogin", new Date()));
            return true;
        }
        return false;
    }
}
