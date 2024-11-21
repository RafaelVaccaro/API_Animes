package com.api.anime.anime_library_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RegistroAnimeDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String autor,
        @NotBlank
        String sinopse,
        @NotEmpty
        List<String> generos // Agora é uma lista de strings, representando os nomes dos gêneros.
) {}
