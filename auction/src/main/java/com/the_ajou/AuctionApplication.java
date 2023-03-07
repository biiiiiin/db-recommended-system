package com.the_ajou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Date;
import java.util.TimeZone;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

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
