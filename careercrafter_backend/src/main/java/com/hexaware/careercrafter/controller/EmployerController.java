package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.EmployerDTO;
import com.hexaware.careercrafter.service.IEmployerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private IEmployerService service;

    @PostMapping("/add")
    public EmployerDTO addEmployer(@Valid @RequestBody EmployerDTO dto) {
        return service.addEmployer(dto);
    }

    @GetMapping("/{employerId}")
    public EmployerDTO getEmployerById(
            @PathVariable int employerId) {

        return service.getEmployerById(employerId);
    }

    @GetMapping("/all")
    public List<EmployerDTO> getAllEmployers() {

        return service.getAllEmployers();
    }

    @PutMapping("/update/{employerId}")
    public EmployerDTO updateEmployer(
            @PathVariable int employerId,
            @Valid  @RequestBody EmployerDTO dto) {

        return service.updateEmployer(employerId, dto);
    }

    @DeleteMapping("/delete/{employerId}")
    public void deleteEmployer(
            @PathVariable int employerId) {

        service.deleteEmployer(employerId);
    }

    @GetMapping("/user/{userId}")
    public EmployerDTO getEmployerByUserId(
            @PathVariable int userId) {

        return service.getEmployerByUserId(userId);
    }
}
