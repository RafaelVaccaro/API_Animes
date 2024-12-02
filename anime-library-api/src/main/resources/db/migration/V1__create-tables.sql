-- Criação da tabela `animes`
CREATE TABLE animes (
id BIGSERIAL PRIMARY KEY,
titulo VARCHAR(255) NOT NULL,
autor VARCHAR(255) NOT NULL,
nota DOUBLE PRECISION DEFAULT 0.0,
quantidade_avaliacoes INT DEFAULT 0,
sinopse TEXT NOT NULL,
generos JSONB,  -- JSONB no PostgreSQL
ativo BOOLEAN DEFAULT TRUE -- Coluna `ativo` incluída diretamente na criação
);

-- Criação da tabela `generos`
CREATE TABLE generos (
id BIGSERIAL PRIMARY KEY,
nome VARCHAR(255) NOT NULL UNIQUE
);

-- Criação da tabela associativa `anime_generos`
CREATE TABLE anime_generos (
anime_id BIGINT NOT NULL,
genero_id BIGINT NOT NULL,
PRIMARY KEY (anime_id, genero_id),
FOREIGN KEY (anime_id) REFERENCES animes(id) ON DELETE CASCADE,
FOREIGN KEY (genero_id) REFERENCES generos(id) ON DELETE CASCADE
);
