package com.example.usermanagement.controller;

import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    public void testLoginSuccess() throws Exception {
        User user = User.builder().id(new ObjectId("67a21b808cf1401b9b1af3b9")).name("somename").password("password1").email("email@email.com").lastLogin(new Date(1738684697117L)).build();
        when(userRepository.findOneByEmail("email@email.com")).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/login").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "    \"email\": \"email@email.com\",\n" +
                                "    \"password\": \"password1\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\": \"OK\", \"message\": \"Success\"}"));
        verify(userRepository).findOneByEmail(eq("email@email.com"));
        verify(userRepository).update(eq("67a21b808cf1401b9b1af3b9"), anyMap() );
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testLoginFailureWrongEmail() throws Exception {
        when(userRepository.findOneByEmail("wrong@email.com")).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/login").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "    \"email\": \"email@email.com\",\n" +
                                "    \"password\": \"password1\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\": \"FORBIDDEN\", \"message\": \"Could not validate email or password\"}"));
        verify(userRepository).findOneByEmail(eq("email@email.com"));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testLoginFailureWrongPassword() throws Exception {
        User user = User.builder().id(new ObjectId("67a21b808cf1401b9b1af3b9")).name("somename").password("password1").email("email@email.com").lastLogin(new Date(1738684697117L)).build();
        when(userRepository.findOneByEmail("email@email.com")).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/login").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "    \"email\": \"email@email.com\",\n" +
                                "    \"password\": \"wrongPassword\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\": \"FORBIDDEN\", \"message\": \"Could not validate email or password\"}"));
        verify(userRepository).findOneByEmail(eq("email@email.com"));
        verifyNoMoreInteractions(userRepository);
    }

}
