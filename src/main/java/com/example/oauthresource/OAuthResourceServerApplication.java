package com.example.oauthresource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@SpringBootApplication
public class OAuthResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuthResourceServerApplication.class, args);
	}
}
