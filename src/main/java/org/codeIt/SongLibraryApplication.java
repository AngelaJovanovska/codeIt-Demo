package com.project.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SongLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SongLibraryApplication.class, args);
	}

	@GetMapping()
	public String hello() {
		return "hii world";
	}
}