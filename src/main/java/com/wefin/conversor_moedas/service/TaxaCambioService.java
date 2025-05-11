package com.wefin.conversor_moedas.service;

import com.wefin.conversor_moedas.dto.ConsultaTaxaCambioRecordDTO;
import com.wefin.conversor_moedas.dto.TaxaCambioRecordDTO;
import com.wefin.conversor_moedas.model.TaxaCambio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Interface TaxaCambioService
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
public interface TaxaCambioService {

    TaxaCambio findTaxaById(UUID taxaCambioId);

    Page<TaxaCambio> findAllTaxas(Pageable pageable);

    void deleteTaxa(UUID taxaCambioId);

    Page<TaxaCambio> findAllTaxasByCidade(Pageable pageable,UUID cidadeId);

    TaxaCambio createTaxaCambio(TaxaCambioRecordDTO taxaCambioRecordDTO);

    TaxaCambio updateTaxaCambio(TaxaCambioRecordDTO taxaCambioRecordDTO);

    ConsultaTaxaCambioRecordDTO consultaCotacao(UUID taxaCambioId, BigDecimal valorEnviado, UUID moedaDoCliente);

}
