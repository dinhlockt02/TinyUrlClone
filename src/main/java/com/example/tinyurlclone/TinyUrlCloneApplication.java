package com.example.tinyurlclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
public class TinyUrlCloneApplication {


	public static void main(String[] args) {
		SpringApplication.run(TinyUrlCloneApplication.class, args);
	}

}
