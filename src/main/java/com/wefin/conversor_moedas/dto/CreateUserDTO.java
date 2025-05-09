package com.wefin.conversor_moedas.dto;

import com.wefin.conversor_moedas.validators.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
* The Record CreateUserDTO
*
* @author Miguel Vilela Moraes Ribeiro
* @since 15/03/2025
*/
public record CreateUserDTO(
        @NotBlank(message = "Username é obrigatório")
        String username,
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,
        @PasswordConstraint
        String password) {
}
