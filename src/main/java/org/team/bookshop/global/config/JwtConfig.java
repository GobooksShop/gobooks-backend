package org.team.bookshop.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfig {

    @Value("${jwt.secret.key}")
    private String secretKey;

}
