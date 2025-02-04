package com.example.usermanagement.controller;

import com.example.usermanagement.model.User;
import com.example.usermanagement.model.dto.UserDTO;
import com.example.usermanagement.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        mapper = springMvcJacksonConverter.getObjectMapper();
    }

    @Test
    public void testGetAll() throws Exception {
        User user1 = User.builder().id(new ObjectId("67a21b808cf1401b9b1af3b9")).name("user1").password("password1").email("email1@email.com").lastLogin(new Date(1738684697117L)).build();
        User user2 = User.builder().id(new ObjectId("67a21b808cf1401b9b1aabcd")).name("user2").password("password2").email("email2@email.com").lastLogin(new Date(1738684697117L)).build();
        User user3 = User.builder().id(new ObjectId("67a21b808cf1401b9b1abfab")).name("user3").password("password3").email("email3@email.com").build();
        UserDTO userDTO1 = UserDTO.builder().id("67a21b808cf1401b9b1af3b9").name("user1").password("password1").email("email1@email.com").lastLogin(new Date(1738684697117L)).build();
        UserDTO userDTO2 = UserDTO.builder().id("67a21b808cf1401b9b1aabcd").name("user2").password("password2").email("email2@email.com").lastLogin(new Date(1738684697117L)).build();
        UserDTO userDTO3 = UserDTO.builder().id("67a21b808cf1401b9b1abfab").name("user3").password("password3").email("email3@email.com").build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3));
        List<UserDTO> resultList = List.of(userDTO1, userDTO2, userDTO3);
        String resultJSON = mapper.writeValueAsString(resultList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(resultJSON));
        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetOneUser() throws Exception {
        User user1 = User.builder().id(new ObjectId("67a21b808cf1401b9b1af3b9")).name("user1").password("password1").email("email1@email.com").lastLogin(new Date(1738684697117L)).build();
        UserDTO userDTO1 = UserDTO.builder().id("67a21b808cf1401b9b1af3b9").name("user1").password("password1").email("email1@email.com").lastLogin(new Date(1738684697117L)).build();

        when(userRepository.findOne(anyString())).thenReturn(user1);
        String resultJSON = mapper.writeValueAsString(userDTO1);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/67a21b808cf1401b9b1af3b9")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(resultJSON));
        verify(userRepository).findOne(eq("67a21b808cf1401b9b1af3b9"));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testCreateUser() throws Exception {
        User user1 = User.builder().id(new ObjectId("67a21b808cf1401b9b1af3b9")).name("user1").password("password1").email("email1@email.com").build();
        UserDTO userDTO1 = UserDTO.builder().id("67a21b808cf1401b9b1af3b9").name("user1").password("password1").email("email1@email.com").build();

        when(userRepository.save(any(User.class))).thenReturn(user1);
        String userJSON = mapper.writeValueAsString(userDTO1);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users").contentType(MediaType.APPLICATION_JSON).content(userJSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(userJSON));
        verify(userRepository).save(eq(user1));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user1 = User.builder().id(new ObjectId("67a21b808cf1401b9b1af3b9")).name("user1").password("password1").email("email1@email.com").build();
        UserDTO userDTO1 = UserDTO.builder().id("67a21b808cf1401b9b1af3b9").name("user1").password("password1").email("email1@email.com").build();

        when(userRepository.update(any(User.class))).thenReturn(user1);
        String userJSON = mapper.writeValueAsString(userDTO1);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/users/67a21b808cf1401b9b1af3b9").contentType(MediaType.APPLICATION_JSON).content(userJSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(userJSON));
        verify(userRepository).update(eq(user1));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user1 = User.builder().id(new ObjectId("67a21b808cf1401b9b1af3b9")).name("user1").password("password1").email("email1@email.com").build();
        UserDTO userDTO1 = UserDTO.builder().id("67a21b808cf1401b9b1af3b9").name("user1").password("password1").email("email1@email.com").build();

        when(userRepository.delete(anyString())).thenReturn(1L);
        String userJSON = mapper.writeValueAsString(userDTO1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/users/67a21b808cf1401b9b1af3b9").contentType(MediaType.APPLICATION_JSON).content(userJSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("1"));
        verify(userRepository).delete(eq("67a21b808cf1401b9b1af3b9"));
        verifyNoMoreInteractions(userRepository);
    }
}
