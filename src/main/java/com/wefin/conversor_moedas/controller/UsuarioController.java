package com.wefin.conversor_moedas.controller;

import com.wefin.conversor_moedas.dto.CreateUserDTO;
import com.wefin.conversor_moedas.service.UsuarioService;
import com.wefin.conversor_moedas.validators.UsuarioValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class UserController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
@Tag(name = "Módulo de Usuarios do Sistema")
@RestController
public class UsuarioController {

    private final UsuarioValidator userValidator;
    private final UsuarioService userService;

    public UsuarioController(UsuarioValidator userValidator, UsuarioService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @Operation(summary = "Método que cria um usuário no sistema")
    @PostMapping("/usuario")
    public ResponseEntity<Object> createUser(@RequestBody @Validated CreateUserDTO createUserDTO, Errors errors) {
        userValidator.validate(createUserDTO, errors);
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(createUserDTO));
    }
}
