package com.wefin.conversor_moedas.repository;

import com.wefin.conversor_moedas.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * The Interface ProdutoRepository
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    @EntityGraph(value = "Produto.completo")
    Page<Produto> findAll(Pageable pageable);

    @EntityGraph(value = "Produto.completo")
    Optional<Produto> findById(UUID id);

    @Query("""
        SELECT DISTINCT p FROM Produto p
        INNER JOIN FETCH p.moeda m
        INNER JOIN FETCH p.cidade c
        LEFT JOIN FETCH p.taxaPersonalizada tp
        WHERE p.cidade.id = :cidadeId
        """)
    Page<Produto> findAllByCidade(Pageable pageable, @Param("cidadeId") UUID cidadeId);


    @Query(value = """
    SELECT DISTINCT p 
    FROM Produto p 
    INNER JOIN FETCH p.moeda m 
    INNER JOIN FETCH p.cidade c 
    LEFT JOIN FETCH p.taxaPersonalizada tp 
    WHERE (:comAjuste IS NULL OR 
        (:comAjuste = true AND tp IS NOT NULL) OR 
        (:comAjuste = false AND tp IS NULL))
    AND (:cidadeId IS NULL OR c.id = :cidadeId)
    AND (:moedaId IS NULL OR m.id = :moedaId)
    AND (:nome IS NULL OR CAST(p.nome AS string) ILIKE CONCAT('%', CAST(:nome AS string), '%'))
    """,
            countQuery = """
    SELECT COUNT(DISTINCT p) 
    FROM Produto p 
    INNER JOIN p.moeda m 
    INNER JOIN p.cidade c 
    LEFT JOIN p.taxaPersonalizada tp 
    WHERE (:comAjuste IS NULL OR 
        (:comAjuste = true AND tp IS NOT NULL) OR 
        (:comAjuste = false AND tp IS NULL))
    AND (:cidadeId IS NULL OR c.id = :cidadeId)
    AND (:moedaId IS NULL OR m.id = :moedaId)
    AND (:nome IS NULL OR CAST(p.nome AS string) ILIKE CONCAT('%', CAST(:nome AS string), '%'))
    """)
    Page<Produto> findAllProdutosWithFilters(
            Pageable pageable,
            @Param("cidadeId") UUID cidadeId,
            @Param("nome") String nome,
            @Param("moedaId") UUID moedaId,
            @Param("comAjuste") Boolean comAjuste
    );

}
