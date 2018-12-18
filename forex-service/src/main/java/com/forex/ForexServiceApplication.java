package com.forex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ForexServiceApplication {
	@Autowired
	private static Environment environment;
	public static void main(String[] args) {
		SpringApplication.run(ForexServiceApplication.class, args);

		System.out.println("Welcome to Springboot application!!");

		System.out.println("checking if Dev-tools working");
		System.out.println("Yes Dev-Tools works properly");
		
		System.out.println(environment);

	}
}
