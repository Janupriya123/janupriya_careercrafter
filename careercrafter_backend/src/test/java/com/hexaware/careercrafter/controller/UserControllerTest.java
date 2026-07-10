package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.LoginDTO;
import com.hexaware.careercrafter.dto.UserDTO;
import com.hexaware.careercrafter.dto.UserRegisterDTO;
import com.hexaware.careercrafter.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IUserService service;

    @Test
    void createUser() {

        UserDTO dto = new UserDTO();

        dto.setFullName("Test User");
        dto.setEmail("testuser999@gmail.com");
        dto.setPassword("test123");
        dto.setPhone("9876543210");
        dto.setRole("JOBSEEKER");
        dto.setActive(true);

        ResponseEntity<UserRegisterDTO> response =
                restTemplate.postForEntity(
                        "http://localhost:8080/user/create",
                        dto,
                        UserRegisterDTO.class);

        assertEquals(
                "testuser999@gmail.com",
                response.getBody().getEmail());
    }

    @Test
    void getUserById() {

        UserRegisterDTO dto =
                restTemplate.getForObject(
                        "http://localhost:8080/user/getById/102",
                        UserRegisterDTO.class);

        assertEquals(
                102,
                dto.getUserId());
    }

    @Test
    void getAllUsers() {

        ResponseEntity<UserRegisterDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/user/getAll",
                        UserRegisterDTO[].class);

        assertTrue(
                response.getBody().length > 0);
    }

    @Test
    void updateUser() {

        UserDTO dto = new UserDTO();

        dto.setUserId(102);
        dto.setFullName("Updated User");
        dto.setEmail("testuser100@gmail.com");
        dto.setPassword("test123");
        dto.setPhone("9876543210");
        dto.setRole("JOBSEEKER");
        dto.setActive(true);

        restTemplate.put(
                "http://localhost:8080/user/update/102",
                dto);

        UserRegisterDTO updated =
                service.getUserById(102);

        assertEquals(
                "Updated User",
                updated.getFullName());
    }

    @Test
    void deleteUser() {

        UserDTO dto = new UserDTO();

        dto.setFullName("Delete User");
        dto.setEmail("delete999@gmail.com");
        dto.setPassword("test123");
        dto.setPhone("9876543299");
        dto.setRole("JOBSEEKER");
        dto.setActive(true);

        UserRegisterDTO saved =
                restTemplate.postForObject(
                        "http://localhost:8080/user/create",
                        dto,
                        UserRegisterDTO.class);

        int userId =
                saved.getUserId();

        restTemplate.delete(
                "http://localhost:8080/user/delete/" + userId);

        assertThrows(
                Exception.class,
                () -> restTemplate.getForObject(
                        "http://localhost:8080/user/getById/" + userId,
                        UserRegisterDTO.class));
    }

    @Test
    void login() {

        LoginDTO dto =
                new LoginDTO();

        dto.setEmail(
                "testuser100@gmail.com");

        dto.setPassword(
                "test123");

        String token =
                restTemplate.postForObject(
                        "http://localhost:8080/user/login",
                        dto,
                        String.class);

        assertNotNull(token);
    }
}