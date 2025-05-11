package com.wefin.conversor_moedas.repository;

import com.wefin.conversor_moedas.dto.TransacaoViewRecordDTO;
import com.wefin.conversor_moedas.model.Transacoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * The Interface TransacaoRepository
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
public interface TransacaoRepository extends JpaRepository<Transacoes, UUID> {

    @Query("""
        SELECT new com.wefin.conversor_moedas.dto.TransacaoViewRecordDTO(
            t.id,
            mo.name,
            md.name,
            t.valorOriginal,
            CAST((t.valorOriginal / t.quantidade) AS bigdecimal),
            t.quantidade,
            t.valorConvertido,
            t.valorTroco,
            CAST((t.valorConvertido + t.valorTroco) AS bigdecimal),
            t.transactionDate
        )
        FROM Transacoes t
        JOIN t.moedaOrigem mo
        JOIN t.moedaDestino md
        WHERE t.id = :id
        """)
    Optional<TransacaoViewRecordDTO> findTransacaoViewById(@Param("id") UUID id);

    @Query(value = """
    SELECT new com.wefin.conversor_moedas.dto.TransacaoViewRecordDTO(
        t.id,
        mo.name,
        md.name,
        t.valorOriginal,
        CAST((t.valorOriginal / t.quantidade) AS bigdecimal),
        t.quantidade,
        t.valorConvertido,
        t.valorTroco,
        CAST((t.valorConvertido + t.valorTroco) AS bigdecimal),
        t.transactionDate
    )
    FROM Transacoes t
    INNER JOIN t.produto p
    INNER JOIN t.moedaOrigem mo
    INNER JOIN t.moedaDestino md
    INNER JOIN p.cidade c
    LEFT JOIN t.taxaCambio tc
    WHERE (:cidadeId IS NULL OR c.id = :cidadeId)
    AND (:moedaOrigemId IS NULL OR mo.id = :moedaOrigemId)
    AND (:moedaDestinoId IS NULL OR md.id = :moedaDestinoId)
    AND (:produtoId IS NULL OR p.id = :produtoId)
    AND (cast(:dataInicial as timestamp) IS NULL AND cast(:dataFinal as timestamp) IS NULL
        OR t.transactionDate BETWEEN :dataInicial AND :dataFinal)
    """,
            countQuery = """
    SELECT COUNT(t)
    FROM Transacoes t
    INNER JOIN t.produto p
    INNER JOIN t.moedaOrigem mo
    INNER JOIN t.moedaDestino md
    INNER JOIN p.cidade c
    LEFT JOIN t.taxaCambio tc
    WHERE (:cidadeId IS NULL OR c.id = :cidadeId)
    AND (:moedaOrigemId IS NULL OR mo.id = :moedaOrigemId)
    AND (:moedaDestinoId IS NULL OR md.id = :moedaDestinoId)
    AND (:produtoId IS NULL OR p.id = :produtoId)
    AND (cast(:dataInicial as timestamp) IS NULL AND cast(:dataFinal as timestamp) IS NULL
        OR t.transactionDate BETWEEN :dataInicial AND :dataFinal)
    """)
    Page<TransacaoViewRecordDTO> findTransacoesWithFilters(
            Pageable pageable,
            @Param("produtoId") UUID produtoId,
            @Param("cidadeId") UUID cidadeId,
            @Param("moedaOrigemId") UUID moedaOrigemId,
            @Param("moedaDestinoId") UUID moedaDestinoId,
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("dataFinal") LocalDateTime dataFinal
    );

}
