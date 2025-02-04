package com.example.usermanagement.model.mapper;

import com.example.usermanagement.model.User;
import com.example.usermanagement.model.dto.UserDTO;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapperTest {

    @Test
    public void testUserToUserDTO() {
        User user = User.builder().id(new ObjectId("67a21b808cf1401b9b1af3b9")).name("somename").password("somepassword").email("user@somedomain.com").lastLogin(new Date(1738677177)).build();
        UserDTO userDTO = Mapper.toDto(user);
        assertEquals("67a21b808cf1401b9b1af3b9", userDTO.getId());
        assertEquals("somename", userDTO.getName());
        assertEquals("somepassword", userDTO.getPassword());
        assertEquals("user@somedomain.com", userDTO.getEmail());
        assertEquals(new Date(1738677177), userDTO.getLastLogin());
    }

    @Test
    public void testUserDTOToUser() {
        UserDTO userDTO = UserDTO.builder().id("67a21b808cf1401b9b1af3b9").name("somename").password("somepassword").email("user@somedomain.com").lastLogin(new Date(1738677177)).build();
        User user = Mapper.toUser(userDTO);
        assertEquals(new ObjectId("67a21b808cf1401b9b1af3b9"), user.getId());
        assertEquals("somename", user.getName());
        assertEquals("somepassword", user.getPassword());
        assertEquals("user@somedomain.com", user.getEmail());
        assertEquals(null, user.getLastLogin());
    }

    @Test
    public void testNullUserToUserDTO() {
        assertThrows(NullPointerException.class,
                () -> Mapper.toDto(null));
    }

    @Test
    public void testNullUserDTOToUser() {
        assertThrows(NullPointerException.class,
                () -> Mapper.toUser(null));
    }
}
