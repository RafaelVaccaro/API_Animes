package com.api.anime.anime_library_api.domain.dto;

import com.api.anime.anime_library_api.infrastructure.entity.Genero;

public record ListarGenerosDTO(String nome) {
    public ListarGenerosDTO(Genero genero) {
        this(genero.getNome());
    }
}
