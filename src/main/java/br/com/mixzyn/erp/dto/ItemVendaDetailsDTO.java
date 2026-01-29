package br.com.mixzyn.erp.dto;

import java.math.BigDecimal;

public record ItemVendaDetailsDTO(Long idItem, Long idProduto, String descricao, int quantidade, BigDecimal precoUnitario, String imagePath) {
}