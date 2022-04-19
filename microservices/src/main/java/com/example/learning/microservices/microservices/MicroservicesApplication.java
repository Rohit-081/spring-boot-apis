package com.example.learning.microservices.microservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MicroservicesApplication {
    private static final Logger logger = LoggerFactory.getLogger(MicroservicesApplication.class);

	public static void main(String[] args) {
		logger.info("This is info message");
		logger.warn("This is warn message");
		logger.error("This is error message");
		SpringApplication.run(MicroservicesApplication.class, args);
	}
	@RequestMapping("/hello")
	public String hello() { return "Hello World"; }

}
