package com.api.anime.anime_library_api.infrastructure.entity;

import com.api.anime.anime_library_api.domain.dto.AtualizacaoAnimeDTO;
import com.api.anime.anime_library_api.domain.dto.RegistroAnimeDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "animes")
@Entity(name = "Anime")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String autor;

    private Double nota = 0.0;

    @Column(name = "quantidade_avaliacoes", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int quantidadeAvaliacoes = 0;

    private String sinopse;

    @ManyToMany
    @JoinTable(
            name = "anime_generos",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genero> generos;

    private Boolean ativo;

    public Anime(RegistroAnimeDTO dados, List<Genero> generos) {
        this.titulo = dados.titulo();
        this.autor = dados.autor();
        this.sinopse = dados.sinopse();
        this.generos = generos;
        this.ativo = true;
    }

    public void adicionarNota(Double novaNota) {
        this.nota = ((this.nota * this.quantidadeAvaliacoes) + novaNota) / (this.quantidadeAvaliacoes + 1);
        this.quantidadeAvaliacoes++;
    }

    public void atualizarInformacoes(AtualizacaoAnimeDTO dados) {
        if (dados.titulo() != null)
            this.titulo = dados.titulo();
        if (dados.autor() != null)
            this.autor = dados.autor();
        if (dados.sinopse() != null)
            this.sinopse = dados.sinopse();
        if (dados.generos() != null) {
            // Limpa os gêneros atuais antes de adicionar os novos
            this.generos.clear();
            this.generos.addAll(dados.generos());
        }
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
