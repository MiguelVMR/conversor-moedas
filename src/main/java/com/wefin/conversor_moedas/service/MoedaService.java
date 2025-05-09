package com.wefin.conversor_moedas.service;

import com.wefin.conversor_moedas.dto.MoedaRecordDTO;
import com.wefin.conversor_moedas.model.Moeda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * The Interface MoedaService
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
public interface MoedaService {
    Moeda createMoeda(MoedaRecordDTO moedaRecordDTO);

    Moeda updateMoeda(MoedaRecordDTO moedaRecordDTO);

    void deleteMoeda(UUID moedaId);

    Moeda findMoedaById(UUID moedaId);

    Page<Moeda> findAllMoedas(Pageable pageable);
}
