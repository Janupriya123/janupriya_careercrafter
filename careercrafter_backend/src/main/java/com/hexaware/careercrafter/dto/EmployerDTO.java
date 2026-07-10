package com.hexaware.careercrafter.dto;

import com.hexaware.careercrafter.entity.Job;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployerDTO {

    private Integer employerId;

    private Integer userId;

    @NotBlank(message="Company name is required")
    @Pattern(
            regexp="^[A-Za-z][A-Za-z0-9 ]*$",
            message="Company name must start with a letter"
    )
    private String companyName;

    @Size(max=500)
    private String website;

    @NotBlank
    private String industry;

    @NotBlank
    private String companySize;

    @Size(max=500)
    private String companyDescription;


}
