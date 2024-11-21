package com.api.anime.anime_library_api.infrastructure.repository;

import com.api.anime.anime_library_api.infrastructure.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneroJPARepository extends JpaRepository<Genero, Long>{

    Optional<Genero> findByNome(String nome);

}
