package com.wefin.conversor_moedas.enums;

import lombok.Getter;

/**
 * The Enum ErrorType
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
public enum ErrorType {
    ENTITY_NOT_FOUND("Entidade não encontrada"),
    INVALID_DATA("Dados inválidos"),
    DATABASE_ERROR("Erro de banco de dados"),
    BUSINESS_RULE("Regra de negócio violada"),
    UNEXPECTED_ERROR("Erro inesperado");

    @Getter
    private final String description;

    ErrorType(String description) {
        this.description = description;
    }

}
