package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.EmployerDTO;
import com.hexaware.careercrafter.service.IEmployerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmployerControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEmployerService service;

    @Test
    void addEmployer() {

        EmployerDTO dto = new EmployerDTO();

        dto.setUserId(4);
        dto.setCompanyName("Infosys");
        dto.setWebsite("https://www.infosys.com");
        dto.setIndustry("IT");
        dto.setCompanySize("10000+");
        dto.setCompanyDescription("Software Company");

        ResponseEntity<EmployerDTO> response =
                restTemplate.postForEntity(
                        "http://localhost:8080/employer/add",
                        dto,
                        EmployerDTO.class);

        EmployerDTO result = response.getBody();

        assertEquals("Infosys",
                result.getCompanyName());
    }

    @Test
    void getEmployerById() {

        EmployerDTO dto =
                restTemplate.getForObject(
                        "http://localhost:8080/employer/1",
                        EmployerDTO.class);

        assertEquals(1,
                dto.getEmployerId());
    }

    @Test
    void getAllEmployers() {

        ResponseEntity<EmployerDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/employer/all",
                        EmployerDTO[].class);

        EmployerDTO[] list = response.getBody();

        assertTrue(list.length > 0);
    }

    @Test
    void updateEmployer() {

        EmployerDTO dto = new EmployerDTO();

        dto.setUserId(1);
        dto.setCompanyName("Updated Company");
        dto.setWebsite("https://www.test.com");
        dto.setIndustry("Software");
        dto.setCompanySize("5000");
        dto.setCompanyDescription("Updated");

        restTemplate.put(
                "http://localhost:8080/employer/update/1",
                dto);

        EmployerDTO result =
                service.getEmployerById(1);

        assertEquals(
                "Updated Company",
                result.getCompanyName());
    }

    @Test
    void deleteEmployer() {

        int employerId = 11;

        restTemplate.delete(
                "http://localhost:8080/employer/delete/" + employerId);

        assertThrows(
                Exception.class,
                () -> restTemplate.getForObject(
                        "http://localhost:8080/employer/" + employerId,
                        EmployerDTO.class));
    }

    @Test
    void getEmployerByUserId() {

        EmployerDTO dto =
                restTemplate.getForObject(
                        "http://localhost:8080/employer/user/3",
                        EmployerDTO.class);

        assertEquals(3,
                dto.getUserId());
    }
}