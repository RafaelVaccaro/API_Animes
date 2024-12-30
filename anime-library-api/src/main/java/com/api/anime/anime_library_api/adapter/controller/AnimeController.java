package com.api.anime.anime_library_api.adapter.controller;

import com.api.anime.anime_library_api.domain.service.AnimeService;
import com.api.anime.anime_library_api.domain.dto.*;
import com.api.anime.anime_library_api.infrastructure.entity.Anime;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("animes")
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public Anime registrar(@RequestBody @Valid RegistroAnimeDTO dados) {
        return animeService.registrar(dados);
    }

    @PostMapping("/{id}/avaliar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void avaliar(@PathVariable Long id, @RequestBody @Valid AvaliarDTO dados) {
        animeService.avaliar(id, dados);
    }

    @GetMapping("/genero/{genero}")
    public Page<ListarAnimeDTO> listarAnimePorGenero(@PathVariable String genero, @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        return animeService.listaGenero(genero, paginacao);

    }

    @GetMapping
    public Page<ListarAnimeDTO> listar(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        return animeService.listar(paginacao);
    }

    @GetMapping("/{id}")
    public DetalhamentoAnimeDTO detalhar(@PathVariable Long id){
        return animeService.detalhar(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public DetalhamentoAnimeDTO atualizar(@RequestBody @Valid AtualizacaoAnimeDTO dados, @PathVariable Long id) {
        return animeService.atualizar(dados, id);
    }

    @PutMapping("/{id}/ativar")
    @Transactional
    public DetalhamentoAnimeDTO ativar(@PathVariable Long id) {
        return animeService.ativar(id);
    }

    @PutMapping("/{id}/{genero}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionarGenero(@PathVariable Long id, @PathVariable String genero) {
        animeService.adicionarGenero(id, genero);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public DetalhamentoAnimeDTO desativar(@PathVariable Long id) {
        return animeService.desativar(id);
    }
}
