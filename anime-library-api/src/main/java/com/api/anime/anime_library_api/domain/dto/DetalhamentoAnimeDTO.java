package com.api.anime.anime_library_api.domain.dto;

import com.api.anime.anime_library_api.infrastructure.entity.Anime;
import com.api.anime.anime_library_api.infrastructure.entity.Genero;
import com.api.anime.anime_library_api.util.NotaSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record DetalhamentoAnimeDTO(
        Long id,
        String titulo,
        String autor,
        @JsonSerialize(using = NotaSerializer.class)
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

