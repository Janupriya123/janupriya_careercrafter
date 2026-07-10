package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.ApplicationDTO; 
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ApplicationImplTest {

    @Autowired
    private IApplicationService service;
    @Autowired
    private RestTemplate restTemplate;
    @Test
    void applyForJob() {
        ApplicationDTO dto = new ApplicationDTO();

        dto.setJobId(1);
        dto.setJobSeekerId(1);
        dto.setStatus("Applied");
        dto.setCoverLetter("Interested");

        ApplicationDTO result =
                service.applyForJob(dto);

        assertNotNull(result);
        assertEquals("Applied",
                result.getStatus());
    }

    @Test
    void getApplicationById() {
        ApplicationDTO dto =
                service.getApplicationById(2L);

        assertNotNull(dto);
    }

    @Test
    void getAllApplications() {
        List<ApplicationDTO> list =
                service.getAllApplications();

        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    void findByJobJobId() {

        List<ApplicationDTO> list =
                service.findByJobJobId(1);

        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    void findByJobSeekerProfileId() {

        List<ApplicationDTO> list =
                service.findByJobSeekerProfileId(1);

        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    void updateApplicationStatus() {
        ApplicationDTO dto =
                service.updateApplicationStatus(
                        2L,
                        "Approved");

        assertEquals(
                "Approved",
                dto.getStatus());
    }

    @Test
    void deleteApplication() {

        Long id = 2L;

        service.deleteApplication(id);

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getApplicationById(id));
    }
}
