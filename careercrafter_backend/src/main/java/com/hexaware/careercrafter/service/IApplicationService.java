package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.ApplicationDTO;

import java.util.List;

public interface IApplicationService {


      public   ApplicationDTO applyForJob(ApplicationDTO dto);

       public  ApplicationDTO getApplicationById(Long applicationId);

      public  List<ApplicationDTO> getAllApplications();

        List<ApplicationDTO> findByJobJobId(int jobId);

        List<ApplicationDTO> findByJobSeekerProfileId(int profileId);

        ApplicationDTO updateApplicationStatus(
                Long applicationId,
                String status);

        void deleteApplication(Long applicationId);
    }

