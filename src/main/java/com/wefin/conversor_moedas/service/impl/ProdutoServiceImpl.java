package com.wefin.conversor_moedas.service.impl;

import com.wefin.conversor_moedas.dto.ProdutoRecordDTO;
import com.wefin.conversor_moedas.dto.TaxaPersonalizadaRecordDTO;
import com.wefin.conversor_moedas.enums.ErrorType;
import com.wefin.conversor_moedas.exception.BusinessException;
import com.wefin.conversor_moedas.exception.NotFoundException;
import com.wefin.conversor_moedas.model.Cidade;
import com.wefin.conversor_moedas.model.Moeda;
import com.wefin.conversor_moedas.model.Produto;
import com.wefin.conversor_moedas.model.TaxaPersonalizada;
import com.wefin.conversor_moedas.repository.ProdutoRepository;
import com.wefin.conversor_moedas.service.CidadeService;
import com.wefin.conversor_moedas.service.MoedaService;
import com.wefin.conversor_moedas.service.ProducoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * The Class ProdutoServiceImpl
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
@Service
public class ProdutoServiceImpl implements ProducoService {

    private final ProdutoRepository produtoRepository;
    private final CidadeService cidadeService;
    private final MoedaService moedaService;

    public ProdutoServiceImpl(ProdutoRepository repository, CidadeService cidadeService, MoedaService moedaService) {
        this.produtoRepository = repository;
        this.cidadeService = cidadeService;
        this.moedaService = moedaService;
    }

    @Override
    public Produto findProdutoById(UUID produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new NotFoundException("Produto não encontrada!"));
    }

    @Override
    public Page<Produto> findAllProdutos(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    @Override
    public Page<Produto> findAllProdutosByCidade(Pageable pageable, UUID cidadeId) {
        cidadeService.findCidadeById(cidadeId);

        Page<Produto> produtos = produtoRepository.findAllByCidade(pageable, cidadeId);

        if (produtos.isEmpty()) {
            throw new NotFoundException("Não foram encontrados produtos para esta cidade!");
        }

        return produtos;
    }

    @Override
    public Page<Produto> buscaPorFiltros(UUID cidadeId, String nome, UUID moedaId, Boolean temAjuste, Pageable pageable) {
        return produtoRepository.findAllByCidadeWithFilters(
                pageable,
                cidadeId,
                nome,
                moedaId,
                temAjuste
        );
    }

    @Override
    public Produto createProduto(ProdutoRecordDTO produtoRecordDTO) {
        var moeda = moedaService.findMoedaById(produtoRecordDTO.moeda());
        var cidade = cidadeService.findCidadeById(produtoRecordDTO.cidade());
        var produto = new Produto();

        if (produtoRecordDTO.taxaPersonalizada() != null) {
            validarTaxaPersonalizada(produtoRecordDTO.taxaPersonalizada());
            produto.setTaxaPersonalizada(new TaxaPersonalizada(produtoRecordDTO.taxaPersonalizada()));
        }

        produto.setNome(produtoRecordDTO.nome());
        produto.setPreco(produtoRecordDTO.preco());
        produto.setDescricao(produtoRecordDTO.descricao());
        produto.setMoeda(moeda);
        produto.setCidade(cidade);

        return produtoRepository.save(produto);
    }

    @Override
    public Produto updateProduto(ProdutoRecordDTO produtoRecordDTO) {
        try {
            Produto produto = findProdutoById(produtoRecordDTO.id());

            produto = atualizarMoeda(produto, produtoRecordDTO.moeda());
            produto = atualizarPropriedades(produto, produtoRecordDTO);
            produto = atualizarTaxaPersonalizaxa(produto, produtoRecordDTO.taxaPersonalizada());
            produto = atualizarCidade(produto, produtoRecordDTO.cidade());

            return produtoRepository.save(produto);

        } catch (NotFoundException e) {
            throw BusinessException.entityNotFound("Produto", produtoRecordDTO.id().toString());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(
                    "Falha ao atualizar produto",
                    ErrorType.UNEXPECTED_ERROR
            );
        }
    }

    @Override
    public void deleteProduto(UUID produtoId) {
        produtoRepository.deleteById(produtoId);
    }

    private Produto atualizarTaxaPersonalizaxa(Produto produto, TaxaPersonalizadaRecordDTO taxaPersonalizadaRecordDTO) {
        if (taxaPersonalizadaRecordDTO != null) {
            validarTaxaPersonalizada(taxaPersonalizadaRecordDTO);

            if (produto.getTaxaPersonalizada() == null) {
                produto.setTaxaPersonalizada(new TaxaPersonalizada(taxaPersonalizadaRecordDTO));
            } else {
                produto.getTaxaPersonalizada().updateProperties(taxaPersonalizadaRecordDTO);
            }
        }
        return produto;
    }

    private void validarTaxaPersonalizada(TaxaPersonalizadaRecordDTO dto) {
        if (dto.valor() == null) {
            throw BusinessException.invalidData("Valor da Taxa", "O valor da taxa personalizada não pode ser nulo");
        }
        if (dto.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw BusinessException.invalidData("Valor da Taxa", "O valor da taxa personalizada deve ser maior que zero");
        }
        if (dto.tipoAjuste() == null) {
            throw BusinessException.invalidData("Tipo de Ajuste", "O tipo de ajuste da taxa personalizada não pode ser nulo");
        }
    }

    private Produto atualizarMoeda(Produto produto, UUID moedaId) {
        if (moedaId != null) {
            try {
                Moeda moeda = moedaService.findMoedaById(moedaId);
                produto.setMoeda(moeda);
            } catch (NotFoundException e) {
                throw BusinessException.entityNotFound("Moeda", moedaId.toString());
            }
        }
        return produto;
    }


    private Produto atualizarCidade(Produto produto, UUID cidadeId) {
        if (cidadeId != null) {
            try {
                Cidade cidade = cidadeService.findCidadeById(cidadeId);
                produto.setCidade(cidade);
            } catch (NotFoundException e) {
                throw BusinessException.entityNotFound("Cidade", cidadeId.toString());
            }
        }
        return produto;
    }

    private Produto atualizarPropriedades(Produto produto, ProdutoRecordDTO dto) {
        if (dto.nome() != null) {
            produto.setNome(dto.nome());
        }
        if (dto.descricao() != null) {
            produto.setDescricao(dto.descricao());
        }
        if (dto.preco() != null) {
            produto.setPreco(dto.preco());
        }
        return produto;
    }

}
