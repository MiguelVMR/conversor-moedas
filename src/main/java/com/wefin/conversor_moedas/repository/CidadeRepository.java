package com.wefin.conversor_moedas.repository;

import com.wefin.conversor_moedas.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * The Interface CidadeRepository
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
public interface CidadeRepository extends JpaRepository<Cidade, UUID> {
    Optional<Cidade> findByNome(String name);
}
