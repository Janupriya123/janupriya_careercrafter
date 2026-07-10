package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobDTO;
import com.hexaware.careercrafter.entity.Application;
import com.hexaware.careercrafter.entity.Employer;
import com.hexaware.careercrafter.entity.Job;
import com.hexaware.careercrafter.entity.JobSeekerProfile;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.ApplicationRepository;
import com.hexaware.careercrafter.repository.EmployerRepository;
import com.hexaware.careercrafter.repository.JobRepository;
import com.hexaware.careercrafter.repository.JobSeekerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JobServiceImpl implements IJobService {
    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    @Autowired
    private JobRepository repo;
    @Autowired
    private ApplicationRepository ap;
    @Autowired
    private EmployerRepository er;
    @Autowired
    private JobSeekerProfileRepository jr;
    @Override
    public JobDTO addJob(JobDTO dto) {
        logger.info("Adding new job for employerId: {}", dto.getEmployerId());
        Employer emp =er.findById(dto.getEmployerId())
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        Job j = new Job();

        j.setEmployer(emp);
        j.setTitle(dto.getTitle());
        j.setDescription(dto.getDescription());
        j.setLocation(dto.getLocation());
        j.setExperienceRequired(dto.getExperienceRequired());
        j.setSalaryMin(dto.getSalaryMin());
        j.setSalaryMax(dto.getSalaryMax());
        j.setJobType(dto.getJobType());
        j.setVacancies(dto.getVacancies());
        j.setApplicationDeadline(dto.getApplicationDeadline());
        j.setStatus(dto.getStatus());

        Job  s = repo.save(j);
        logger.info("Job created successfully with jobId: {}", s.getJobId());
        JobDTO d = new JobDTO();

        d.setJobId(s.getJobId());
        d.setEmployerId(s.getEmployer().getEmployerId());
        d.setTitle(s.getTitle());
        d.setDescription(s.getDescription());
        d.setLocation(s.getLocation());
        d.setExperienceRequired(s.getExperienceRequired());
        d.setSalaryMin(s.getSalaryMin());
        d.setSalaryMax(s.getSalaryMax());
        d.setJobType(s.getJobType());
        d.setVacancies(s.getVacancies());
        d.setApplicationDeadline(s.getApplicationDeadline());
        d.setStatus(s.getStatus());
        d.setCompanyName(s.getEmployer().getCompanyName());
        return d;
    }

    @Override
    public JobDTO getJobById(int jobId) {
        logger.info("Fetching job with id: {}", jobId);
         Job s=repo.findById(jobId).orElseThrow(()-> new ResourceNotFoundException("ID not Exist"));
        JobDTO d = new JobDTO();

        d.setJobId(s.getJobId());
        d.setEmployerId(s.getEmployer().getEmployerId());
        d.setTitle(s.getTitle());
        d.setDescription(s.getDescription());
        d.setLocation(s.getLocation());
        d.setExperienceRequired(s.getExperienceRequired());
        d.setSalaryMin(s.getSalaryMin());
        d.setSalaryMax(s.getSalaryMax());
        d.setJobType(s.getJobType());
        d.setVacancies(s.getVacancies());
        d.setApplicationDeadline(s.getApplicationDeadline());
        d.setStatus(s.getStatus());
        d.setCompanyName(s.getEmployer().getCompanyName());
        logger.info("Job retrieved successfully with id: {}", jobId);
        return d;

    }

    @Override
    public List<JobDTO> getAllJobs() {
        logger.info("Fetching all jobs");
        List<JobDTO> list = new ArrayList<>();

        List<Job> jobs = repo.findAll();

        for(Job j : jobs) {

            JobDTO d = new JobDTO();

            d.setJobId(j.getJobId());
            d.setEmployerId(j.getEmployer().getEmployerId());
            d.setTitle(j.getTitle());
            d.setDescription(j.getDescription());
            d.setLocation(j.getLocation());
            d.setExperienceRequired(j.getExperienceRequired());
            d.setSalaryMin(j.getSalaryMin());
            d.setSalaryMax(j.getSalaryMax());
            d.setJobType(j.getJobType());
            d.setVacancies(j.getVacancies());
            d.setApplicationDeadline(j.getApplicationDeadline());
            d.setStatus(j.getStatus());
            d.setCompanyName(j.getEmployer().getCompanyName());
            list.add(d);
        }

        logger.info("Total jobs found: {}", jobs.size());

        return list;
    }

    @Override
    public JobDTO updateJob(int jobId, JobDTO dto) {
        logger.info("Updating job with id: {}", jobId);
        Job j = repo.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Employer e=er.findById(dto.getEmployerId()).orElseThrow(()->new RuntimeException("Id not exist"));

        j.setEmployer(e);
        j.setTitle(dto.getTitle());
        j.setDescription(dto.getDescription());
        j.setLocation(dto.getLocation());
        j.setExperienceRequired(dto.getExperienceRequired());
        j.setSalaryMin(dto.getSalaryMin());
        j.setSalaryMax(dto.getSalaryMax());
        j.setJobType(dto.getJobType());
        j.setVacancies(dto.getVacancies());
        j.setApplicationDeadline(dto.getApplicationDeadline());
        j.setStatus(dto.getStatus());

        Job s = repo.save(j);
        logger.info("Job updated successfully with id: {}", s.getJobId());

        return getJobById(s.getJobId());
    }

    @Override
    public void deleteJob(int jobId) {
        logger.info("Deleting job with id: {}", jobId);
        Job j=repo.findById(jobId).orElseThrow(()-> new ResourceNotFoundException("IdD not exist"));
        if(ap.existsByJobJobId(jobId))
        {
            throw new RuntimeException(
                    "Cannot delete job because candidates have already applied."
            );
        }


           repo.deleteById(jobId);
        logger.info("Job deleted successfully with id: {}", jobId);
    }

    @Override
    public List<JobDTO> getJobsByEmployerId(int employerId) {
        logger.info("Fetching jobs for employerId: {}", employerId);
        List<JobDTO> list = new ArrayList<>();
        List<Job> jobs = repo.findByEmployerEmployerId(employerId);

        for(Job j : jobs)
        {
            JobDTO d = new JobDTO();

            d.setJobId(j.getJobId());
            d.setEmployerId(j.getEmployer().getEmployerId());
            d.setTitle(j.getTitle());
            d.setDescription(j.getDescription());
            d.setLocation(j.getLocation());
            d.setExperienceRequired(j.getExperienceRequired());
            d.setSalaryMin(j.getSalaryMin());
            d.setSalaryMax(j.getSalaryMax());
            d.setJobType(j.getJobType());
            d.setVacancies(j.getVacancies());
            d.setApplicationDeadline(j.getApplicationDeadline());
            d.setStatus(j.getStatus());

            list.add(d);
        }

        logger.info("Found {} jobs for employerId: {}", jobs.size(), employerId);

        return list;
    }

    @Override
    public List<JobDTO> searchJobsByTitle(String title) {
        logger.info("Searching jobs with title: {}", title);
        List<JobDTO> list = new ArrayList<>();
        List<Job> jobs = repo.findByTitleContainingIgnoreCase(title);

        for(Job j : jobs)
        {
            JobDTO d = new JobDTO();

            d.setJobId(j.getJobId());
            d.setEmployerId(j.getEmployer().getEmployerId());
            d.setTitle(j.getTitle());
            d.setDescription(j.getDescription());
            d.setLocation(j.getLocation());
            d.setExperienceRequired(j.getExperienceRequired());
            d.setSalaryMin(j.getSalaryMin());
            d.setSalaryMax(j.getSalaryMax());
            d.setJobType(j.getJobType());
            d.setVacancies(j.getVacancies());
            d.setApplicationDeadline(j.getApplicationDeadline());
            d.setStatus(j.getStatus());
            d.setCompanyName(j.getEmployer().getCompanyName());
            list.add(d);
        }

        logger.info("Found {} jobs matching title: {}", jobs.size(), title);
        return list;
    }

    @Override
    public List<JobDTO> searchJobsByLocation(String location) {
        logger.info("Searching jobs in location: {}", location);

        List<JobDTO> list = new ArrayList<>();

        List<Job> jobs = repo.findByLocationContainingIgnoreCase(location);

        for(Job j : jobs)
        {
            JobDTO d = new JobDTO();

            d.setJobId(j.getJobId());
            d.setEmployerId(j.getEmployer().getEmployerId());
            d.setTitle(j.getTitle());
            d.setDescription(j.getDescription());
            d.setLocation(j.getLocation());
            d.setExperienceRequired(j.getExperienceRequired());
            d.setSalaryMin(j.getSalaryMin());
            d.setSalaryMax(j.getSalaryMax());
            d.setJobType(j.getJobType());
            d.setVacancies(j.getVacancies());
            d.setApplicationDeadline(j.getApplicationDeadline());
            d.setStatus(j.getStatus());
            d.setCompanyName(j.getEmployer().getCompanyName());
            list.add(d);
        }
        logger.info("Found {} jobs in location: {}", jobs.size(), location);
        return list;
    }

    @Override
    public List<JobDTO> searchJobsByJobType(String jobType) {
        logger.info("Searching jobs with type: {}", jobType);
        List<JobDTO> list = new ArrayList<>();

        List<Job> jobs = repo.findByJobTypeIgnoreCase(jobType);

        for(Job j : jobs)
        {
            JobDTO d = new JobDTO();

            d.setJobId(j.getJobId());
            d.setEmployerId(j.getEmployer().getEmployerId());
            d.setTitle(j.getTitle());
            d.setDescription(j.getDescription());
            d.setLocation(j.getLocation());
            d.setExperienceRequired(j.getExperienceRequired());
            d.setSalaryMin(j.getSalaryMin());
            d.setSalaryMax(j.getSalaryMax());
            d.setJobType(j.getJobType());
            d.setVacancies(j.getVacancies());
            d.setApplicationDeadline(j.getApplicationDeadline());
            d.setStatus(j.getStatus());
            d.setCompanyName(j.getEmployer().getCompanyName());
            list.add(d);
        }

        logger.info("Found {} jobs with type: {}", jobs.size(), jobType);
        return list;
    }

    @Override
    public List<JobDTO> searchJobsByExperience(int experience) {
        logger.info("Searching jobs requiring {} years of experience", experience);
        List<JobDTO> list = new ArrayList<>();

        List<Job> jobs = repo.findByExperienceRequired(experience);

        for(Job j : jobs)
        {
            JobDTO d = new JobDTO();

            d.setJobId(j.getJobId());
            d.setEmployerId(j.getEmployer().getEmployerId());
            d.setTitle(j.getTitle());
            d.setDescription(j.getDescription());
            d.setLocation(j.getLocation());
            d.setExperienceRequired(j.getExperienceRequired());
            d.setSalaryMin(j.getSalaryMin());
            d.setSalaryMax(j.getSalaryMax());
            d.setJobType(j.getJobType());
            d.setVacancies(j.getVacancies());
            d.setApplicationDeadline(j.getApplicationDeadline());
            d.setStatus(j.getStatus());
            d.setCompanyName(j.getEmployer().getCompanyName());

            list.add(d);
        }
        logger.info("Found {} jobs matching experience", jobs.size());

        return list;
    }
    @Override
    public List<JobDTO> getRecommendedJobs(int profileId) {
        logger.info("Fetching recommended jobs for profileId: {}", profileId);


        JobSeekerProfile profile = jr.findById(profileId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Profile not found"));

        String[] skills = profile.getSkills().split(",");

        List<Job> recommendedJobs = new ArrayList<>();

        for (String skill : skills) {

            List<Job> jobs = repo.findRecommendedJobs(skill.trim());

            for (Job job : jobs) {

                if (job.getExperienceRequired() <= profile.getExperienceYears() + 2
                        && !recommendedJobs.contains(job)) {

                    recommendedJobs.add(job);
                }
            }
        }

        List<JobDTO> list = new ArrayList<>();

        for (Job job : recommendedJobs) {

            JobDTO dto = new JobDTO();

            dto.setJobId(job.getJobId());
            dto.setEmployerId(job.getEmployer().getEmployerId());
            dto.setTitle(job.getTitle());
            dto.setDescription(job.getDescription());
            dto.setLocation(job.getLocation());
            dto.setExperienceRequired(job.getExperienceRequired());
            dto.setSalaryMin(job.getSalaryMin());
            dto.setSalaryMax(job.getSalaryMax());
            dto.setJobType(job.getJobType());
            dto.setVacancies(job.getVacancies());
            dto.setApplicationDeadline(job.getApplicationDeadline());
            dto.setStatus(job.getStatus());
            dto.setCompanyName(job.getEmployer().getCompanyName());

            list.add(dto);
        }
        logger.info("Recommended {} jobs for profileId: {}", recommendedJobs.size(), profileId);

        return list;
    }


    }
