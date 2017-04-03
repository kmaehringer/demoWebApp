package de.websel.demo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@Autowired
	private OAuth2RestOperations restTemplate;

	@Value("${config.oauth2.resourceURI}")
	private String resourceURI;

	@RequestMapping("/")
	public String index(Model model) {
		String text = restTemplate.getForObject("http://localhost:9090/demo/foo", String.class);
		model.addAttribute("message", text);
		return text;
	}
}
