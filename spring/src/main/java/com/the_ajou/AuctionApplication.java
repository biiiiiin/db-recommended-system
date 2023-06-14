package com.the_ajou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class AuctionApplication {

	@PostConstruct
	public void setTimeZone(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		System.out.println("[QWER] 현재 시간 : " + new Date());
	}


	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}
}
