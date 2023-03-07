package com.the_ajou.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Transactional
    public String sendMail(String email){
        String code = UUID.randomUUID().toString().substring(0,6);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Auction 이메일 인증");
        simpleMailMessage.setText("이메일 인증 화면에 하단 텍스트를 정확히 입력해 주세요!\n인증코드 : "+code);
        javaMailSender.send(simpleMailMessage);
        return code;
    }
}
