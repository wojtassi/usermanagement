package com.example.usermanagement.config;

import com.example.usermanagement.util.PasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserManagementConfiguration {

    @Bean
    public PasswordEncryptor passwordEncryptor() {
        return new PasswordEncryptor();
    }
}
