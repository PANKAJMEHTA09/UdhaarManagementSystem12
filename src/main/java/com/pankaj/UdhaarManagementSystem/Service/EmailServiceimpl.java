//package com.pankaj.UdhaarManagementSystem.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
//public class EmailServiceimpl implements  EmailService{
//
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Override
//    public  void sendVerificationEmail(String to, String token){
//        String subject = "Email Verification";
//        String verificationUrl = "http://localhost:8080/verify?token=" + token;
//        String message = "Please click the following link to verify your email: " + verificationUrl;
//
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(to);
//        mailMessage.setSubject(subject);
//        mailMessage.setText(message);
//
//        mailSender.send(mailMessage);
//
//    }
//
//}
