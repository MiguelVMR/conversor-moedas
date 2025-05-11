package com.wefin.conversor_moedas.dto;

import java.math.BigDecimal;

/**
 * The Record ConsultaTaxaCambioRecordDTO
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 11/05/2025
 */
public record ConsultaTaxaCambioRecordDTO(
        BigDecimal valorTaxa,
        BigDecimal valorEnviadoConvertido,
        String suaMoeda,
        String moedaDesejada
) {
}
