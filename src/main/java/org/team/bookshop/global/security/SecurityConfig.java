package org.team.bookshop.global.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.authorizeHttpRequests((authorizeRequests) ->
				authorizeRequests
					.requestMatchers("/", "/login","/join", "/pro").permitAll()
					// .requestMatchers("/admin/**").hasRole(Member.Role.ADMIN.name())
					// .requestMatchers("/my-page/**").hasAnyRole(Member.Role.USER.name(), Member.Role.ADMIN.name())
					.anyRequest().permitAll()
			)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.logout(logout -> logout
				.logoutSuccessUrl("/")
			)
			.exceptionHandling(exceptionHandling ->
				exceptionHandling.accessDeniedHandler(accessDeniedHandler())
			);

		return http.build();
	}
	private AccessDeniedHandler accessDeniedHandler() {
		return (request, response, accessDeniedException) -> {
			response.sendRedirect("/access-denied");
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
	public WebSecurityCustomizer h2ConsoleCustomizer() {
		return web -> web.ignoring()
				.requestMatchers(PathRequest.toH2Console());
	}

	@Bean
	@ConditionalOnProperty(name = "springdoc.swagger-ui.enabled", havingValue = "true")
	public WebSecurityCustomizer swaggerCustomizer() {
		return web -> web.ignoring()
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
	}

}
