package com.example.usermanagement.model.mapper;

import com.example.usermanagement.model.User;
import com.example.usermanagement.model.dto.UserDTO;
import org.bson.types.ObjectId;

public class Mapper {
    public static UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId() == null ? new ObjectId().toHexString() : user.getId().toHexString())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .lastLogin(user.getLastLogin())
                .build();
    }

    public static User toUser(UserDTO userDTO) {
        ObjectId _id = userDTO.getId() == null ? new ObjectId() : new ObjectId(userDTO.getId());
        return User.builder()
                .id(_id)
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
}
