package de.websel.demo.app.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
		return new OAuth2RestTemplate(resource, context);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.antMatcher("/**").authorizeRequests()
			.antMatchers("/").permitAll()
			.anyRequest().authenticated()
			.and()
			.logout().logoutUrl("/exit").deleteCookies("JSESSIONID", "XSRF-TOKEN").logoutSuccessUrl("/").permitAll()
			.and()
			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		// @formatter:on
	}
}
