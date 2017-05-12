package de.websel.demo.app.controller;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

@Controller
public class HomeController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private KeycloakRestTemplate restTemplate;

	@Value("${demo.resource.foo}")
	private String demoResource;

	@RequestMapping("/")
	public String index(Principal user, Model model) {
		String name = "World";
		if (user != null && user.getName() != null) {
			name = user.getName();
		}
		model.addAttribute("name", name);
		return "index";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/home")
	public String home(Principal principal, Model model) {
		KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) principal;
		String name = "";
		try {
			name = ((KeycloakPrincipal) token.getPrincipal()).getKeycloakSecurityContext().getToken().getName();
		} catch (Exception e) {
			name = "n/a";
		}
		model.addAttribute("name", name);
		String readResult = "";
		String writeResult = "";
		try {
			ResponseEntity<String> readResponse = restTemplate.getForEntity(demoResource, String.class);
			if (readResponse.getStatusCode() == HttpStatus.OK) {
				readResult = readResponse.getBody();
			}
		} catch (RestClientException e) {
			readResult = "reading not possible";
		}
		model.addAttribute("readText", readResult);
		try {
			ResponseEntity<String> writeResponse = restTemplate.postForEntity(demoResource, null, String.class);
			if (writeResponse.getStatusCode() == HttpStatus.OK) {
				writeResult = writeResponse.getBody();
			}
		} catch (RestClientException e) {
			writeResult = "writing not possible";
		}
		model.addAttribute("writeText", writeResult);
		return "home";
	}

	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public String exit() throws ServletException {
		request.logout();
		return "exit";
	}
}
