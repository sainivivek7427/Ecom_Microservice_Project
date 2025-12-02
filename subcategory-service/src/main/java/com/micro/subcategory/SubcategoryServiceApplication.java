package com.micro.subcategory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.micro.subcategory.communicate")
public class SubcategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubcategoryServiceApplication.class, args);
	}

}
