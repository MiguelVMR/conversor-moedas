package com.wefin.conversor_moedas.service.impl;

import com.wefin.conversor_moedas.dto.CidadeRecordDTO;
import com.wefin.conversor_moedas.exception.ConflictException;
import com.wefin.conversor_moedas.exception.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException("Cidade não encontrada!"));

        cidadeDb.setNome(cidadeRecordDTO.name());

        return cidadeDb;
    }

    @Override
    public void deleteCidade(UUID moedaId) {
        cidadeRepository.deleteById(moedaId);
    }

    @Override
    public Cidade findCidadeById(UUID moedaId) {
        return cidadeRepository.findById(moedaId)
                .orElseThrow(() -> new NotFoundException("Cidade não encontrada!"));
    }

    @Override
    public Page<Cidade> findAllCidades(Pageable pageable) {
        return cidadeRepository.findAll(pageable);
    }
}
