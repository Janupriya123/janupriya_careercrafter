package com.hexaware.careercrafter.dto;

import com.hexaware.careercrafter.entity.Employer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {

    private Integer jobId;
    private Integer employerId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    @Min(0)
    private Integer experienceRequired;

    @Positive
    private Double salaryMin;

    @Positive
    private Double salaryMax;

    @NotBlank
    private String jobType;

    @Min(1)
    private Integer vacancies;

    @Future
    private LocalDate applicationDeadline;

    @NotBlank
    private String status;
    private String companyName;

}
