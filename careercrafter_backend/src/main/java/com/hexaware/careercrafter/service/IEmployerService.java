package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.EmployerDTO;

import java.util.List;

public interface IEmployerService {
    EmployerDTO addEmployer(EmployerDTO dto);

    EmployerDTO getEmployerById(int employerId);

    List<EmployerDTO> getAllEmployers();

    EmployerDTO updateEmployer(
            int employerId,
            EmployerDTO dto);

    void deleteEmployer(int employerId);

    EmployerDTO getEmployerByUserId(int userId);
}
