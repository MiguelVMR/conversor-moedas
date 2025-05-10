package com.wefin.conversor_moedas.service;

import com.wefin.conversor_moedas.dto.CidadeRecordDTO;
import com.wefin.conversor_moedas.model.Cidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * The Interface ReinoService
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
public interface CidadeService {
    Cidade createCidade(CidadeRecordDTO cidadeRecordDTO);

    Cidade updateCidade(CidadeRecordDTO cidadeRecordDTO);

    void deleteCidade(UUID ciadeId);

    Cidade findCidadeById(UUID ciadeId);

    Page<Cidade> findAllCidades(Pageable pageable);
}
