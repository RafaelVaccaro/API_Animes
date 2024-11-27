package com.api.anime.anime_library_api.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${keycloak.server.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.client-id}")
    private String clientId;

    private final RestTemplate restTemplate;

    public ResponseEntity<Map<String, Object>> login(Map<String, String> userCredentials) {
        String username = userCredentials.get("username");
        String password = userCredentials.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username e password são obrigatórios."));
        }

        String tokenUrl = keycloakUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";
        String body = "grant_type=password&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&username=" + username +
                "&password=" + password +
                "&scope=openid";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);

                String accessToken = (String) responseBody.get("access_token");

                if (accessToken != null) {
                    return ResponseEntity.ok(Map.of("access_token", accessToken));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token de acesso não encontrado."));
                }
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(Map.of("error", "Falha na autenticação."));
            }

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getResponseBodyAsString()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erro ao processar resposta do Keycloak."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erro interno do servidor."));
        }
    }
}



