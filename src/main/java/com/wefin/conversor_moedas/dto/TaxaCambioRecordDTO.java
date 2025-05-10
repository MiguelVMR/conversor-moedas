package com.wefin.conversor_moedas.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Record TaxaCambioRecordDTO
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
public record TaxaCambioRecordDTO(
        @NotNull(groups = UpdateValidation.class, message = "O ID da Taxa é obrigatório para atualização")
        UUID id,

        @NotNull(groups = CreateValidation.class, message = "A moeda de origem não pode ser nula")
        UUID moedaOrigem,

        @NotNull(groups = CreateValidation.class, message = "A moeda de destino não pode ser nula")
        UUID moedaDestino,

        @NotNull(groups = CreateValidation.class, message = "A taxa não pode ser nula")
        BigDecimal taxa,

        @NotNull(groups = CreateValidation.class, message = "A cidade não pode ser nula")
        UUID cidade
) {
    public interface CreateValidation {}
    public interface UpdateValidation {}
}
