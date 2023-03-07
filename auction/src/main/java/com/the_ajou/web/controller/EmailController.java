package com.the_ajou.web.controller;

import com.the_ajou.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class EmailController {
    private final EmailService emailService;

    @CrossOrigin
    @GetMapping("/api/auth/emailValidation")
    public String sendEmail(String email){
        return emailService.sendMail(email);
    }
}
