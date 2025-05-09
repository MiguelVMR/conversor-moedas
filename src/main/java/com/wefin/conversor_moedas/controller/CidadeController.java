package com.wefin.conversor_moedas.controller;

import com.wefin.conversor_moedas.dto.CidadeRecordDTO;
import com.wefin.conversor_moedas.model.Cidade;
import com.wefin.conversor_moedas.service.CidadeService;
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
 * The Class ReinoController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
@Tag(name = "Módulo de Cidades do Sistema")
@RestController
@RequestMapping("/cidade")
public class CidadeController {

    private final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @Operation(summary = "Método que cria um cidade no sistema")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Object> createCidade(@RequestBody @Validated CidadeRecordDTO moedaRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.createCidade(moedaRecordDTO));
    }

    @Operation(summary = "Método que atualiza uma cidade do sistema")
    @PutMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Object> updateCidade(@RequestBody @Validated CidadeRecordDTO moedaRecordDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(cidadeService.updateCidade(moedaRecordDTO));
    }

    @Operation(summary = "Método que deleta uma cidade do sistema")
    @DeleteMapping("/{cidadeId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteCidade(@PathVariable(value = "cidadeId") UUID cidadeId) {
        cidadeService.deleteCidade(cidadeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Método que busca todas as cidades do sistema paginados")
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<Cidade>> findAllCidades(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(cidadeService.findAllCidades(pageable));
    }

    @Operation(summary = "Método que busca uma cidade no sistema")
    @GetMapping("/{cidadeId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Cidade> findCidadeById(@PathVariable(value = "cidadeId") UUID cidadeId) {
        return ResponseEntity.status(HttpStatus.OK).body(cidadeService.findCidadeById(cidadeId));
    }
}
