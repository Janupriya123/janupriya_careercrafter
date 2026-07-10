package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.ApplicationDTO;
import com.hexaware.careercrafter.service.IApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private IApplicationService service;

    @PostMapping("/create")
   public ApplicationDTO applyForJob( @Valid @RequestBody  ApplicationDTO dto)
    {
        return service.applyForJob(dto);
    }

    @GetMapping("/getById/{id}")
    public ApplicationDTO getApplicationById(   @PathVariable  Long id)
    {
        return  service.getApplicationById(id);
    }

    @GetMapping("/getall")
   public List<ApplicationDTO> getAllApplications()
    {
        return service.getAllApplications();
    }

    @GetMapping("/getByJobId/{jobId}")
    public List<ApplicationDTO> getApplicationsByJobId(@PathVariable int jobId)
    {
        return service.findByJobJobId(jobId);
    }

    @GetMapping("/getByjsid/{profileId}")
   public List<ApplicationDTO> getApplicationsByJobSeekerId(@PathVariable int profileId)
    {
        return service.findByJobSeekerProfileId(profileId);
    }

    @PutMapping("/update/{aid}/{status}")
   public ApplicationDTO updateApplicationStatus(@PathVariable Long aid,@PathVariable String status)
    {
        return  service.updateApplicationStatus(aid,status);
    }

    @DeleteMapping("delete/{aid}")
   public String deleteApplication(@PathVariable  Long aid)
    {
        service.deleteApplication(aid);
        return "Deleted successfully";

    }
}
