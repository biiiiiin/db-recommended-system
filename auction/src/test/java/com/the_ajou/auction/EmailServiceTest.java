package com.the_ajou.auction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@WebAppConfiguration
class EmailServiceTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Transactional
    @Rollback
    @Test
    void sendMail(){
        String code = UUID.randomUUID().toString().substring(0,6);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("ksfan2003@gmail.com");
        simpleMailMessage.setSubject("Auction 이메일 인증");
        simpleMailMessage.setText("이메일 인증 화면에 하단 텍스트를 정확히 입력해 주세요!\n인증코드 : "+code);
        //javaMailSender.send(simpleMailMessage);

        Assertions.assertThat(code).isNotEmpty();
    }
}
