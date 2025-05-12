package com.wefin.conversor_moedas.service.impl;

import com.wefin.conversor_moedas.dto.TransacaoRecordDTO;
import com.wefin.conversor_moedas.dto.TransacaoViewRecordDTO;
import com.wefin.conversor_moedas.enums.ErrorType;
import com.wefin.conversor_moedas.enums.TipoAjuste;
import com.wefin.conversor_moedas.exception.BusinessException;
import com.wefin.conversor_moedas.model.Produto;
import com.wefin.conversor_moedas.model.Transacoes;
import com.wefin.conversor_moedas.repository.TransacaoRepository;
import com.wefin.conversor_moedas.service.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The Class TransacaoServiceImpl
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
@Service
public class TransacaoServiceImpl implements TrasacaoService {
    private final TransacaoRepository transacaoRepository;
    private final UsuarioService usuarioService;
    private final ProducoService producoService;
    private final MoedaService moedaService;
    private final TaxaCambioService taxaCambioService;

    public TransacaoServiceImpl(TransacaoRepository transacaoRepository, UsuarioService usuarioService, ProducoService producoService, MoedaService moedaService, TaxaCambioService taxaCambioService) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioService = usuarioService;
        this.producoService = producoService;
        this.moedaService = moedaService;
        this.taxaCambioService = taxaCambioService;
    }

    @Transactional(
            rollbackFor = Exception.class,
            isolation = Isolation.SERIALIZABLE,
            timeout = 30
    )
    public TransacaoViewRecordDTO createTransacao(TransacaoRecordDTO transacaoRecordDTO, JwtAuthenticationToken jwtToken) {
        var usuario = usuarioService.findById(UUID.fromString(jwtToken.getName()));
        var produto = producoService.findProdutoById(transacaoRecordDTO.produtoId());
        var moedaOrigem = moedaService.findMoedaById(transacaoRecordDTO.moedaOrigemId());

        var transacao = new Transacoes();
        transacao.setUsuario(usuario);
        transacao.setProduto(produto);
        transacao.setQuantidade(transacaoRecordDTO.quantidade());
        transacao.setMoedaOrigem(moedaOrigem);
        transacao.setMoedaDestino(produto.getMoeda());

        BigDecimal valorFinalProduto = calcularValorProdutoComTaxa(produto);
        BigDecimal valorTotal = valorFinalProduto.multiply(transacaoRecordDTO.quantidade());

        if (moedaOrigem.getId().equals(produto.getMoeda().getId())) {
            transacao.setValorOriginal(valorTotal);
            transacao.setValorConvertido(valorTotal);
        } else {
            var taxaCambio = taxaCambioService.findTaxaById(transacaoRecordDTO.taxaCambioId());
            transacao.setTaxaCambio(taxaCambio);

            BigDecimal valorConvertido;
            if (taxaCambio.getMoedaOrigem().equals(produto.getMoeda())) {

                if (!taxaCambio.getMoedaDestino().getId().equals(moedaOrigem.getId())) {
                    throw BusinessException.businessRule("Taxa de cambio inválida para esta operação");
                }

                valorConvertido = valorTotal.multiply(taxaCambio.getTaxa());
            } else {

                if (!taxaCambio.getMoedaOrigem().getId().equals(moedaOrigem.getId())) {
                    throw BusinessException.businessRule("Taxa de cambio inválida para esta operação");
                }
                valorConvertido = valorTotal.divide(taxaCambio.getTaxa(), 2, RoundingMode.HALF_UP);
            }

            transacao.setValorOriginal(valorTotal);
            transacao.setValorConvertido(valorConvertido);
        }

        BigDecimal valorDado = transacaoRecordDTO.valorDadoPeloCliente();
        BigDecimal valorASerPago = transacao.getValorConvertido();

        if (valorDado.compareTo(valorASerPago) < 0) {
            throw BusinessException.insufficientFunds(valorDado, valorASerPago);
        }

        BigDecimal troco = valorDado.subtract(valorASerPago);
        transacao.setValorTroco(troco);
        Transacoes transacaoSalva = transacaoRepository.save(transacao);

        return new TransacaoViewRecordDTO(
                transacaoSalva.getId(),
                transacaoSalva.getMoedaOrigem().getName(),
                transacaoSalva.getMoedaDestino().getName(),
                transacaoSalva.getValorOriginal(),
                transacaoSalva.getValorOriginal().divide(transacaoSalva.getQuantidade(), 2, RoundingMode.HALF_UP),
                transacaoSalva.getQuantidade(),
                transacaoSalva.getValorConvertido(),
                transacaoSalva.getValorTroco(),
                transacaoSalva.getValorConvertido().add(transacaoSalva.getValorTroco()),
                transacaoSalva.getTransactionDate()
        );

    }

    @Override
    public TransacaoViewRecordDTO findTransacaoById(UUID id) {
        return transacaoRepository.findTransacaoViewById(id)
                .orElseThrow(() -> BusinessException.entityNotFound("Transação", id.toString()));
    }


    private BigDecimal calcularValorProdutoComTaxa(Produto produto) {
        BigDecimal valorInicial = produto.getPreco();

        if (produto.getTaxaPersonalizada() == null) {
            return valorInicial;
        }

        BigDecimal taxaAjuste = produto.getTaxaPersonalizada().getValor();

        if (produto.getTaxaPersonalizada().getTipoAjuste().equals(TipoAjuste.ADICIONAL)) {
            BigDecimal adicional = valorInicial.multiply(taxaAjuste);
            return valorInicial.add(adicional);
        } else {
            BigDecimal desconto = valorInicial.multiply(taxaAjuste);
            return valorInicial.subtract(desconto);
        }
    }

    @Override
    public Page<TransacaoViewRecordDTO> findTransacoesWithFilters(
            Pageable pageable,
            UUID produtoId,
            UUID cidadeId,
            UUID moedaOrigemId,
            UUID moedaDestinoId,
            LocalDate dataInicial,
            LocalDate dataFinal) {

        LocalDateTime dataInicio = null;
        LocalDateTime dataFim = null;

        if (dataInicial != null && dataFinal != null) {
            if (dataInicial.isAfter(dataFinal)) {
                throw new BusinessException("Data inicial não pode ser posterior à data final");
            }
            dataInicio = dataInicial.atStartOfDay();
            dataFim = dataFinal.atTime(23, 59, 59, 999999999);
        } else if (dataInicial != null || dataFinal != null) {
            throw new BusinessException("Data inicial e final devem ser fornecidas juntas");
        }


        return transacaoRepository.findTransacoesWithFilters(
                pageable,
                produtoId,
                cidadeId,
                moedaOrigemId,
                moedaDestinoId,
                dataInicio,
                dataFim
        );
    }
}
