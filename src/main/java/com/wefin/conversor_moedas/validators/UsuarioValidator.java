package com.wefin.conversor_moedas.validators;


import com.wefin.conversor_moedas.dto.CreateUserDTO;
import com.wefin.conversor_moedas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class UserValidator
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 16/03/2025
 */
@Component
public class UsuarioValidator implements Validator {
    private final Validator validator;
    final UsuarioService usuarioService;

    public UsuarioValidator(@Qualifier("defaultValidator") Validator validator, UsuarioService userService) {
        this.validator = validator;
        this.usuarioService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        CreateUserDTO userRecordDto = (CreateUserDTO) o;
        validator.validate(userRecordDto, errors);
        if(!errors.hasErrors()){
            validateUsername(userRecordDto, errors);
        }
    }

    private void validateUsername(CreateUserDTO createUserRecordDTO, Errors errors){
        if(usuarioService.existsByUsername(createUserRecordDTO.username())) {
            errors.rejectValue("username", "usernameConflict", "Erro: Username ja esta em uso!");
        }
    }
}
