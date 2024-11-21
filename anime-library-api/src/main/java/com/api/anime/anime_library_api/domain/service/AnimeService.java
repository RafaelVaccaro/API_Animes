package com.api.anime.anime_library_api.domain.service;

import com.api.anime.anime_library_api.domain.dto.*;
import com.api.anime.anime_library_api.domain.service.GeneroService;
import com.api.anime.anime_library_api.infrastructure.entity.Anime;
import com.api.anime.anime_library_api.infrastructure.entity.Genero;
import com.api.anime.anime_library_api.infrastructure.repository.AnimeJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final GeneroService genderService;
    private final AnimeJPARepository animeJPARepository;

    public Anime registrar(RegistroAnimeDTO dados) {
        List<Genero> generos = dados.generos().stream()
                .map(genderService::findByNome)
                .collect(Collectors.toList());

        Anime anime = new Anime(dados, generos);
        animeJPARepository.save(anime);

        return anime;
    }

    public void avaliar(Long id, AvaliarDTO novaNota) {
        Anime anime = animeJPARepository.getReferenceById(id);
        anime.adicionarNota(novaNota.nota());
        animeJPARepository.save(anime);
    }

    public Page<ListarAnimeDTO> listar(Pageable pageable) {
        return animeJPARepository.findAllByAtivoTrue(pageable).map(ListarAnimeDTO::new);
    }

    public Page<ListarAnimeDTO> listaGenero(String genero, Pageable pageable) {
        Page<Anime> animes = animeJPARepository.findByGenerosNomeIgnoreCaseAndAtivoTrue(genero, pageable);

        List<ListarAnimeDTO> animesDTO = animes.getContent().stream()
                .map(ListarAnimeDTO::new)
                .collect(Collectors.toList());

        return new PageImpl<>(animesDTO, pageable, animes.getTotalElements());
    }

    public DetalhamentoAnimeDTO detalhar(Long id) {
        return DetalhamentoAnimeDTO.toDto(animeJPARepository.getReferenceById(id));
    }

    public DetalhamentoAnimeDTO atualizar(AtualizacaoAnimeDTO dados, Long id) {
        Anime anime = animeJPARepository.getReferenceById(id);
        anime.atualizarInformacoes(dados);

        List<Genero> generos = new ArrayList<>();

        dados.generos().forEach(nomeGenero -> {
            generos.add(genderService.findByNome(String.valueOf(nomeGenero)));
        });

        anime.setGeneros(generos);

        return DetalhamentoAnimeDTO.toDto(anime);
    }

    public DetalhamentoAnimeDTO ativar(Long id) {
        Anime anime = animeJPARepository.getReferenceById(id);
        anime.ativar();
        return DetalhamentoAnimeDTO.toDto(anime);
    }

    public void adicionarGenero(Long id, String nomeGenero) {
        Anime anime = animeJPARepository.getReferenceById(id);
        anime.getGeneros().add( genderService.findByNome(nomeGenero));
        animeJPARepository.save(anime);
    }

    public DetalhamentoAnimeDTO desativar(Long id) {
        Anime anime = animeJPARepository.getReferenceById(id);
        anime.desativar();
        return DetalhamentoAnimeDTO.toDto(animeJPARepository.save(anime));
    }
}