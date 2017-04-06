package de.websel.demo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class HomeController {

	@Autowired
	private OAuth2RestOperations oauth2RestTemplate;

	@Value("${security.oauth2.resource.user-info-uri}")
	private String resourceURI;

	@RequestMapping("/")
	public JsonNode home() {
		return oauth2RestTemplate.getForObject(resourceURI, JsonNode.class);
	}
}
