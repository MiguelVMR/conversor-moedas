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
public record CidadeRecordDTO(
        UUID id,

        @NotBlank(message = "Nome do Reino é obrigatório")
        @Size(max = 100)
        String name
) {
}
