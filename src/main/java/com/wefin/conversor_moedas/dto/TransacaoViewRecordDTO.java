package com.wefin.conversor_moedas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The Record TransacaoViewRecordDTO
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
public record TransacaoViewRecordDTO(
        UUID id,
        String MoedaQueClienteUsou,
        String moedaDoProduto,
        BigDecimal valorDosProdutos,
        BigDecimal valorUnitarioProduto,
        BigDecimal quantidadeDoProduto,
        BigDecimal valorDoProdutoConvertido,
        BigDecimal valorTroco,
        BigDecimal valoDadoPeloCliente,
        LocalDateTime dataTransacao

) {
}
