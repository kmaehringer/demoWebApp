package de.websel.demo.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthCallbackController {
	Logger log = LoggerFactory.getLogger(AuthCallbackController.class);

	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public String authCallback(Model model) {
		log.info("callback request arrived");
		return "auth";
	}

	@RequestMapping(value = "/remoteLogin", method = RequestMethod.GET)
	public String remoteLogin(Model model) {
		model.addAttribute("code_url", "http://localhost:9000/oauth/authorize");
		model.addAttribute("client_id", "web_app");
		model.addAttribute("client_secret", "web_app_secret");
		model.addAttribute("redirect_uri", "http://localhost:8080/auth");
		model.addAttribute("response_type", "code");
		return "remoteLogin";
	}
}
