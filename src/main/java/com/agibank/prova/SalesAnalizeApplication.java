package com.agibank.prova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SalesAnalizeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesAnalizeApplication.class, args);
	}

}
