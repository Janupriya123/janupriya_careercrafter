package com.hexaware.careercrafter.dto;

import com.hexaware.careercrafter.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerProfileDTO {

    private Integer profileId;
    private Integer userId;

    @NotBlank
    private String gender;

    @NotBlank
    private String address;

    @NotBlank
    private String education;

    @NotBlank
    private String skills;

    @Min(0)
    @Max(60)
    private Integer experienceYears;

    private String currentCompany;

    @PositiveOrZero
    private Double currentSalary;

    @Positive
    private Double  expectedSalary;

    @NotBlank
    private String profileSummary;
}
