package br.com.mixzyn.erp.dto;

import java.math.BigDecimal;

public record ItemVendaDetailsDTO(Long idProduto, String descricao, int quantidade, BigDecimal precoUnitario, String imagePath) {
}