package com.wefin.conversor_moedas.service.impl;

import com.wefin.conversor_moedas.dto.MoedaRecordDTO;
import com.wefin.conversor_moedas.exception.ConflictException;
import com.wefin.conversor_moedas.exception.NotFoundException;
import com.wefin.conversor_moedas.model.Moeda;
import com.wefin.conversor_moedas.repository.MoedaRepository;
import com.wefin.conversor_moedas.service.MoedaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The Class MoedaServiceImpl
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
@Service
public class MoedaServiceImpl implements MoedaService {

    private final MoedaRepository moedaRepository;

    public MoedaServiceImpl(MoedaRepository moedaRepository) {
        this.moedaRepository = moedaRepository;
    }

    @Override
    public Moeda createMoeda(MoedaRecordDTO moedaRecordDTO) {
        var moedaDb = moedaRepository.findByName(moedaRecordDTO.name());
        if (moedaDb.isPresent()) {
            throw new ConflictException("Já Existe uma Moeda com este nome!");
        }
        var moeda = new Moeda();
        moeda.setName(moedaRecordDTO.name());
        moeda.setSymbol(moedaRecordDTO.symbol());
        moedaRepository.save(moeda);

        return moeda;
    }

    @Override
    public Moeda updateMoeda(MoedaRecordDTO moedaRecordDTO) {
        var moedaDb = moedaRepository.findById(moedaRecordDTO.id())
                .orElseThrow(() -> new NotFoundException("Moeda não encontrada!"));

        moedaDb.setName(moedaRecordDTO.name());
        moedaDb.setSymbol(moedaRecordDTO.symbol());
        moedaRepository.save(moedaDb);

        return moedaDb;
    }

    @Override
    public void deleteMoeda(UUID moedaId) {
        moedaRepository.deleteById(moedaId);
    }

    @Override
    public Moeda findMoedaById(UUID moedaId){
        return moedaRepository.findById(moedaId)
                .orElseThrow(() -> new NotFoundException("Moeda não encontrada!"));
    }

    @Override
    public Page<Moeda> findAllMoedas(Pageable pageable){
        return moedaRepository.findAll(pageable);
    }

}
