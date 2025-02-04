package com.example.usermanagement.service;

import com.example.usermanagement.model.dto.UserDTO;
import com.example.usermanagement.model.mapper.Mapper;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO save(UserDTO userDTO) {
        return Mapper.toDto(userRepository.save(Mapper.toUser(userDTO)));
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(Mapper::toDto).toList();
    }

    public UserDTO findOne(String id) {
        return Mapper.toDto(userRepository.findOne(id));
    }

    public long delete(String id) {
        return userRepository.delete(id);
    }

    public UserDTO update(UserDTO userDTO) {
        return Mapper.toDto(userRepository.update(Mapper.toUser(userDTO)));
    }
}
