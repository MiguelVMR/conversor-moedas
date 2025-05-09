package com.wefin.conversor_moedas.model;

import com.wefin.conversor_moedas.enums.TipoAjuste;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Class TaxaPersonalizada
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 08/05/2025
 */
@Entity
@Table(name = "tb_taxa_personalizada")
@Getter
@Setter
public class TaxaPersonalizada implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ajuste", length = 25)
    private TipoAjuste tipoAjuste;
}
