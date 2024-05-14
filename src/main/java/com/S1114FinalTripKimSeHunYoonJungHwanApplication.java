package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class S1114FinalTripKimSeHunYoonJungHwanApplication {

	public static void main(String[] args) {
		SpringApplication.run(S1114FinalTripKimSeHunYoonJungHwanApplication.class, args);
	}

}
