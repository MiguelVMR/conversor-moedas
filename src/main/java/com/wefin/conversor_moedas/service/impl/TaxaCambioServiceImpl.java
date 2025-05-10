package com.wefin.conversor_moedas.service.impl;

import com.wefin.conversor_moedas.dto.TaxaCambioRecordDTO;
import com.wefin.conversor_moedas.enums.ErrorType;
import com.wefin.conversor_moedas.exception.BusinessException;
import com.wefin.conversor_moedas.exception.NotFoundException;
import com.wefin.conversor_moedas.model.Cidade;
import com.wefin.conversor_moedas.model.Moeda;
import com.wefin.conversor_moedas.model.Produto;
import com.wefin.conversor_moedas.model.TaxaCambio;
import com.wefin.conversor_moedas.repository.TaxaCambioRepository;
import com.wefin.conversor_moedas.service.CidadeService;
import com.wefin.conversor_moedas.service.MoedaService;
import com.wefin.conversor_moedas.service.TaxaCambioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * The Class TaxaCambioServiceImpl
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
@Service
public class TaxaCambioServiceImpl implements TaxaCambioService {
    private final TaxaCambioRepository taxaCambioRepository;
    private final CidadeService cidadeService;
    private final MoedaService moedaService;

    public TaxaCambioServiceImpl(TaxaCambioRepository taxaCambioRepository, CidadeService cidadeService, MoedaService moedaService) {
        this.taxaCambioRepository = taxaCambioRepository;
        this.cidadeService = cidadeService;
        this.moedaService = moedaService;
    }

    @Override
    public TaxaCambio findTaxaById(UUID taxaCambioId) {
        return taxaCambioRepository.findById(taxaCambioId)
                .orElseThrow(() -> new NotFoundException("Taxa de Cambio não encontrada!"));
    }

    @Override
    public Page<TaxaCambio> findAllTaxas(Pageable pageable) {
        return taxaCambioRepository.findAll(pageable);
    }

    @Override
    public void deleteTaxa(UUID taxaCambioId) {
        taxaCambioRepository.deleteById(taxaCambioId);
    }

    @Override
    public Page<TaxaCambio> findAllTaxasByCidade(Pageable pageable, UUID cidadeId) {
        cidadeService.findCidadeById(cidadeId);

        Page<TaxaCambio> taxas = taxaCambioRepository.findAllByCidade(pageable, cidadeId);

        if (taxas.isEmpty()) {
            throw new NotFoundException("Não foram encontradas taxas de câmbio para esta cidade!");
        }

        return taxas;
    }

    @Override
    public TaxaCambio createTaxaCambio(TaxaCambioRecordDTO taxaCambioRecordDTO) {
        vailaMoedas(taxaCambioRecordDTO);

        var moedaOrigem = moedaService.findMoedaById(taxaCambioRecordDTO.moedaOrigem());
        var moedaDestino = moedaService.findMoedaById(taxaCambioRecordDTO.moedaDestino());
        var cidade = cidadeService.findCidadeById(taxaCambioRecordDTO.cidade());

        var taxaCambio = new TaxaCambio();
        taxaCambio.setTaxa(taxaCambioRecordDTO.taxa());
        taxaCambio.setDate(LocalDate.now());
        taxaCambio.setMoedaOrigem(moedaOrigem);
        taxaCambio.setMoedaDestino(moedaDestino);
        taxaCambio.setCidade(cidade);

        return taxaCambioRepository.save(taxaCambio);
    }

    private void vailaMoedas(TaxaCambioRecordDTO taxaCambioRecordDTO) {
        if (taxaCambioRecordDTO.moedaOrigem() != null && taxaCambioRecordDTO.moedaDestino() != null &&
            Objects.equals(taxaCambioRecordDTO.moedaOrigem(), taxaCambioRecordDTO.moedaDestino())) {
            throw BusinessException.invalidData("Moeda de Origem e Destino",
                    "Moeda de origem e destino não podem ser iguais!");
        }
    }

    @Override
    public TaxaCambio updateTaxaCambio(TaxaCambioRecordDTO taxaCambioRecordDTO) {
        vailaMoedas(taxaCambioRecordDTO);

        try {
            TaxaCambio taxa = findTaxaById(taxaCambioRecordDTO.id());

            taxa = atualizarMoeda(taxa, taxaCambioRecordDTO.moedaOrigem(), "origem", TaxaCambio::setMoedaOrigem);
            taxa = atualizarMoeda(taxa, taxaCambioRecordDTO.moedaDestino(), "destino", TaxaCambio::setMoedaDestino);
            taxa = atualizarTaxa(taxa, taxaCambioRecordDTO.taxa());
            taxa = atualizarCidade(taxa, taxaCambioRecordDTO.cidade());

            taxa.setDate(LocalDate.now());

            return taxaCambioRepository.save(taxa);

        } catch (NotFoundException e) {
            throw BusinessException.entityNotFound("Taxa de Câmbio", taxaCambioRecordDTO.id().toString());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(
                    "Falha ao atualizar taxa de câmbio",
                    ErrorType.UNEXPECTED_ERROR,
                    e.getMessage(),
                    e
            );
        }
    }

    private TaxaCambio atualizarMoeda(TaxaCambio taxa, UUID moedaId, String tipoMoeda,
                                      BiConsumer<TaxaCambio, Moeda> setter) {
        if (moedaId != null) {
            try {
                Moeda moeda = moedaService.findMoedaById(moedaId);
                setter.accept(taxa, moeda);
            } catch (NotFoundException e) {
                throw new BusinessException(
                        String.format("Moeda de %s não encontrada: %s", tipoMoeda, moedaId)
                );
            }
        }
        return taxa;
    }

    private TaxaCambio atualizarCidade(TaxaCambio taxa, UUID cidadeId) {
        if (cidadeId != null) {
            try {
                Cidade cidade = cidadeService.findCidadeById(cidadeId);
                taxa.setCidade(cidade);
            } catch (NotFoundException e) {
                throw BusinessException.entityNotFound("Cidade", cidadeId.toString());
            }
        }
        return taxa;
    }

    private TaxaCambio atualizarTaxa(TaxaCambio taxa, BigDecimal novoValor) {
        if (novoValor != null) {
            taxa.setTaxa(novoValor);
        }
        return taxa;
    }

}
