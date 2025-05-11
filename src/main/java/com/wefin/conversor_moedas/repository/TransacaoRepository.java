package com.wefin.conversor_moedas.repository;

import com.wefin.conversor_moedas.dto.TransacaoViewRecordDTO;
import com.wefin.conversor_moedas.model.Transacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
