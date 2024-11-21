package com.api.anime.anime_library_api.infrastructure.repository;

import com.api.anime.anime_library_api.infrastructure.entity.Anime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeJPARepository extends JpaRepository<Anime, Long>{

    Page<Anime> findAllByAtivoTrue(Pageable paginacao);

    Page<Anime> findByGenerosNomeIgnoreCaseAndAtivoTrue(String nomeGenero, Pageable pageable);

}
