package com.hexaware.careercrafter.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MailServiceImpl implements IMailService {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Async
    public void sendOtp(String email, String otp) {
        logger.info("Sending OTP mail to {}", email);
        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("janupriyae@gmail.com");
            message.setTo(email);
            message.setSubject("CareerCrafter Password Reset OTP");
            message.setText("Your OTP is : " + otp);

            mailSender.send(message);

            logger.info("OTP mail sent successfully to {}", email);

        } catch (Exception e) {


            logger.error("Failed to send OTP mail to {}", email, e);
        }
    }
    @Override
    @Async
    public void sendApplicationMail(String email,String companyName,String jobTitle){
        logger.info("Sending job application notification to {}", email);
        SimpleMailMessage message=new SimpleMailMessage();

        message.setFrom("janupriyae@gmail.com");
        message.setTo(email);
        message.setSubject("New Job Application Received");

        message.setText(
                "Hello "+companyName+",\n\n"+
                        "A new candidate has applied for your job.\n\n"+
                        "Job Title : "+jobTitle+"\n\n"+
                        "Please login to CareerCrafter to review the application.\n\n"+
                        "Thank you,\n"+
                        "CareerCrafter"
        );

        mailSender.send(message);
        logger.info("mail sent successfully to {}",email);

    }
    @Override
    @Async
    public void sendStatusMail(String email,String name,String jobTitle,String status){
        logger.info("Sending application status mail to {}", email);

        SimpleMailMessage message=new SimpleMailMessage();

        message.setFrom("janupriyae@gmail.com");

        message.setTo(email);

        message.setSubject("Application Status Updated");

        message.setText(
                "Hello "+name+",\n\n"+
                        "Your application status has been updated.\n\n"+
                        "Job Title : "+jobTitle+"\n"+
                        "Status : "+status+"\n\n"+
                        "Please login to CareerCrafter to view more details.\n\n"+
                        "Thank you,\n"+
                        "CareerCrafter"
        );

        mailSender.send(message);
        logger.info("Application status mail sent successfully to {}", email);


    }
}