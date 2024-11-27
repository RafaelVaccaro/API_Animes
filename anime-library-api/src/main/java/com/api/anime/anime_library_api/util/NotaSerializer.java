package com.api.anime.anime_library_api.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class NotaSerializer extends StdSerializer<Double> {

    public NotaSerializer() {
        super(Double.class);
    }

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            // Arredonda para 1 casa decimal e escreve como string
            gen.writeString(String.format("%.1f", value));
        }
    }
}

//keycloak.auth-server-url=http://localhost:8080/realms/anime_api
//keycloak.realm=anime_api_realm
//keycloak.resource=anime-api
//keycloak.credentials.secret=<CLIENT_SECRET>  # Substitua pelo segredo do cliente que você criou no Keycloak
//keycloak.public-client=true
//
//        # Define as permissões para as URLs
//keycloak.security-constraints[0].authRoles[0]=user
//keycloak.security-constraints[0].url-pattern=/*
