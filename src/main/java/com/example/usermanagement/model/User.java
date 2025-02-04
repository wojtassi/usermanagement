package com.example.usermanagement.model;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.Date;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public ObjectId id;
    public String name;
    public String email;
    public String password;
    public Date lastLogin;

}
