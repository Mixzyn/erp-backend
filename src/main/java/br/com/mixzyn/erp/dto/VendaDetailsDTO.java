package br.com.mixzyn.erp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendaDetailsDTO(Long id, LocalDateTime data, BigDecimal valorTotal, List<ItemVendaDetailsDTO> itens) {
}