package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.ApplicationDTO;
import com.hexaware.careercrafter.dto.NotificationDTO;
import com.hexaware.careercrafter.entity.*;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.ApplicationRepository;
import com.hexaware.careercrafter.repository.JobRepository;
import com.hexaware.careercrafter.repository.JobSeekerProfileRepository;
import com.hexaware.careercrafter.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ApplicationImplService implements IApplicationService {

    private static final Logger log =
            LoggerFactory.getLogger(ApplicationImplService.class);
    @Autowired
    private JobRepository jr;
    @Autowired
    private JobSeekerProfileRepository js;
    @Autowired
    private ApplicationRepository repo;
    @Autowired
    private ResumeRepository resumeRepo;
    @Autowired
    private IMailService mailService;
    @Autowired
    private INotificationService notificationService;

    @Override
    public ApplicationDTO applyForJob(ApplicationDTO dto) {
        log.info("Job application request received. Job ID: {}, Profile ID: {}",
                dto.getJobId(), dto.getJobSeekerId());
        List<Application> existing = repo.findExistingApplications(
                dto.getJobId(),
                dto.getJobSeekerId());
        log.info("Existing applications found: {}", existing.size());
        log.warn("Duplicate application attempt. Job ID: {}, Profile ID: {}",
                dto.getJobId(), dto.getJobSeekerId());

        if (!existing.isEmpty()) {
            throw new RuntimeException("You have already applied for this job");
        }

        Job j = jr.findById(dto.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job ID not exist"));

        JobSeekerProfile jp = js.findById(dto.getJobSeekerId())
                .orElseThrow(() -> new RuntimeException("No Profile Exist"));

       Application a=new Application();
       a.setJob(j);
       a.setJobSeeker(jp);
       a.setAppliedDate(LocalDateTime.now());
       a.setStatus("Applied");
       a.setCoverLetter(dto.getCoverLetter());


       Application aa=repo.save(a);
        log.info("Application submitted successfully. Application ID: {}",
                aa.getApplicationId());
        NotificationDTO notification = new NotificationDTO();

        notification.setUserId(
                aa.getJob()
                        .getEmployer()
                        .getUser()
                        .getUserId());

        notification.setTitle("New Job Application");

        notification.setMessage(
                aa.getJobSeeker().getUser().getFullName()
                        + " applied for "
                        + aa.getJob().getTitle());

        notificationService.createNotification(notification);
        log.info("Notification created for employer.");

        try{

            mailService.sendApplicationMail(
                    aa.getJob().getEmployer().getUser().getEmail(),
                    aa.getJob().getEmployer().getCompanyName(),
                    aa.getJob().getTitle()
            );

        }catch(Exception e){

            log.error("Failed to send application email.", e);

        }
       ApplicationDTO dt=new ApplicationDTO();

       dt.setApplicationId(aa.getApplicationId());
        dt.setJobId(aa.getJob().getJobId());
        dt.setJobSeekerId(aa.getJobSeeker().getProfileId());
        dt.setAppliedDate(aa.getAppliedDate());
        dt.setStatus(aa.getStatus());
        dt.setCoverLetter(aa.getCoverLetter());
        dt.setJobTitle(aa.getJob().getTitle());
        dt.setCompanyName(aa.getJob().getEmployer().getCompanyName());
        dt.setLocation(aa.getJob().getLocation());

        log.info("Application process completed successfully.");
        return dt;

    }

    @Override
    public ApplicationDTO getApplicationById(Long applicationId) {

        log.info("Fetching application with ID: {}", applicationId);
        Application a=repo.findById(applicationId).orElseThrow(()-> new ResourceNotFoundException("Id does not exist"));
        ApplicationDTO dt=new ApplicationDTO();
        dt.setApplicationId(a.getApplicationId());
        dt.setJobId(a.getJob().getJobId());
        dt.setJobSeekerId(a.getJobSeeker().getProfileId());
        dt.setAppliedDate(a.getAppliedDate());
        dt.setStatus(a.getStatus());
        dt.setCoverLetter(a.getCoverLetter());
        dt.setJobTitle(a.getJob().getTitle());
        dt.setCompanyName(a.getJob().getEmployer().getCompanyName());
        dt.setLocation(a.getJob().getLocation());
        List<Resume> resumes = resumeRepo.findByJobSeekerProfileId(
                a.getJobSeeker().getProfileId());

        if(!resumes.isEmpty()){

            Resume resume = resumes.get(0);

            dt.setResumeId(resume.getResumeId());
            dt.setResumeFileName(resume.getFileName());

        }
        dt.setJobSeekerName(
                a.getJobSeeker()
                        .getUser()
                        .getFullName()
        );
        dt.setEmail(
                a.getJobSeeker()
                        .getUser()
                        .getEmail()
        );

        dt.setPhoneNumber(
                a.getJobSeeker()
                        .getUser()
                        .getPhone()
        );

        dt.setEducation(
                a.getJobSeeker()
                        .getEducation()
        );

        dt.setExperienceYears(
                a.getJobSeeker()
                        .getExperienceYears()
        );

        dt.setSkills(
                a.getJobSeeker()
                        .getSkills()
        );
        log.info("Application retrieved successfully.");

        return dt;
    }

    @Override
    public List<ApplicationDTO> getAllApplications() {
        log.info("Fetching all job applications.");

        List<ApplicationDTO> list = new ArrayList<>();

        List<Application> a = repo.findAll();

        for(Application b : a){

            ApplicationDTO d = new ApplicationDTO();

            d.setApplicationId(b.getApplicationId());
            d.setJobId(b.getJob().getJobId());
            d.setJobSeekerId(b.getJobSeeker().getProfileId());
            d.setAppliedDate(b.getAppliedDate());
            d.setStatus(b.getStatus());
            d.setCoverLetter(b.getCoverLetter());
            d.setJobTitle(b.getJob().getTitle());
            d.setCompanyName(b.getJob().getEmployer().getCompanyName());
            d.setLocation(b.getJob().getLocation());

            List<Resume> resumes = resumeRepo.findByJobSeekerProfileId(
                    b.getJobSeeker().getProfileId());

            if(!resumes.isEmpty()){

                Resume resume = resumes.get(0);

                d.setResumeId(resume.getResumeId());
                d.setResumeFileName(resume.getFileName());

            }
            d.setJobSeekerName(
                    b.getJobSeeker()
                            .getUser()
                            .getFullName()
            );
            d.setEmail(
                    b.getJobSeeker()
                            .getUser()
                            .getEmail()
            );

            d.setPhoneNumber(
                    b.getJobSeeker()
                            .getUser()
                            .getPhone()
            );

            d.setEducation(
                    b.getJobSeeker()
                            .getEducation()
            );

            d.setExperienceYears(
                    b.getJobSeeker()
                            .getExperienceYears()
            );

            d.setSkills(
                    b.getJobSeeker()
                            .getSkills()
            );

            list.add(d);

        }
        log.info("Total applications fetched: {}", list.size());
        return list;

    }

    @Override
    public List<ApplicationDTO> findByJobJobId(int jobId) {
        log.info("Fetching applications for Job ID: {}", jobId);
        List<ApplicationDTO> list=new ArrayList<>();
        List<Application> c=repo.findByJobJobId(jobId);
        for(Application b:c)
        {
            ApplicationDTO d=new ApplicationDTO();
            d.setApplicationId(b.getApplicationId());
            d.setJobId(b.getJob().getJobId());
            d.setJobSeekerId(b.getJobSeeker().getProfileId());
            d.setAppliedDate(b.getAppliedDate());
            d.setStatus(b.getStatus());
            d.setCoverLetter(b.getCoverLetter());
            d.setJobTitle(b.getJob().getTitle());
            d.setCompanyName(b.getJob().getEmployer().getCompanyName());
            d.setLocation(b.getJob().getLocation());
            List<Resume> resumes = resumeRepo.findByJobSeekerProfileId(
                    b.getJobSeeker().getProfileId());

            if(!resumes.isEmpty()){

                Resume r = resumes.get(0);

                d.setResumeId(r.getResumeId());
                d.setResumeFileName(r.getFileName());

            }
            d.setJobSeekerName(
                    b.getJobSeeker()
                            .getUser()
                            .getFullName()
            );
            d.setEmail(
                    b.getJobSeeker()
                            .getUser()
                            .getEmail()
            );

            d.setPhoneNumber(
                    b.getJobSeeker()
                            .getUser()
                            .getPhone()
            );

            d.setEducation(
                    b.getJobSeeker()
                            .getEducation()
            );

            d.setExperienceYears(
                    b.getJobSeeker()
                            .getExperienceYears()
            );

            d.setSkills(
                    b.getJobSeeker()
                            .getSkills()
            );

            list.add(d);
        }
        log.info("Applications found: {}", list.size());
        return list;
    }

    @Override
    public List<ApplicationDTO> findByJobSeekerProfileId(int profileId) {
        log.info("Fetching applications for Profile ID: {}", profileId);

        List<ApplicationDTO> list = new ArrayList<>();

        List<Application> c = repo.findByJobSeekerProfileId(profileId);

        for(Application b : c){

            ApplicationDTO d = new ApplicationDTO();

            d.setApplicationId(b.getApplicationId());
            d.setJobId(b.getJob().getJobId());
            d.setJobSeekerId(b.getJobSeeker().getProfileId());
            d.setAppliedDate(b.getAppliedDate());
            d.setStatus(b.getStatus());
            d.setCoverLetter(b.getCoverLetter());
            d.setJobTitle(b.getJob().getTitle());
            d.setCompanyName(b.getJob().getEmployer().getCompanyName());
            d.setLocation(b.getJob().getLocation());

            List<Resume> resumes = resumeRepo.findByJobSeekerProfileId(
                    b.getJobSeeker().getProfileId());

            if(!resumes.isEmpty()){

                Resume resume = resumes.get(0);

                d.setResumeId(resume.getResumeId());
                d.setResumeFileName(resume.getFileName());

            }
            d.setJobSeekerName(
                    b.getJobSeeker()
                            .getUser()
                            .getFullName()
            );
            d.setEmail(
                    b.getJobSeeker()
                            .getUser()
                            .getEmail()
            );

            d.setPhoneNumber(
                    b.getJobSeeker()
                            .getUser()
                            .getPhone()
            );

            d.setEducation(
                    b.getJobSeeker()
                            .getEducation()
            );

            d.setExperienceYears(
                    b.getJobSeeker()
                            .getExperienceYears()
            );

            d.setSkills(
                    b.getJobSeeker()
                            .getSkills()
            );


            list.add(d);

        }
        log.info("Applications found: {}", list.size());
        return list;

    }

    @Override
    public ApplicationDTO updateApplicationStatus(Long applicationId, String status) {
        log.info("Updating application status. Application ID: {}, New Status: {}",
                applicationId, status);
        Application app=repo.findById(applicationId).orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        app.setStatus(status);
        Application s=repo.save(app);
        log.info("Application status updated successfully.");
        NotificationDTO notification = new NotificationDTO();

        notification.setUserId(
                s.getJobSeeker()
                        .getUser()
                        .getUserId());

        notification.setTitle("Application Status Updated");

        notification.setMessage(
                "Your application for "
                        + s.getJob().getTitle()
                        + " has been "
                        + s.getStatus());

        notificationService.createNotification(notification);
        log.info("Notification sent to job seeker.");

        mailService.sendStatusMail(
                s.getJobSeeker().getUser().getEmail(),
                s.getJobSeeker().getUser().getFullName(),
                s.getJob().getTitle(),
                s.getStatus()
        );
        log.info("Status update email sent successfully.");
        ApplicationDTO dto=new ApplicationDTO();

        dto.setApplicationId(s.getApplicationId());
        dto.setJobId(s.getJob().getJobId());
        dto.setJobSeekerId(s.getJobSeeker().getProfileId());
        dto.setAppliedDate(s.getAppliedDate());
        dto.setStatus(s.getStatus());
        dto.setCoverLetter(s.getCoverLetter());
        dto.setJobTitle(s.getJob().getTitle());
        dto.setCompanyName(s.getJob().getEmployer().getCompanyName());
        dto.setLocation(s.getJob().getLocation());
        log.info("Application status update completed.");
        return dto;
    }

    @Override
    public void deleteApplication(Long applicationId) {
        log.info("Deleting application. Application ID: {}", applicationId);
        Application a = repo.findById(applicationId).orElseThrow(() ->
                        new ResourceNotFoundException("ID not found"));
        repo.deleteById(applicationId);
        log.info("Application deleted successfully.");
    }
}
