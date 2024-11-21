package com.api.anime.anime_library_api.domain.dto;

import com.api.anime.anime_library_api.infrastructure.entity.Anime;
import com.api.anime.anime_library_api.infrastructure.entity.Genero;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record DetalhamentoAnimeDTO(
        Long id,
        String titulo,
        String autor,
        Double nota,
        String sinopse,
        List<String> generos,
        Boolean ativo) {

    public static DetalhamentoAnimeDTO toDto(Anime anime) {
        return DetalhamentoAnimeDTO.builder()
                .id(anime.getId())
                .titulo(anime.getTitulo())
                .autor(anime.getAutor())
                .nota(anime.getNota())
                .sinopse(anime.getSinopse())
                .generos(anime.getGeneros().stream().map(Genero::getNome).toList())
                .ativo(anime.getAtivo())
                .build();
    }

}

