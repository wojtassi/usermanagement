package com.example.usermanagement.service;

import com.example.usermanagement.model.User;
import com.example.usermanagement.model.dto.UserDTO;
import com.example.usermanagement.model.mapper.Mapper;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.util.PasswordEncryptor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    public UserService(UserRepository userRepository, PasswordEncryptor passwordEncryptor) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    public UserDTO save(UserDTO userDTO) {
        User user = Mapper.toUser(userDTO);
        user.setPassword(passwordEncryptor.encryptPassword(user.getPassword()));
        user = userRepository.save(user);
        user.setPassword("*******");
        return Mapper.toDto(user);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(Mapper::toDto).toList();
    }

    public UserDTO findOne(String id) {
        User user = userRepository.findOne(id);
        user.setPassword("*******");
        return Mapper.toDto(user);
    }

    public long delete(String id) {
        return userRepository.delete(id);
    }

    public UserDTO update(UserDTO userDTO) {
        User user = Mapper.toUser(userDTO);
        user.setPassword(passwordEncryptor.encryptPassword(user.getPassword()));
        user = userRepository.update(user);
        user.setPassword("*******");
        return Mapper.toDto(user);
    }
}
