package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobSeekerProfileDTO;
import com.hexaware.careercrafter.service.IJobSeekerProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class JobSeekerProfileController {
    @Autowired
    private IJobSeekerProfileService service;

    @PostMapping("/add")
    public JobSeekerProfileDTO createProfile(
            @Valid @RequestBody JobSeekerProfileDTO dto) {

        System.out.println("PROFILE API HIT");

        return service.createProfile(dto);
    }

    @GetMapping("/{profileId}")
    public JobSeekerProfileDTO getProfileById(@PathVariable int profileId) {
        return service.getProfileById(profileId);
    }

    @GetMapping("/all")
    public List<JobSeekerProfileDTO> getAllProfiles() {
        return service.getAllProfiles();
    }

    @PutMapping("/update/{profileId}")
    public JobSeekerProfileDTO updateProfile(@PathVariable int profileId, @Valid  @RequestBody JobSeekerProfileDTO dto)
    {
        return service.updateProfile(profileId, dto);
    }

    @DeleteMapping("/delete/{profileId}")
    public String deleteProfile(@PathVariable int profileId) {
        service.deleteProfile(profileId);
        return "Deleted Sucessfully";
    }

    @GetMapping("/user/{userId}")
    public JobSeekerProfileDTO getProfileByUserId(@PathVariable int userId) {
        return service.getProfileByUserId(userId);
    }

    @GetMapping("/skills/{skills}")
    public List<JobSeekerProfileDTO> searchBySkills(@PathVariable String skills) {
        return service.searchBySkills(skills);
    }
}
