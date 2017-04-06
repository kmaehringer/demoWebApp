package de.websel.demo.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class HomeController {

	@Autowired
	private OAuth2RestOperations oauth2RestTemplate;

	@Value("${security.oauth2.resource.user-info-uri}")
	private String resourceURI;

	@RequestMapping("/")
	public String index() {
		return "home";
	}

	@RequestMapping("/home")
	@ResponseBody
	public JsonNode home() {
		return oauth2RestTemplate.getForObject(resourceURI, JsonNode.class);
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}
		return "home";
	}
}
