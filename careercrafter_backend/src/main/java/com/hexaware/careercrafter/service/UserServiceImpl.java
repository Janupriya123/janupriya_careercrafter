package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.*;
import com.hexaware.careercrafter.entity.PasswordReset;
import com.hexaware.careercrafter.entity.User;
import com.hexaware.careercrafter.exceptionhandling.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.PasswordResetRepository;
import com.hexaware.careercrafter.repository.UserRepository;
import com.hexaware.careercrafter.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService
{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordResetRepository passwordResetRepo;

    @Autowired
    private IMailService mailService;


    @Override
    public UserRegisterDTO registerUser(UserDTO dto) {
        logger.info("Registering user with email: {}", dto.getEmail());
        User existingUser=repo.findByEmail(dto.getEmail());

        if (existingUser!=null) {
            throw new RuntimeException("Email already exists");
        }
        User u=new User();
        u.setFullName(dto.getFullName());
        u.setEmail(dto.getEmail());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setPhone(dto.getPhone());
        u.setRole(dto.getRole());
        u.setActive(dto.getActive());
        User dt=repo.save(u);
        UserRegisterDTO d=new UserRegisterDTO();
        d.setUserId(dt.getUserId());
        d.setFullName(dt.getFullName());
        d.setEmail(dt.getEmail());
        d.setPhone(dt.getPhone());
        d.setRole(dt.getRole());
        logger.info("User registered successfully with userId: {}", dt.getUserId());
        return d;
    }

    @Override
    public UserRegisterDTO  getUserById(int id)
    {
        logger.info("Fetching user with id: {}", id);
        User dt=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("ID not exist"));

        UserRegisterDTO d=new UserRegisterDTO();
        d.setUserId(dt.getUserId());
        d.setFullName(dt.getFullName());
        d.setEmail(dt.getEmail());
        d.setPhone(dt.getPhone());
        d.setRole(dt.getRole());
        logger.info("User retrieved successfully with id: {}", id);
        return d;
    }

    @Override
    public List<UserRegisterDTO> getAllUsers()

    {
        logger.info("Fetching all users");
        List<User> users = repo.findAll();

        List<UserRegisterDTO> dtos =
                new ArrayList<>();

        for(User user : users)
        {
            UserRegisterDTO dto=new UserRegisterDTO();
            dto.setUserId(user.getUserId());
            dto.setFullName(user.getFullName());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setRole(user.getRole());

            dtos.add(dto);
        }

        logger.info("Total users found: {}", users.size());
        return dtos;
    }
    @Override
    public UserRegisterDTO updateUser(int id, UserDTO dto)
    {
        logger.info("Updating user with id: {}", id);
        User u=repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        u.setFullName(dto.getFullName());
        u.setEmail(dto.getEmail());
        u.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()));
        u.setPhone(dto.getPhone());
        u.setRole(dto.getRole());
        u.setActive(dto.getActive());
        User dt=repo.save(u);
        UserRegisterDTO d=new UserRegisterDTO();
        d.setUserId(dt.getUserId());
        d.setFullName(dt.getFullName());
        d.setEmail(dt.getEmail());
        d.setPhone(dt.getPhone());
        d.setRole(dt.getRole());
        logger.info("User updated successfully with id: {}", id);
        return d;
    }

    @Override
    public void deleteUser(int id)
    {
        logger.info("Deleting user with id: {}", id);
        User u=repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID not exist"));
        repo.deleteById(id);
        logger.info("User deleted successfully with id: {}", id);
    }

    @Override
    public LoginResponseDTO login(LoginDTO dto)
    {
        logger.info("Login attempt for email: {}", dto.getEmail());
        User u = repo.findByEmail(dto.getEmail());

        if (u == null) {
            logger.error("User not found with email: {}", dto.getEmail());
            throw new ResourceNotFoundException("User not found");
        }

        boolean valid = passwordEncoder.matches(dto.getPassword(), u.getPassword());

        if (!valid) {
            logger.warn("Invalid login attempt for email: {}", dto.getEmail());
            throw new ResourceNotFoundException("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(u.getEmail());
        logger.info("User logged in successfully. UserId: {}", u.getUserId());
        return new LoginResponseDTO(
                token,
                u.getUserId(),
                u.getRole(),
                u.getFullName()
        );
    }
    @Override
    public void forgotPassword(ForgotPasswordDTO dto) {
        logger.info("Forgot password request for email: {}", dto.getEmail());
        User user = repo.findByEmail(dto.getEmail());

        if(user == null){
            logger.error("Email not registered: {}", dto.getEmail());
            throw new ResourceNotFoundException("Email not registered");
        }

        String otp = String.valueOf(
                (int)(Math.random() * 900000) + 100000
        );

        PasswordReset reset = passwordResetRepo
                .findByEmail(dto.getEmail())
                .orElse(new PasswordReset());

        reset.setEmail(dto.getEmail());
        reset.setOtp(otp);
        reset.setVerified(false);
        reset.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        passwordResetRepo.save(reset);


        mailService.sendOtp(dto.getEmail(), otp);

        logger.info("OTP generated and sent to email: {}", dto.getEmail());
    }
    @Override
    public void verifyOtp(VerifyOtpDTO dto) {
        logger.info("Verifying OTP for email: {}", dto.getEmail());
        PasswordReset reset = passwordResetRepo
                .findByEmailAndOtp(dto.getEmail(), dto.getOtp())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid OTP"));

        if(reset.getExpiryTime().isBefore(LocalDateTime.now())){
            logger.warn("OTP expired for email: {}", dto.getEmail());
            throw new RuntimeException("OTP Expired");
        }

        reset.setVerified(true);

        passwordResetRepo.save(reset);
        logger.info("OTP verified successfully for email: {}", dto.getEmail());
    }
    @Override
    public void resetPassword(ResetPasswordDTO dto) {
        logger.info("Resetting password for email: {}", dto.getEmail());
        PasswordReset reset = passwordResetRepo
                .findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("OTP verification required"));

        if(!reset.isVerified()){
            logger.warn("Password reset attempted without OTP verification for email: {}", dto.getEmail());
            throw new RuntimeException("OTP not verified");
        }

        User user = repo.findByEmail(dto.getEmail());

        if(user == null){
            logger.error("User not found with email: {}", dto.getEmail());
            throw new ResourceNotFoundException("User not found");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        repo.save(user);

        passwordResetRepo.delete(reset);

        logger.info("Password reset successfully for email: {}", dto.getEmail());
    }

}
