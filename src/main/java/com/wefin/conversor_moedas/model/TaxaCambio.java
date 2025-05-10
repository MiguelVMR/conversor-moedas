package com.wefin.conversor_moedas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * The Class ExchangeRate
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 08/05/2025
 */
@Entity
@Table(name = "tb_taxas_cambio")
@Getter
@Setter
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "TaxaCambio.completo",
                attributeNodes = {
                        @NamedAttributeNode("moedaOrigem"),
                        @NamedAttributeNode("moedaDestino"),
                        @NamedAttributeNode("cidade")
                }
        )
})
public class TaxaCambio implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "moeda_origem_id")
    private Moeda moedaOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "moeda_destino_id")
    private Moeda moedaDestino;

    @Column(nullable = false)
    private BigDecimal taxa;

    @Column(nullable = false)
    private LocalDate date;

    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;
}
