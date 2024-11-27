package com.api.anime.anime_library_api.domain.service;

import com.api.anime.anime_library_api.domain.dto.ListarGenerosDTO;
import com.api.anime.anime_library_api.infrastructure.entity.Genero;
import com.api.anime.anime_library_api.infrastructure.repository.GeneroJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroJPARepository generoJPARepository;

    public Genero findByNome(String nomeGenero) {
        return generoJPARepository.findByNome(nomeGenero)
                .orElse(generoJPARepository.save(new Genero(null, nomeGenero)));
    }

    public Page<ListarGenerosDTO> listar(Pageable pageable) {
        return generoJPARepository.findAll(pageable).map(ListarGenerosDTO::new);
    }
}
