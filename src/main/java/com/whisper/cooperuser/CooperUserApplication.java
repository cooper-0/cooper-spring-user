package com.whisper.cooperuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CooperUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(CooperUserApplication.class, args);
	}

}