package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobDTO;

import java.util.List;

public interface IJobService {
    JobDTO addJob(JobDTO dto);

    JobDTO getJobById(int jobId);

    List<JobDTO> getAllJobs();

    JobDTO updateJob(int jobId, JobDTO dto);

    void deleteJob(int jobId);

    List<JobDTO> getJobsByEmployerId(int employerId);

    List<JobDTO> searchJobsByTitle(String title);

    List<JobDTO> searchJobsByLocation(String location);

    List<JobDTO> searchJobsByJobType(String jobType);

    List<JobDTO> searchJobsByExperience(int experience);

    List<JobDTO> getRecommendedJobs(int profileId);
}
