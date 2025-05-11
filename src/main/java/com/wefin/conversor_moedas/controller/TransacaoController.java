package com.wefin.conversor_moedas.controller;

import com.wefin.conversor_moedas.dto.TransacaoRecordDTO;
import com.wefin.conversor_moedas.dto.TransacaoViewRecordDTO;
import com.wefin.conversor_moedas.service.TrasacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The Class TransacaoController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
@Tag(name = "Módulo de Transações do Sistema")
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

    @Operation(summary = "Método que busca transações com filtros")
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<TransacaoViewRecordDTO>> findTransacoesWithFilters(
            @RequestParam(required = false) UUID cidadeId,
            @RequestParam(required = false) UUID produtoId,
            @RequestParam(required = false) UUID moedaOrigemId,
            @RequestParam(required = false) UUID moedaDestinoId,
            @RequestParam(required = false) LocalDate dataInicial,
            @RequestParam(required = false) LocalDate dataFinal,
            Pageable pageable) {

        return ResponseEntity.ok(trasacaoService.findTransacoesWithFilters(
                pageable,produtoId, cidadeId, moedaOrigemId, moedaDestinoId, dataInicial, dataFinal));
    }


}
