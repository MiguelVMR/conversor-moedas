package com.wefin.conversor_moedas.exception;

import com.wefin.conversor_moedas.enums.ErrorType;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
public class BusinessException extends RuntimeException {
    private final ErrorType errorType;
    private final String field;

    public BusinessException(String message) {
        this(message, ErrorType.UNEXPECTED_ERROR, null);
    }

    public BusinessException(String message, ErrorType errorType) {
        this(message, errorType, null);
    }

    public BusinessException(String message, ErrorType errorType, String field) {
        super(message);
        this.errorType = errorType;
        this.field = field;
    }

    public BusinessException(String message, Throwable cause) {
        this(message, ErrorType.UNEXPECTED_ERROR, null, cause);
    }

    public BusinessException(String message, ErrorType errorType, String field, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
        this.field = field;
    }

    public static BusinessException entityNotFound(String entity, String id) {
        return new BusinessException(
                String.format("%s com ID %s não encontrado(a)", entity, id),
                ErrorType.ENTITY_NOT_FOUND,
                entity.toLowerCase()
        );
    }

    public static BusinessException invalidData(String field, String reason) {
        return new BusinessException(
                String.format("Dados inválidos para o campo %s: %s", field, reason),
                ErrorType.INVALID_DATA,
                field
        );
    }

    public static BusinessException insufficientFunds(BigDecimal valorDado, BigDecimal valorASerPago) {
        return new BusinessException(
                "Valor insuficiente",
                ErrorType.INSUFFICIENT_FUNDS,
                String.format("Valor fornecido (%s) é menor que o valor necessário (%s)",
                        valorDado, valorASerPago)
        );
    }

    public static BusinessException businessRule(String message) {
        return new BusinessException(message, ErrorType.BUSINESS_RULE);
    }


}
