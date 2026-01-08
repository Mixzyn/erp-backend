package br.com.mixzyn.erp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VendaResumoDTO(Long id, LocalDateTime data, BigDecimal valorTotal) {
}
