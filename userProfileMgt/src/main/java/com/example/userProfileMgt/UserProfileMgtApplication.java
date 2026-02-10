package com.example.userProfileMgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserProfileMgtApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserProfileMgtApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner demo(com.example.userProfileMgt.repository.UserProfileRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new com.example.userProfileMgt.model.UserProfile(null, "john_doe", "john@example.com", "John Doe", 30, "USA", "Java Developer", true));
			repository.save(new com.example.userProfileMgt.model.UserProfile(null, "jane_doe", "jane@example.com", "Jane Doe", 25, "UK", "Python Developer", false));
			repository.save(new com.example.userProfileMgt.model.UserProfile(null, "bob_smith", "bob@example.com", "Bob Smith", 40, "Canada", "Manager", true));

			// fetch all customers
			System.out.println("Users found with findAll():");
			System.out.println("-------------------------------");
			for (com.example.userProfileMgt.model.UserProfile customer : repository.findAll()) {
				System.out.println(customer.toString());
			}
			System.out.println("");
		};
	}
}
