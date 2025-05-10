package com.wefin.conversor_moedas.controller;

import com.wefin.conversor_moedas.dto.MoedaRecordDTO;
import com.wefin.conversor_moedas.model.Moeda;
import com.wefin.conversor_moedas.service.MoedaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The Class MoedaController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
@Tag(name = "Módulo de Moedas do Sistema")
@RestController
@RequestMapping("/moeda")
public class MoedaController {

    private final MoedaService moedaService;

    public MoedaController(MoedaService moedaService) {
        this.moedaService = moedaService;
    }

    @Operation(summary = "Método que cria uma moeda no sistema")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Object> createMoeda(@RequestBody @Validated MoedaRecordDTO moedaRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moedaService.createMoeda(moedaRecordDTO));
    }

    @Operation(summary = "Método que atualiza uma moeda do sistema")
    @PutMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Object> updateMoeda(@RequestBody @Validated MoedaRecordDTO moedaRecordDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(moedaService.updateMoeda(moedaRecordDTO));
    }

    @Operation(summary = "Método que deleta uma moeda do sistema")
    @DeleteMapping("/{moedaId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteMoeda(@PathVariable(value = "moedaId") UUID moedaId) {
        moedaService.deleteMoeda(moedaId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Método que busca todas as moedas do sistema paginadas")
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<Moeda>> findAllMoedas(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(moedaService.findAllMoedas(pageable));
    }

    @Operation(summary = "Método que busca uma moeda no sistema")
    @GetMapping("/{moedaId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Moeda> findMoedaById(@PathVariable(value = "moedaId") UUID moedaId) {
        return ResponseEntity.status(HttpStatus.OK).body(moedaService.findMoedaById(moedaId));
    }

}
