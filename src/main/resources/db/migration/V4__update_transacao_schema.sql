ALTER TABLE tb_transacoes
DROP CONSTRAINT IF EXISTS tb_transacoes_taxa_cambio_id_fkey;

ALTER TABLE tb_transacoes
    ALTER COLUMN taxa_cambio_id DROP NOT NULL;

ALTER TABLE tb_transacoes
    ADD CONSTRAINT tb_transacoes_taxa_cambio_id_fkey
        FOREIGN KEY (taxa_cambio_id) REFERENCES tb_taxas_cambio (id);
