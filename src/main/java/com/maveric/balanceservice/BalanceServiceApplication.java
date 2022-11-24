package com.maveric.balanceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BalanceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BalanceServiceApplication.class, args);
	}

}
