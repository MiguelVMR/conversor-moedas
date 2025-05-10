package com.wefin.conversor_moedas.dto;

import com.wefin.conversor_moedas.enums.TipoAjuste;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Record TaxaPersonalizadaRecordDTO
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
public record TaxaPersonalizadaRecordDTO(
        UUID id,
        BigDecimal valor,
        TipoAjuste tipoAjuste
) {
}
