package br.com.mixzyn.erp.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public record ProdutoDto(String descricao, String codigo, BigDecimal precoUnitario, MultipartFile imagePath) {
    
}
