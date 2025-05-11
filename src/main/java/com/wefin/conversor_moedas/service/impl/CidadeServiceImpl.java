package com.wefin.conversor_moedas.service.impl;

import com.wefin.conversor_moedas.dto.CidadeRecordDTO;
import com.wefin.conversor_moedas.exception.BusinessException;
import com.wefin.conversor_moedas.exception.ConflictException;
import com.wefin.conversor_moedas.model.Cidade;
import com.wefin.conversor_moedas.repository.CidadeRepository;
import com.wefin.conversor_moedas.service.CidadeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
* The Class CidadeServiceImpl
*
* @author Miguel Vilela Moraes Ribeiro
* @since 09/05/2025
*/
@Service
public class CidadeServiceImpl implements CidadeService {

    private final CidadeRepository cidadeRepository;

    public CidadeServiceImpl(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    @Override
    public Cidade createCidade(CidadeRecordDTO cidadeRecordDTO) {
        var cidadeDb = cidadeRepository.findByNome(cidadeRecordDTO.name());
        if (cidadeDb.isPresent()) {
            throw new ConflictException("Já Existe uma Cidade com este nome!");
        }
        var cidade = new Cidade();
        cidade.setNome(cidadeRecordDTO.name());
        cidadeRepository.save(cidade);

        return cidade;
    }

    @Override
    public Cidade updateCidade(CidadeRecordDTO cidadeRecordDTO) {
        var cidadeDb = cidadeRepository.findById(cidadeRecordDTO.id())
                .orElseThrow(() -> BusinessException.entityNotFound("Cidade",cidadeRecordDTO.id().toString()));

        cidadeDb.setNome(cidadeRecordDTO.name());

        return cidadeDb;
    }

    @Override
    public void deleteCidade(UUID cidadeId) {
        cidadeRepository.deleteById(cidadeId);
    }

    @Override
    public Cidade findCidadeById(UUID cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> BusinessException.entityNotFound("Cidade",cidadeId.toString()));
    }

    @Override
    public Page<Cidade> findAllCidades(Pageable pageable) {
        return cidadeRepository.findAll(pageable);
    }
}
