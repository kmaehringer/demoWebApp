package de.websel.demo.app.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class HomeController {

	@Autowired
	private OAuth2RestOperations oauth2RestTemplate;

	@Value("${security.oauth2.resource.user-info-uri}")
	private String resourceURI;

	@RequestMapping("/")
	public String index(Principal user, Model model) {
		String name = "World";
		if (user != null && user.getName() != null) {
			name = user.getName();
		}
		model.addAttribute("name", name);
		return "index";
	}

	@RequestMapping("/home")
	public String home(Model model) {
		JsonNode result = oauth2RestTemplate.getForObject(resourceURI, JsonNode.class);
		String name = result.get("name").asText();
		model.addAttribute("name", name);
		return "home";
	}

	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public String exit(HttpServletRequest request) {
		try {
			request.logout();
		} catch (Exception e) {
		}
		return "exit";
	}
}
