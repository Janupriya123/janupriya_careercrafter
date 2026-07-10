package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.EmployerDTO;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmployerImplTest {

    @Autowired
    private IEmployerService service;

    @Test
    void addEmployer() {
        EmployerDTO dto = new EmployerDTO();

        dto.setUserId(5);
        dto.setCompanyName("Infosys");
        dto.setWebsite("https://www.infosys.com");
        dto.setIndustry("IT");
        dto.setCompanySize("10000+");
        dto.setCompanyDescription("Software Services Company");

        EmployerDTO result = service.addEmployer(dto);

        assertNotNull(result);

        assertEquals("Infosys",
                result.getCompanyName());

        assertEquals(5,
                result.getUserId());
    }

    @Test
    void getEmployerById() {

        EmployerDTO dto = service.getEmployerById(1);

        assertEquals(1, dto.getEmployerId());
    }

    @Test
    void getAllEmployers() {

        List<EmployerDTO> list = service.getAllEmployers();

        assertFalse(list.isEmpty());
    }

    @Test
    void updateEmployer() {

        EmployerDTO dto = new EmployerDTO();

        dto.setUserId(1);
        dto.setCompanyName("Hexaware Updated");
        dto.setWebsite("https://www.hexaware.com");
        dto.setIndustry("Software");
        dto.setCompanySize("2000+");
        dto.setCompanyDescription("Updated Description");

        EmployerDTO result =
                service.updateEmployer(1, dto);

        assertEquals(
                "Hexaware Updated",
                result.getCompanyName());
    }

    @Test
    void deleteEmployer() {

        EmployerDTO dto = new EmployerDTO();
        dto.setUserId(5);
        dto.setCompanyName("Delete Test");
        dto.setWebsite("www.test.com");
        dto.setIndustry("IT");
        dto.setCompanySize("100");
        dto.setCompanyDescription("Testing");

        EmployerDTO saved = service.addEmployer(dto);

        int employerId = saved.getEmployerId();

        service.deleteEmployer(employerId);

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getEmployerById(employerId));
    }

    @Test
    void getEmployerByUserId() {

        EmployerDTO dto =
                service.getEmployerByUserId(1);

        assertEquals(1, dto.getUserId());
    }
}