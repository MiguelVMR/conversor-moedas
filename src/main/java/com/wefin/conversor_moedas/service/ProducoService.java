package com.wefin.conversor_moedas.service;

import com.wefin.conversor_moedas.dto.ProdutoRecordDTO;
import com.wefin.conversor_moedas.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * The Interface ProducoService
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
public interface ProducoService {

    Produto findProdutoById(UUID produtoId);

    Page<Produto> findAllProdutos(Pageable pageable);

    Page<Produto> findAllProdutosByCidade(Pageable pageable, UUID cidadeId);

    Page<Produto> buscaPorFiltros(UUID cidadeId, String nome, UUID moedaId, Boolean temAjuste, Pageable pageable);

    Produto createProduto(ProdutoRecordDTO produtoRecordDTO);

    Produto updateProduto(ProdutoRecordDTO produtoRecordDTO);

    void deleteProduto(UUID produtoId);
}
