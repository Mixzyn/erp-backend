package br.com.mixzyn.erp.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_produto")
public class Produto extends AbstractEntity {

    @Column(nullable = false, length = 200)
    private String descricao;

    @Column(unique = true, nullable = false, length = 50)
    private String codigo;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

/*     @Column(length = 100)
    private String categoria;

    @Column(length = 100)
    private String fabricante; */
}