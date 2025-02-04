package com.example.usermanagement.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserDTO {
    public String id;
    public String name;
    public String email;
    public String password;
    public Date lastLogin;
}
