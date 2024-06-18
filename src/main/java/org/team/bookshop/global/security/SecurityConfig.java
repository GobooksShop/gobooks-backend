package org.team.bookshop.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.global.config.JwtConfig;
import org.team.bookshop.global.config.WebConfig;
import org.team.bookshop.global.error.exception.SecurityConfigurationException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthSuccessHandler customAuthSuccessHandler;
    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;
    private final WebConfig webConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws SecurityConfigurationException {
        try {
            http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                            .requestMatchers(HttpMethod.GET, "/").permitAll()
                            .requestMatchers(HttpMethod.OPTIONS, "/**")
                            .permitAll() //preflight 요청을 처리하기위해 사용
                            .requestMatchers("/api/auth/**").permitAll()
//                            .requestMatchers(HttpMethod.POST,"/api/users/**").permitAll()
//                            .requestMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
//                            .requestMatchers(HttpMethod.GET,"/api/products/**").permitAll()
                            .requestMatchers("/api/users/**").permitAll()
                            .requestMatchers("/api/categories/**").permitAll()
                            .requestMatchers("/api/products/**").permitAll()
                            .requestMatchers("/api/admin/**").permitAll()
                            .requestMatchers("/login/oauth2/**").permitAll()
                            .requestMatchers("/api/cart/**").permitAll()
                            .requestMatchers("/api/orders/**").permitAll()
                            .requestMatchers("/api/delivery/**").permitAll()
                            .requestMatchers("/api/payment/**").permitAll()
                            .requestMatchers("/image/**").permitAll()
                            .anyRequest().authenticated()

                )
                .oauth2Login(oauth2Login ->
                    oauth2Login
                        .successHandler(customAuthSuccessHandler)
                )
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(customAuthSuccessHandler)
                    .invalidateHttpSession(true)
                    .deleteCookies(JwtConfig.REFRESH_JWT_COOKIE_NAME)
                )
                .addFilterBefore(new JwtCustomFilter(userRepository, jwtTokenizer, webConfig),
                    UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
            return http.build();
        } catch (Exception e) {
            throw new SecurityConfigurationException("Security configuration failed");
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);

        configuration.addAllowedOrigin(webConfig.getBaseUrl());
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("OPTIONS"); //preflight 요청을 처리하기위해 사용
        configuration.addExposedHeader("Authorization");

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
