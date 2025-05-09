package com.wefin.conversor_moedas.repository;

import com.wefin.conversor_moedas.model.Moeda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * The Interface MoedaRepository
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
public interface MoedaRepository extends JpaRepository<Moeda, UUID> {

    Optional<Moeda> findByName(String name);
}
