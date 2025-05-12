# API de Conversão de Moedas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

## 🛠️ Script SQL

```
CREATE TABLE tb_usuario
(
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username      VARCHAR(50)  NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_moedas
(
    id     UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name   VARCHAR(50) NOT NULL,
    symbol VARCHAR(10) NOT NULL
);

CREATE TABLE tb_cidades
(
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tb_taxa_personalizada
(
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    valor DECIMAL(18, 6) NOT NULL,
    tipo_ajuste varchar(25) NOT NULL

);

CREATE TABLE tb_produtos
(
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome            VARCHAR(100)   NOT NULL,
    descricao       TEXT,
    preco           DECIMAL(18, 6) NOT NULL,
    moeda_origem_id UUID,
    tipo_ajuste_id UUID,
    cidade_id UUID,
    FOREIGN KEY (moeda_origem_id) REFERENCES tb_moedas (id),
    FOREIGN KEY (tipo_ajuste_id) REFERENCES tb_taxa_personalizada (id),
    FOREIGN KEY (cidade_id) REFERENCES tb_cidades (id)
);

CREATE TABLE tb_taxas_cambio
(
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    moeda_origem_id  UUID           NOT NULL,
    moeda_destino_id UUID           NOT NULL,
    cidade_id         UUID           NOT NULL,
    taxa             DECIMAL(18, 6) NOT NULL,
    date             DATE           NOT NULL,
    FOREIGN KEY (moeda_origem_id) REFERENCES tb_moedas (id),
    FOREIGN KEY (moeda_destino_id) REFERENCES tb_moedas (id),
    FOREIGN KEY (cidade_id) REFERENCES tb_cidades (id)
);

CREATE TABLE tb_transacoes
(
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    usuario_id       UUID           NOT NULL,
    produto_id       UUID           NOT NULL,
    quantidade       DECIMAL(18, 6) NOT NULL,
    valor_original   DECIMAL(18, 6) NOT NULL,
    valor_convertido DECIMAL(18, 6) NOT NULL,
    valor_troco DECIMAL(18, 6),
    transaction_date TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    moeda_origem_id  UUID           NOT NULL,
    taxa_cambio_id   UUID,
    moeda_destino_id UUID           NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id),
    FOREIGN KEY (produto_id) REFERENCES tb_produtos (id),
    FOREIGN KEY (moeda_origem_id) REFERENCES tb_moedas (id),
    FOREIGN KEY (taxa_cambio_id) REFERENCES tb_taxas_cambio (id),
    FOREIGN KEY (moeda_destino_id) REFERENCES tb_moedas (id)
);
```

### 🔍 Documentação da API (Swagger)

A documentação da API está disponível diretamente pelo Swagger

```
http://localhost:8080/swagger-ui.html
```
