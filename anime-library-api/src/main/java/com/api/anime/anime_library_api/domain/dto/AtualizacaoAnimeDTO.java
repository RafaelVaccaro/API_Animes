package com.api.anime.anime_library_api.domain.dto;

import com.api.anime.anime_library_api.infrastructure.entity.Genero;

import java.util.List;


public record AtualizacaoAnimeDTO(

        String titulo,
        String autor,
        String sinopse,
        List<Genero> generos

) {
}
