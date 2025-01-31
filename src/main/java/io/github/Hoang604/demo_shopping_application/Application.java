package io.github.Hoang604.demo_shopping_application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.github.Hoang604.demo_shopping_application.model.User;
import io.github.Hoang604.demo_shopping_application.repository.UserRepository;

@SpringBootApplication
public class Application {
	private final UserRepository userRepository;

	public Application(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner createUser() {
		return (args) -> {
			String username = "admin";
			String password = "admin1234";
			String role = "ADMIN";

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(password);

			if (userRepository.findByUsername(username) == null) {
				User user = new User();
				user.setUsername(username);
				user.setPassword(encodedPassword);
				user.setRole(role);
				userRepository.save(user);
				System.out.println("User created with username: " + username);
				System.out.println("Password: " + password);
			} else {
				System.out.println("User exists for testing with username: " + username);
				System.out.println("Password: " + password);
			}
		};
	}
}
