package com.wefin.conversor_moedas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Class Product
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 08/05/2025
 */
@Entity
@Table(name = "tb_produtos")
@Getter
@Setter
public class Produto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column( nullable = false)
    private BigDecimal preco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moeda_origem_id")
    private Moeda moeda;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_ajuste_id")
    private TaxaPersonalizada taxaPersonalizada;
}
