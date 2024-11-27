package com.api.anime.anime_library_api.util.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll() // Permite acesso aos endpoints públicos sem autenticação
                        .requestMatchers("/keycloak/**").permitAll() // Permite acesso aos endpoints do Keycloak sem autenticação (login, token, etc)
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Restrição para admins
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Restrição para user ou admin
                        .anyRequest().authenticated() // Requer autenticação para outras requisições
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()) // Conversor de roles
                        )
                );

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter()); // Ajuste aqui para não passar SimpleAuthorityMapper
        return converter;
    }
}
