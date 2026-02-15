package com.example.demo.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemRequestDTO {
    private String descricao;
    private BigDecimal quantidade;
    private BigDecimal valorUnitario;
}