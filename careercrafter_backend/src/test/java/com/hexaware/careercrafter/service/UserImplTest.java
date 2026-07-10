package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.UserDTO;
import com.hexaware.careercrafter.dto.UserRegisterDTO;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserImplTest {

    @Autowired
    private IUserService service;

    @Test
    void registerUser() {

        UserDTO dto = new UserDTO();

        dto.setFullName("Service User");
        dto.setEmail("serviceuser@gmail.com");
        dto.setPassword("test123");
        dto.setPhone("9876543210");
        dto.setRole("JOBSEEKER");
        dto.setActive(true);

        UserRegisterDTO user =
                service.registerUser(dto);

        assertEquals(
                "serviceuser@gmail.com",
                user.getEmail());
    }

    @Test
    void getUserById() {

        UserRegisterDTO dto =
                service.getUserById(102);

        assertEquals(
                102,
                dto.getUserId());
    }

    @Test
    void getAllUsers() {

        List<UserRegisterDTO> users =
                service.getAllUsers();

        assertFalse(
                users.isEmpty());
    }

    @Test
    void updateUser() {

        UserDTO dto = new UserDTO();

        dto.setFullName("Updated Service User");
        dto.setEmail("testuser100@gmail.com");
        dto.setPassword("test123");
        dto.setPhone("9876543210");
        dto.setRole("JOBSEEKER");
        dto.setActive(true);

        UserRegisterDTO updated =
                service.updateUser(
                        102,
                        dto);

        assertEquals(
                "Updated Service User",
                updated.getFullName());
    }

    @Test
    void deleteUser() {

        UserDTO dto = new UserDTO();

        dto.setFullName("Delete User");
        dto.setEmail("deleteuser@gmail.com");
        dto.setPassword("test123");
        dto.setPhone("9876543211");
        dto.setRole("JOBSEEKER");
        dto.setActive(true);

        UserRegisterDTO saved =
                service.registerUser(dto);

        int userId =
                saved.getUserId();

        service.deleteUser(userId);

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getUserById(userId));
    }

   /* @Test
    void login() {

        LoginDTO dto = new LoginDTO();

        dto.setEmail("testuser100@gmail.com");
        dto.setPassword("test123");

        String token =
                service.login(dto);

        assertNotNull(token);
    } */
}