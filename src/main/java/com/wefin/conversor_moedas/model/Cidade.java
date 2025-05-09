package com.wefin.conversor_moedas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * The Class Reino
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 08/05/2025
 */
@Entity
@Table(name = "tb_cidades")
@Getter
@Setter
public class Cidade implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String nome;
}
