package de.websel.demo.app;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class DemoWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoWebAppApplication.class, args);
	}

	@RequestMapping("/me")
	public Principal user(Principal principal) {
		return principal;
	}
}
