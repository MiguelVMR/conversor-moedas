package com.wefin.conversor_moedas.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Record TransacaoRecordDTO
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
public record TransacaoRecordDTO(
        @NotNull(message = "Produto não pode ser nulo")
        UUID produtoId,
        @NotNull(message = "Quantidade do Produto não pode ser nulo")
        BigDecimal quantidade,
        @NotNull(message = "Moeda de Origem não pode ser nulo")
        UUID moedaOrigemId,
        @NotNull(message = "Quantidade do Produto não pode ser nulo")
        BigDecimal valorDadoPeloCliente,
        UUID taxaCambioId
        ) {
}
