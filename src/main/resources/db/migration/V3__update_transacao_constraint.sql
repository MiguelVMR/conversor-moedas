ALTER TABLE tb_transacoes
DROP CONSTRAINT IF EXISTS tb_transacoes_usuario_id_fkey,
DROP CONSTRAINT IF EXISTS tb_transacoes_produto_id_fkey,
DROP CONSTRAINT IF EXISTS tb_transacoes_moeda_origem_id_fkey,
DROP CONSTRAINT IF EXISTS tb_transacoes_taxa_cambio_id_fkey;

ALTER TABLE tb_transacoes
    ADD CONSTRAINT tb_transacoes_usuario_id_fkey
        FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id),
    ADD CONSTRAINT tb_transacoes_produto_id_fkey
        FOREIGN KEY (produto_id) REFERENCES tb_produtos (id),
    ADD CONSTRAINT tb_transacoes_moeda_origem_id_fkey
        FOREIGN KEY (moeda_origem_id) REFERENCES tb_moedas (id),
    ADD CONSTRAINT tb_transacoes_taxa_cambio_id_fkey
        FOREIGN KEY (taxa_cambio_id) REFERENCES tb_taxas_cambio (id);

ALTER TABLE tb_transacoes
    ADD COLUMN moeda_destino_id UUID NOT NULL;

ALTER TABLE tb_transacoes
    ADD CONSTRAINT tb_transacoes_moeda_destino_id_fkey
        FOREIGN KEY (moeda_destino_id) REFERENCES tb_moedas (id);

