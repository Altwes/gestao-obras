package com.example.demo.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemMedicaoRequestDTO {
    private Long itemId;
    private BigDecimal quantidadeMedida;
}