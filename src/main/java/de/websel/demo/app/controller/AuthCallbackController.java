package de.websel.demo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthCallbackController {

	@RequestMapping("/auth")
	public void authCallback() {
		return;
	}
}
