package com.hexaware.careercrafter.controller;

import com.hexaware.careercrafter.dto.*;
import com.hexaware.careercrafter.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService service;

    @PostMapping("/create")
    public UserRegisterDTO createUser(@Valid  @RequestBody UserDTO dto)
    {
        return service.registerUser(dto);
    }

    @GetMapping("/getById/{id}")
    UserRegisterDTO getUserById(@PathVariable int id)
    {
        return service.getUserById(id);
    }

    @GetMapping("/getAll")
   public List< UserRegisterDTO> getAllUsers()
    {
        return service.getAllUsers();
    }

    @PutMapping("/update/{id}")
   public  UserRegisterDTO updateUser(@PathVariable int id,@Valid  @RequestBody UserDTO dto)
    {
       return service.updateUser(id,dto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id)
    {
         service.deleteUser(id);
         return "Deleted Sucessfully";
    }
    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginDTO dto)
    {
        return service.login(dto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @Valid @RequestBody ForgotPasswordDTO dto) {

        service.forgotPassword(dto);

        return ResponseEntity.ok("OTP sent successfully");

    }
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @Valid @RequestBody VerifyOtpDTO dto){

        service.verifyOtp(dto);

        return ResponseEntity.ok("OTP verified successfully");

    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody ResetPasswordDTO dto){

        service.resetPassword(dto);

        return ResponseEntity.ok("Password reset successfully");

    }

}
