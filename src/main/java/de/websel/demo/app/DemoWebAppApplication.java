package de.websel.demo.app;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
@EnableOAuth2Sso
public class DemoWebAppApplication extends WebSecurityConfigurerAdapter {

	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
		return new OAuth2RestTemplate(resource, context);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoWebAppApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.antMatcher("/**").authorizeRequests()
			.antMatchers("/", "/login**", "/webjars/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.logout().logoutSuccessUrl("/").permitAll()
			.and()
			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		;
		// @formatter:on
	}

	@RequestMapping("/me")
	@ResponseBody
	public Principal user(Principal principal) {
		return principal;
	}

	@RequestMapping("/unauthenticated")
	public String unauthenticated() {
		return "redirect:/?error=true";
	}

	@Configuration
	protected static class ServletCustomizer {
		@Bean
		public EmbeddedServletContainerCustomizer customizer() {
			return container -> {
				container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/unauthenticated"));
			};
		}
	}
}
