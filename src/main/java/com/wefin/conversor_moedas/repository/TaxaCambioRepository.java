package com.wefin.conversor_moedas.repository;

import com.wefin.conversor_moedas.model.TaxaCambio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * The Interface TaxaCambioRepository
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
public interface TaxaCambioRepository extends JpaRepository<TaxaCambio, UUID> {

    @EntityGraph(value = "TaxaCambio.completo")
    Page<TaxaCambio> findAll(Pageable pageable);

    @EntityGraph(value = "TaxaCambio.completo")
    Optional<TaxaCambio> findById(UUID id);

    @Query("""
        SELECT DISTINCT t FROM TaxaCambio t 
        INNER JOIN FETCH t.moedaOrigem 
        INNER JOIN FETCH t.moedaDestino 
        INNER JOIN FETCH t.cidade 
        WHERE t.cidade.id = :cidadeId
        """)
    Page<TaxaCambio> findAllByCidade(Pageable pageable, @Param("cidadeId") UUID cidadeId);

    @Query("""
            SELECT DISTINCT t FROM TaxaCambio t 
            INNER JOIN FETCH t.moedaOrigem mo
            INNER JOIN FETCH t.moedaDestino md
            WHERE mo.id = :moedaOrigemId AND md.id = :moedaDestinoId           
            """)
    Optional<TaxaCambio> findMoedaOrigemAndMoedaDestino(@Param("moedaOrigemId") UUID moedaOrigemId, @Param("moedaDestinoId") UUID moedaDestinoId);

}
