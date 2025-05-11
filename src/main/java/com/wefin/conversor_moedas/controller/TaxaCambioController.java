package com.wefin.conversor_moedas.controller;

import com.wefin.conversor_moedas.dto.ConsultaTaxaCambioRecordDTO;
import com.wefin.conversor_moedas.dto.TaxaCambioRecordDTO;
import com.wefin.conversor_moedas.model.TaxaCambio;
import com.wefin.conversor_moedas.service.TaxaCambioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Class TacaCambioController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
@Tag(name = "Módulo de Taxas de cambio do Sistema")
@RestController
@RequestMapping("/taxa-cambio")
public class TaxaCambioController {

    private final TaxaCambioService taxaCambioService;

    public TaxaCambioController(TaxaCambioService taxaCambioService) {
        this.taxaCambioService = taxaCambioService;
    }

    @Operation(summary = "Método que busca todas as taxas de cambio do sistema paginados")
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<TaxaCambio>> findAllTaxas(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(taxaCambioService.findAllTaxas(pageable));
    }

    @Operation(summary = "Método que busca uma taxas de cambio no sistema")
    @GetMapping("/{taxaId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TaxaCambio> findTaxaById(@PathVariable(value = "taxaId") UUID taxaId) {
        return ResponseEntity.status(HttpStatus.OK).body(taxaCambioService.findTaxaById(taxaId));
    }

    @Operation(summary = "Método que busca todas as taxas de cambio por cidade")
    @GetMapping("/por-cidade/{cidadeId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<TaxaCambio>> findAllTaxasByCidade(Pageable pageable,@PathVariable(value = "cidadeId") UUID cidadeId) {
        return ResponseEntity.status(HttpStatus.OK).body(taxaCambioService.findAllTaxasByCidade(pageable,cidadeId));
    }

    @Operation(summary = "Método que deleta uma taxa de cambio do sistema")
    @DeleteMapping("/{taxaId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteTaxa(@PathVariable(value = "taxaId") UUID taxaId) {
        taxaCambioService.deleteTaxa(taxaId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Método que cria uma taxa de cambio do sistema")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TaxaCambio> createTaxaCambio(
            @Validated(TaxaCambioRecordDTO.CreateValidation.class) @RequestBody TaxaCambioRecordDTO taxaCambioRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taxaCambioService.createTaxaCambio(taxaCambioRecordDTO));
    }

    @Operation(summary = "Método que atualiza uma taxa de cambio do sistema")
    @PutMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TaxaCambio> updateTaxaCambio(
            @Validated(TaxaCambioRecordDTO.UpdateValidation.class) @RequestBody TaxaCambioRecordDTO taxaCambioRecordDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(taxaCambioService.updateTaxaCambio(taxaCambioRecordDTO));
    }

    @Operation(summary = "Método que consulta a taxas de cambio")
    @GetMapping("consta-taxa")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ConsultaTaxaCambioRecordDTO> consultaTaxa(
            @RequestParam UUID taxaCambioId,
            @RequestParam(required = false) BigDecimal valorEnviado,
            @RequestParam UUID moedaDoCliente
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(taxaCambioService.consultaCotacao(taxaCambioId,valorEnviado,moedaDoCliente));
    }

}
