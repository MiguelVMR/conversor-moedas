ALTER TABLE tb_produtos
DROP CONSTRAINT IF EXISTS tb_produtos_moeda_origem_id_fkey,
DROP CONSTRAINT IF EXISTS tb_produtos_tipo_ajuste_id_fkey;

ALTER TABLE tb_produtos
    ADD CONSTRAINT tb_produtos_moeda_origem_id_fkey
        FOREIGN KEY (moeda_origem_id) REFERENCES tb_moedas (id) ON DELETE SET NULL,
    ADD CONSTRAINT tb_produtos_tipo_ajuste_id_fkey
        FOREIGN KEY (tipo_ajuste_id) REFERENCES tb_taxa_personalizada (id) ON DELETE SET NULL;

ALTER TABLE tb_produtos
    ADD COLUMN cidade_id UUID;

ALTER TABLE tb_produtos
    ADD CONSTRAINT tb_produtos_cidade_id_fkey
        FOREIGN KEY (cidade_id) REFERENCES tb_cidades (id) ON DELETE SET NULL;

UPDATE tb_produtos
SET cidade_id = (
    SELECT id
    FROM tb_cidades
    WHERE nome = 'Wefin'
)
WHERE cidade_id IS NULL;

ALTER TABLE tb_produtos
    ALTER COLUMN cidade_id SET NOT NULL;
