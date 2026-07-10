package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.EmployerDTO;
import com.hexaware.careercrafter.entity.Employer;
import com.hexaware.careercrafter.entity.User;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.EmployerRepository;
import com.hexaware.careercrafter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployerImplService implements IEmployerService {
    private static final Logger logger = LoggerFactory.getLogger(EmployerImplService.class);

    @Autowired
    private UserRepository ur;
    @Autowired
    private EmployerRepository repo;
    @Override
    public EmployerDTO addEmployer(EmployerDTO dto) {
        logger.info("Adding employer for userId: {}",dto.getUserId());
        User user=ur.findById(dto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Employer e=new Employer();

        e.setUser(user);
        e.setCompanyName(dto.getCompanyName());
        e.setWebsite(dto.getWebsite());
        e.setIndustry(dto.getIndustry());
        e.setCompanySize(dto.getCompanySize());
        e.setCompanyDescription(dto.getCompanyDescription());

        Employer s=repo.save(e);
        logger.info("Employer created successfully with employerId: {}", s.getEmployerId());
        return convertToDTO(s);
    }

    @Override
    public EmployerDTO getEmployerById(int employerId) {
        logger.info("Fetching employer with id: {}", employerId);
        Employer e=repo.findById(employerId).orElseThrow(() -> new ResourceNotFoundException("Employer not found"));
        logger.info("Employer retrieved successfully with id: {}", employerId);
        return convertToDTO(e);

    }

    @Override
    public List<EmployerDTO> getAllEmployers() {
        logger.info("Fetching all employers");
        List<EmployerDTO> list=new ArrayList<>();
        List<Employer> employers=repo.findAll();

        for(Employer e:employers)
        {
            list.add(convertToDTO(e));
        }

        logger.info("Total employers found: {}", employers.size());
        return list;
    }

    @Override
    public EmployerDTO updateEmployer(int employerId,EmployerDTO dto)
    {
        logger.info("Updating employer with id: {}", employerId);
        Employer e=repo.findById(employerId).orElseThrow(() -> new ResourceNotFoundException("Employer not found"));
        e.setCompanyName(dto.getCompanyName());
        e.setWebsite(dto.getWebsite());
        e.setIndustry(dto.getIndustry());
        e.setCompanySize(dto.getCompanySize());
        e.setCompanyDescription(dto.getCompanyDescription());

        Employer s=repo.save(e);
        logger.info("Employer updated successfully with id: {}", employerId);
        return convertToDTO(s);
    }

    @Override
    public void deleteEmployer(int employerId) {
        logger.info("Deleting employer with id: {}", employerId);
        Employer e=repo.findById(employerId).orElseThrow(() -> new ResourceNotFoundException("ID not found"));
        repo.deleteById(employerId);
        logger.info("Employer deleted successfully with id: {}", employerId);

    }

    @Override
    public EmployerDTO getEmployerByUserId(int userId) {
        logger.info("Fetching employer for userId: {}", userId);
        Employer e=repo.findByUserUserId(userId);

        if(e==null)
        {
            throw new ResourceNotFoundException("User not found");
        }

        logger.info("Employer found for userId: {}", userId);
        return convertToDTO(e);
    }
    private EmployerDTO convertToDTO(Employer e)
    {
        EmployerDTO d=new EmployerDTO();

        d.setEmployerId(e.getEmployerId());
        d.setUserId(e.getUser().getUserId());
        d.setCompanyName(e.getCompanyName());
        d.setWebsite(e.getWebsite());
        d.setIndustry(e.getIndustry());
        d.setCompanySize(e.getCompanySize());
        d.setCompanyDescription(e.getCompanyDescription());

        return d;
    }
}
