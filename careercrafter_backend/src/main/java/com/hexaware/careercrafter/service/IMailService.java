package com.hexaware.careercrafter.service;

public interface IMailService {

    void sendOtp(String email, String otp);
    void sendApplicationMail(String email,String companyName,String jobTitle);
    void sendStatusMail(String email,String name,String jobTitle,String status);
}
