package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthenticationDemoApplication {

	@Autowired
	private UserRepo userRepo;

	@PostConstruct
	public void initUsers() {
		List<User> users = Stream.of(
				new User(101, "sandeep", "123456", "sandeepmisal4@gmail.com"),
				new User(102, "deepak", "102030", "deepakmisal4@gmail.com"),
				new User(103, "prashant", "654321", "deepakmisal4@gmail.com")
		).collect(Collectors.toList());
		userRepo.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationDemoApplication.class, args);
	}

}
