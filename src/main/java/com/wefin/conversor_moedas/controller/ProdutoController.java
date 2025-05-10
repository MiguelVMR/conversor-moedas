package com.wefin.conversor_moedas.controller;

import com.wefin.conversor_moedas.dto.ProdutoRecordDTO;
import com.wefin.conversor_moedas.model.Produto;
import com.wefin.conversor_moedas.service.ProducoService;
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
 * The Class ProdutoController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
@Tag(name = "Módulo de Produtos do Sistema")
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProducoService producoService;

    public ProdutoController(ProducoService producoService) {
        this.producoService = producoService;
    }

    @Operation(summary = "Método que busca todas os produtos do sistema paginados")
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<Produto>> findAllProdutos(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(producoService.findAllProdutos(pageable));
    }

    @Operation(summary = "Método que busca um produto no sistema")
    @GetMapping("/{produtoId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Produto> findProdutoById(@PathVariable(value = "produtoId") UUID produtoId) {
        return ResponseEntity.status(HttpStatus.OK).body(producoService.findProdutoById(produtoId));
    }

    @Operation(summary = "Método que busca todas os produtos por cidade")
    @GetMapping("/por-cidade/{cidadeId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<Produto>> findAllProdutosByCidade(Pageable pageable,@PathVariable(value = "cidadeId") UUID cidadeId) {
        return ResponseEntity.status(HttpStatus.OK).body(producoService.findAllProdutosByCidade(pageable,cidadeId));
    }

    @Operation(summary = "Método que busca todas os produtos por filtros")
    @GetMapping("/por-filtros")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<Produto>> findAllProdutosByFiltros(
            @RequestParam(value = "cidadeId", required = false) UUID cidadeId,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "moedaId", required = false) UUID moedaId,
            @RequestParam(value = "temAjuste", required = false) boolean temAjuste,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(producoService.buscaPorFiltros(cidadeId,nome,moedaId,temAjuste,pageable));
    }

    @Operation(summary = "Método que cria um produto no sistema")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Produto> createProduto(
            @Validated(ProdutoRecordDTO.CreateValidation.class) @RequestBody ProdutoRecordDTO produtoRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(producoService.createProduto(produtoRecordDTO));
    }

    @Operation(summary = "Método que atualiza um produto no sistema")
    @PutMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Produto> updateProduto(
            @Validated(ProdutoRecordDTO.UpdateValidation.class) @RequestBody ProdutoRecordDTO produtoRecordDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(producoService.updateProduto(produtoRecordDTO));
    }

}
