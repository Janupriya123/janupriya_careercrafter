package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.JobDTO;
import com.hexaware.careercrafter.service.IJobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private IJobService service;

    @PostMapping("/add")
    public JobDTO addJob( @Valid @RequestBody JobDTO dto) {
        return service.addJob(dto);
    }

    @GetMapping("/{jobId}")
    public JobDTO getJobById(@PathVariable int jobId) {
        return service.getJobById(jobId);
    }

    @GetMapping("/all")
    public List<JobDTO> getAllJobs() {
        return service.getAllJobs();
    }

    @PutMapping("/update/{jobId}")
    public JobDTO updateJob(@PathVariable int jobId, @Valid   @RequestBody JobDTO dto) {
        return service.updateJob(jobId, dto);
    }

    @DeleteMapping("/delete/{jobId}")
    public void deleteJob(@PathVariable int jobId) {
        service.deleteJob(jobId);
    }

    @GetMapping("/employer/{employerId}")
    public List<JobDTO> getJobsByEmployerId(@PathVariable int employerId) {
        return service.getJobsByEmployerId(employerId);
    }

    @GetMapping("/search/title/{title}")
    public List<JobDTO> searchJobsByTitle(@PathVariable String title) {
        return service.searchJobsByTitle(title);
    }

    @GetMapping("/search/location/{location}")
    public List<JobDTO> searchJobsByLocation(@PathVariable String location) {
        return service.searchJobsByLocation(location);
    }

    @GetMapping("/search/type/{jobType}")
    public List<JobDTO> searchJobsByJobType(
            @PathVariable String jobType) {

        return service.searchJobsByJobType(jobType);
    }

    @GetMapping("/search/experience/{experience}")
    public List<JobDTO> searchJobsByExperience(
            @PathVariable int experience) {

        return service.searchJobsByExperience(experience);
    }
    @GetMapping("/recommend/{profileId}")
    public List<JobDTO> getRecommendedJobs(@PathVariable int profileId) {

        return service.getRecommendedJobs(profileId);

    }
}
