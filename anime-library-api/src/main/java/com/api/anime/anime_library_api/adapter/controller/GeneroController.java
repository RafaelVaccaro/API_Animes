package com.api.anime.anime_library_api.adapter.controller;

import com.api.anime.anime_library_api.domain.dto.ListarGenerosDTO;
import com.api.anime.anime_library_api.application.usecase.ListarGenerosUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("generos")
public class GeneroController {

    private final ListarGenerosUseCase listarGeneros;

    @Autowired
    public GeneroController(ListarGenerosUseCase listarGeneros) {
        this.listarGeneros = listarGeneros;
    }

    @GetMapping
    public ResponseEntity<Page<ListarGenerosDTO>> listarGeneros(@PageableDefault(size = 20, sort = {"nome"}) Pageable paginacao) {
        Page<ListarGenerosDTO> page = listarGeneros.listar(paginacao);
        return ResponseEntity.ok(page);
    }

}
