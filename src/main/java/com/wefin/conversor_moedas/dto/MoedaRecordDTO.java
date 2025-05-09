package com.wefin.conversor_moedas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * The Record MoedaRecordDTO
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
public record MoedaRecordDTO(
        UUID id,

        @NotBlank(message = "Nome da Moeda é obrigatório")
        @Size(max = 50)
        String name,

        @NotBlank(message = "Simbulo da Moeda é obrigatório")
        @Size(max = 10)
        String symbol
) {
}
