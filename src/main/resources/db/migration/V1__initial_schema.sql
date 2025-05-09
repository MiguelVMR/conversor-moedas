
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

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
    FOREIGN KEY (moeda_origem_id) REFERENCES tb_moedas (id) ON DELETE SET NULL,
    FOREIGN KEY (tipo_ajuste_id) REFERENCES tb_taxa_personalizada (id) ON DELETE CASCADE
);


CREATE TABLE tb_taxas_cambio
(
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    moeda_origem_id  UUID           NOT NULL,
    moeda_destino_id UUID           NOT NULL,
    cidade_id         UUID           NOT NULL,
    taxa             DECIMAL(18, 6) NOT NULL,
    date             DATE           NOT NULL,
    FOREIGN KEY (moeda_origem_id) REFERENCES tb_moedas (id) ON DELETE CASCADE,
    FOREIGN KEY (moeda_destino_id) REFERENCES tb_moedas (id) ON DELETE CASCADE,
    FOREIGN KEY (cidade_id) REFERENCES tb_cidades (id) ON DELETE CASCADE
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
    taxa_cambio_id   UUID           NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES tb_produtos (id) ON DELETE CASCADE,
    FOREIGN KEY (moeda_origem_id) REFERENCES tb_moedas (id) ON DELETE CASCADE,
    FOREIGN KEY (taxa_cambio_id) REFERENCES tb_taxas_cambio (id) ON DELETE CASCADE
);

CREATE INDEX idx_transaction_date ON tb_transacoes (transaction_date);
CREATE INDEX idx_exchange_rate_date ON tb_taxas_cambio (date);

INSERT INTO tb_moedas (id, name, symbol)
VALUES (uuid_generate_v4(), 'Ouro Real', 'OR'),
       (uuid_generate_v4(), 'Tibar', 'TB');

INSERT INTO tb_cidades (id, nome)
VALUES (uuid_generate_v4(), 'Wefin');

INSERT INTO tb_produtos (id, nome, descricao, preco, moeda_origem_id)
VALUES (uuid_generate_v4(), 'Peles', 'Peles de animais raros.', 100.00,
        (SELECT id FROM tb_moedas WHERE name = 'Ouro Real')),
       (uuid_generate_v4(), 'Madeira', 'Madeira de alta qualidade.', 50.00,
        (SELECT id FROM tb_moedas WHERE name = 'Ouro Real')),
       (uuid_generate_v4(), 'Hidromel', 'Bebida tradicional fermentada.', 25.00,
        (SELECT id FROM tb_moedas WHERE name = 'Ouro Real'));

INSERT INTO tb_taxas_cambio (id, moeda_origem_id, moeda_destino_id, cidade_id, taxa, date)
VALUES (uuid_generate_v4(),
        (SELECT id FROM tb_moedas WHERE name = 'Ouro Real'),
        (SELECT id FROM tb_moedas WHERE name = 'Tibar'),
        (SELECT id FROM tb_cidades WHERE nome = 'Wefin'),
        2.5,
        CURRENT_DATE);
