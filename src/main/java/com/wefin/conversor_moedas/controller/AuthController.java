package com.wefin.conversor_moedas.controller;

import com.wefin.conversor_moedas.dto.LoginRequestRecordDTO;
import com.wefin.conversor_moedas.dto.LoginResponseRecordDTO;
import com.wefin.conversor_moedas.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class AuthController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
@Tag(name = "Módulo de login do sistema")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Endpoint que faz o login do usuário no sistema")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseRecordDTO> login(LoginRequestRecordDTO loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequest));
    }
}
