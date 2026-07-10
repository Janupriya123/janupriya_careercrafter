package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobSeekerProfileDTO;
import com.hexaware.careercrafter.entity.JobSeekerProfile;
import com.hexaware.careercrafter.entity.User;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.JobSeekerProfileRepository;
import com.hexaware.careercrafter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobSeekerProfileServiceImpl implements IJobSeekerProfileService {
    private static final Logger logger = LoggerFactory.getLogger(JobSeekerProfileServiceImpl.class);
    @Autowired
   private JobSeekerProfileRepository repo;
    @Autowired
    private UserRepository userRepo;
    @Override
    public JobSeekerProfileDTO createProfile(JobSeekerProfileDTO dto) {
        logger.info("Creating profile for userId: {}", dto.getUserId());
        if(repo.findByUserUserId(dto.getUserId())!=null)
        {
            logger.error("Profile already exists for userId: {}", dto.getUserId());
            throw new RuntimeException(
                    "Profile already exists for this user");
        }

        User user=userRepo.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        JobSeekerProfile p =new JobSeekerProfile();
        p.setUser(user);
        p.setGender(dto.getGender());
        p.setAddress(dto.getAddress());
        p.setEducation(dto.getEducation());
        p.setSkills(dto.getSkills());
        p.setExperienceYears(dto.getExperienceYears());
        p.setCurrentCompany(dto.getCurrentCompany());
        p.setCurrentSalary(dto.getCurrentSalary());
        p.setExpectedSalary(dto.getExpectedSalary());
        p.setProfileSummary(dto.getProfileSummary());

        JobSeekerProfile saved=repo.save(p);
        JobSeekerProfileDTO d=new JobSeekerProfileDTO();

        d.setProfileId(saved.getProfileId());
        d.setUserId(saved.getUser().getUserId());
        d.setGender(saved.getGender());
        d.setAddress(saved.getAddress());
        d.setEducation(saved.getEducation());
        d.setSkills(saved.getSkills());
        d.setExperienceYears(saved.getExperienceYears());
        d.setCurrentCompany(saved.getCurrentCompany());
        d.setCurrentSalary(saved.getCurrentSalary());
        d.setExpectedSalary(saved.getExpectedSalary());
        d.setProfileSummary(saved.getProfileSummary());
        logger.info("Profile created successfully with profileId: {}", saved.getProfileId());
        return d;

    }

    @Override
    public JobSeekerProfileDTO getProfileById(int profileId) {
        logger.info("Fetching profile with id: {}", profileId);
            JobSeekerProfile p=repo.findById(profileId).orElseThrow(() -> new ResourceNotFoundException("Profile Not Exist"));
        logger.info("Profile retrieved successfully with id: {}", profileId);
            return helperDTO(p);
        }


    @Override
    public List<JobSeekerProfileDTO> getAllProfiles() {
        logger.info("Fetching all job seeker profiles");
        List<JobSeekerProfileDTO> list=new ArrayList<>();

        List<JobSeekerProfile> pp=repo.findAll();

        for(JobSeekerProfile p:pp)
        {
            list.add(helperDTO(p));
        }
        logger.info("Total profiles found: {}", pp.size());

        return list;
    }

    @Override
    public JobSeekerProfileDTO updateProfile(int profileId, JobSeekerProfileDTO dto) {
        logger.info("Updating profile with id: {}", profileId);
        JobSeekerProfile p=repo.findById(profileId).orElseThrow(() -> new  ResourceNotFoundException("Profile not found"));

            p.setGender(dto.getGender());
            p.setAddress(dto.getAddress());
            p.setEducation(dto.getEducation());
            p.setSkills(dto.getSkills());
            p.setExperienceYears(dto.getExperienceYears());
            p.setCurrentCompany(dto.getCurrentCompany());
            p.setCurrentSalary(dto.getCurrentSalary());
            p.setExpectedSalary(dto.getExpectedSalary());
            p.setProfileSummary(dto.getProfileSummary());

            JobSeekerProfile s=repo.save(p);
        logger.info("Profile updated successfully with id: {}", profileId);
            return helperDTO(s);
        }


    @Override
    public void deleteProfile(int profileId) {
        logger.info("Deleting profile with id: {}", profileId);
        JobSeekerProfile jp=repo.findById(profileId).orElseThrow(()->new ResourceNotFoundException("ID not exist"));

            repo.deleteById(profileId);
        logger.info("Profile deleted successfully with id: {}", profileId);
    }

    @Override
    public JobSeekerProfileDTO getProfileByUserId(int userId) {
        logger.info("Fetching profile for userId: {}", userId);
        JobSeekerProfile p =
                repo.findByUserUserId(userId);

        if(p == null)
        {
            logger.error("Profile not found for userId: {}", userId);
            throw new ResourceNotFoundException(
                    "Profile not found");
        }

        logger.info("Profile found for userId: {}", userId);

        return helperDTO(p);
    }

    @Override
    public List<JobSeekerProfileDTO> searchBySkills(String skills) {
        logger.info("Searching profiles with skill: {}", skills);
        List<JobSeekerProfile> list=repo.findBySkillsContainingIgnoreCase(skills);
        List<JobSeekerProfileDTO> j=new ArrayList<>();

        for(JobSeekerProfile jp:list)
        {
            j.add(helperDTO(jp));
        }
        logger.info("Found {} profiles matching skill: {}", list.size(), skills);
        return j;

    }
    private JobSeekerProfileDTO helperDTO(JobSeekerProfile p) {

        JobSeekerProfileDTO d = new JobSeekerProfileDTO();

        d.setProfileId(p.getProfileId());
        d.setUserId(p.getUser().getUserId());
        d.setGender(p.getGender());
        d.setAddress(p.getAddress());
        d.setEducation(p.getEducation());
        d.setSkills(p.getSkills());
        d.setExperienceYears(p.getExperienceYears());
        d.setCurrentCompany(p.getCurrentCompany());
        d.setCurrentSalary(p.getCurrentSalary());
        d.setExpectedSalary(p.getExpectedSalary());
        d.setProfileSummary(p.getProfileSummary());

        return d;
    }
}
