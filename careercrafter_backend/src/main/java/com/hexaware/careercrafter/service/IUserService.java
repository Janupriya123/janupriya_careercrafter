package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.*;

import java.util.List;

public interface IUserService {
    UserRegisterDTO registerUser(UserDTO dto);

    UserRegisterDTO getUserById(int id);

    List<UserRegisterDTO> getAllUsers();

    UserRegisterDTO updateUser(int id, UserDTO dto)  throws RuntimeException;

    void deleteUser(int id);

    LoginResponseDTO login(LoginDTO dto);
    void forgotPassword(ForgotPasswordDTO dto);

    void verifyOtp(VerifyOtpDTO dto);

    void resetPassword(ResetPasswordDTO dto);
}
