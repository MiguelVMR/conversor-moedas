package com.wefin.conversor_moedas.service;

import com.wefin.conversor_moedas.dto.TransacaoRecordDTO;
import com.wefin.conversor_moedas.dto.TransacaoViewRecordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The Interface TrasacaoService
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 10/05/2025
 */
public interface TrasacaoService {

    TransacaoViewRecordDTO createTransacao(TransacaoRecordDTO transacaoRecordDTO, JwtAuthenticationToken jwtToken);

    TransacaoViewRecordDTO findTransacaoById(UUID id);

    Page<TransacaoViewRecordDTO> findTransacoesWithFilters(Pageable pageable,
                                                           UUID produtoId,
                                                           UUID cidadeId,
                                                           UUID moedaOrigemId,
                                                           UUID moedaDestinoId,
                                                           LocalDate dataInicial,
                                                           LocalDate dataFinal);

}
