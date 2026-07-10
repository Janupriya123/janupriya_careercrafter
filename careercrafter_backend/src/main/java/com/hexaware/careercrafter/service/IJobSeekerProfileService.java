package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobSeekerProfileDTO;

import java.util.List;

public interface IJobSeekerProfileService {


      public  JobSeekerProfileDTO createProfile(JobSeekerProfileDTO dto);

    public JobSeekerProfileDTO getProfileById(int profileId);

    public  List<JobSeekerProfileDTO> getAllProfiles();

    public JobSeekerProfileDTO updateProfile(
                int profileId,
                JobSeekerProfileDTO dto);

    public void deleteProfile(int profileId);

    public JobSeekerProfileDTO getProfileByUserId(int userId);

    public List<JobSeekerProfileDTO> searchBySkills(String skills);

    }
