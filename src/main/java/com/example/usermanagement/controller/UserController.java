package com.example.usermanagement.controller;

import com.example.usermanagement.model.dto.UserDTO;
import com.example.usermanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    List<UserDTO> all() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    UserDTO one(@PathVariable String id) {
        return userService.findOne(id);
    }

    @PostMapping("/users")
    UserDTO newUser(@RequestBody UserDTO newUser) {
        return userService.save(newUser);
    }

    @PutMapping("/users/{id}")
    UserDTO updateUser(@RequestBody UserDTO updateUser, @PathVariable String id) {
        if (!StringUtils.hasText(updateUser.getId())) {
            updateUser.setId(id);
        } else if (!updateUser.getId().equals(id)) {
            throw new RuntimeException("Object Id cannot be different then path id");
        }
        return userService.update(updateUser);
    }

    @DeleteMapping("/users/{id}")
    Long delete(@PathVariable String id) {
        return userService.delete(id);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
