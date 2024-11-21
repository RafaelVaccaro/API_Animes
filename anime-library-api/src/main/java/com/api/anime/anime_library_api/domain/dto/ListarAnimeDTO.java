package com.api.anime.anime_library_api.domain.dto;

import com.api.anime.anime_library_api.infrastructure.entity.Anime;

public record ListarAnimeDTO(Long id, String titulo, Double nota) {

    public ListarAnimeDTO(Anime anime) {
        this(
                anime.getId(),
                anime.getTitulo(),
                anime.getNota());
    }
}

