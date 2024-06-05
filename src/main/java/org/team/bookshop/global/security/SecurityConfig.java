package org.team.bookshop.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.filter.CorsFilter;
import org.team.bookshop.domain.user.service.UserService;
import org.team.bookshop.global.error.exception.SecurityConfigurationException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private UserService userService;
	private JwtTokenizer jwtTokenizer;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws SecurityConfigurationException {
		try{
			http
					.csrf(AbstractHttpConfigurer::disable)
					.sessionManagement(session -> session
							.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					)
					.authorizeHttpRequests((authorizeRequests) ->
							authorizeRequests
									.requestMatchers(HttpMethod.GET, "/").permitAll()
									.requestMatchers(HttpMethod.POST, "/api/auth/").permitAll()
									.requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//									.requestMatchers("/api/admin/**").hasAnyAuthority(UserRole.ADMIN.getRole())
//									.requestMatchers("/api/users/**").hasAnyAuthority(UserRole.ADMIN.getRole(), UserRole.USER.getRole())
									.anyRequest().permitAll() // jwt 완성 전까지는 다 접근 가능하게 임시로 세팅
					)
					.addFilterBefore(new JwtCustomFilter(userService, jwtTokenizer), UsernamePasswordAuthenticationFilter.class)
					.formLogin(AbstractHttpConfigurer::disable)
					.httpBasic(AbstractHttpConfigurer::disable);
			return http.build();
		}catch (Exception e) {
			throw new SecurityConfigurationException("Security configuration failed");
		}
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);

		configuration.addAllowedOrigin("http://localhost:3000");
		configuration.addAllowedHeader("*");

		configuration.addAllowedMethod("GET");
		configuration.addAllowedMethod("POST");
		configuration.addAllowedMethod("PUT");
		configuration.addAllowedMethod("DELETE");
		configuration.addAllowedMethod("FETCH");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public CorsFilter corsFilter() {
		return new CorsFilter(corsConfigurationSource());
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
