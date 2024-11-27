package com.api.anime.anime_library_api.util.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Extrai o mapa de claims 'realm_access'
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");

        // Extrai a lista de roles dentro de 'realm_access'
        Collection<String> roles = (realmAccess != null && realmAccess.containsKey("roles"))
                ? (Collection<String>) realmAccess.get("roles") // Realiza o cast de maneira segura
                : List.of(); // Caso não exista 'roles', retorna uma lista vazia

        // Mapeia cada role para uma autoridade com o prefixo 'ROLE_'
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())) // Converte as roles para o formato esperado pelo Spring Security
                .collect(Collectors.toList());
    }
}
