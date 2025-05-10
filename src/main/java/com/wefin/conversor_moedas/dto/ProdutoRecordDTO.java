package com.wefin.conversor_moedas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Record ProdutoRecordDTO
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
public record ProdutoRecordDTO(
        @NotNull(groups = ProdutoRecordDTO.UpdateValidation.class, message = "O ID do Produto é obrigatório para atualização")
        UUID id,

        @NotNull(groups = ProdutoRecordDTO.CreateValidation.class, message = "O Nome não pode ser nula")
        String nome,

        @NotNull(groups = ProdutoRecordDTO.CreateValidation.class, message = "A Descricao não pode ser nula")
        String descricao,

        @NotNull(groups = ProdutoRecordDTO.CreateValidation.class, message = "O Preco não pode ser nula")
        @Positive(groups = ProdutoRecordDTO.CreateValidation.class,message = "O valor do produto deve ser maior que zero")
        BigDecimal preco,

        @NotNull(groups = ProdutoRecordDTO.CreateValidation.class, message = "A Moeda não pode ser nula")
        UUID moeda,

        @NotNull(groups = ProdutoRecordDTO.CreateValidation.class, message = "A Cidade não pode ser nula")
        UUID cidade,

        TaxaPersonalizadaRecordDTO taxaPersonalizada
) {

    public interface CreateValidation {}
    public interface UpdateValidation {}
}
