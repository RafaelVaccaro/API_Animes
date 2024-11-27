package com.api.anime.anime_library_api.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/keycloak")
@Data
public class KeycloakController {

    @Value("${keycloak.server.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.client-id}")
    private String clientId;

    @PostConstruct
    public void init() {
        System.out.println("Keycloak Server URL: " + keycloakUrl);
        System.out.println("Keycloak Realm: " + keycloakRealm);
    }

    @GetMapping
    public ResponseEntity<String> getToken() {
        // URL de token do Keycloak
        String tokenUrl = keycloakUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";

        // Criação do RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Configuração dos parâmetros para enviar na requisição POST
        String body = "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;

        // Cabeçalhos da requisição
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Configuração da requisição
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        // Envio da requisição POST para o Keycloak
        return restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, String.class);
    }



        private static final Logger log = LoggerFactory.getLogger(KeycloakController.class);

        @PostMapping("/login")
        public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> userCredentials) {
            log.info("Requisição recebida para login: {}", userCredentials);

            String username = userCredentials.get("username");
            String password = userCredentials.get("password");

            if (username == null || password == null) {
                log.error("Credenciais inválidas: username ou password ausentes.");
                return ResponseEntity.badRequest().body(Map.of("error", "Username e password são obrigatórios."));
            }

            String tokenUrl = keycloakUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";
            log.info("URL do Keycloak para autenticação: {}", tokenUrl);

            RestTemplate restTemplate = new RestTemplate();

            // Configurando os parâmetros da requisição
            String body = "grant_type=password&client_id=" + clientId +
                    "&client_secret=" + clientSecret +
                    "&username=" + username +
                    "&password=" + password +
                    "&scope=openid";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            try {
                log.info("Enviando requisição para o Keycloak...");
                ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, String.class);

                log.info("Resposta do Keycloak: {}", response.getBody());

                if (response.getStatusCode() == HttpStatus.OK) {
                    // Convertendo resposta para JSON e retornando para o cliente
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);
                    return ResponseEntity.ok(responseBody);
                } else {
                    log.error("Erro ao autenticar no Keycloak: {}", response.getStatusCode());
                    return ResponseEntity.status(response.getStatusCode()).body(Map.of("error", "Falha na autenticação."));
                }

            } catch (HttpClientErrorException e) {
                log.error("Erro na requisição ao Keycloak: {}", e.getMessage());
                return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", "Erro no Keycloak: " + e.getMessage()));
            } catch (Exception e) {
                log.error("Erro inesperado: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erro interno do servidor."));
            }
        }
    }



