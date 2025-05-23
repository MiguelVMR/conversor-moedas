package com.wefin.conversor_moedas.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
* The Record ErrorRecorddResponse
*
* @author Miguel Vilela Moraes Ribeiro
* @since 09/05/2025
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorRecorddResponse( int errorCode,
                                    String errorMessage,
                                    Map<String, String> errorDetails) {
}
