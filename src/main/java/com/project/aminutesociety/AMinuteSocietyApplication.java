package com.project.aminutesociety;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AMinuteSocietyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AMinuteSocietyApplication.class, args);
	}

}
