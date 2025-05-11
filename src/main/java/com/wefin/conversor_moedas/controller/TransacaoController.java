package com.wefin.conversor_moedas.controller;

import com.wefin.conversor_moedas.dto.ProdutoRecordDTO;
import com.wefin.conversor_moedas.dto.TransacaoRecordDTO;
import com.wefin.conversor_moedas.dto.TransacaoViewRecordDTO;
import com.wefin.conversor_moedas.model.Produto;
import com.wefin.conversor_moedas.model.Transacoes;
import com.wefin.conversor_moedas.service.TrasacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The Class TransacaoController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
@Tag(name = "Módulo de Transação e Conversão de Moeda do Sistema")
@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TrasacaoService trasacaoService;

    public TransacaoController(TrasacaoService trasacaoService) {
        this.trasacaoService = trasacaoService;
    }

    @Operation(summary = "Método que cria uma transacao no sistema")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TransacaoViewRecordDTO> createProduto(JwtAuthenticationToken jwtToken,
                                                                @Validated @RequestBody TransacaoRecordDTO transacaoRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trasacaoService.createTransacao(transacaoRecordDTO,jwtToken));
    }

    @Operation(summary = "Método que busca uma transação no sistema")
    @GetMapping("/{transacaoId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TransacaoViewRecordDTO> findTransacaoById(
            @PathVariable(value = "transacaoId") UUID transacaoId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(trasacaoService.findTransacaoById(transacaoId));
    }

}
