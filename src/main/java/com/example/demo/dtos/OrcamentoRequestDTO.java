package com.example.demo.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrcamentoRequestDTO {
    private String numeroProtocolo;
    private String tipoOrcamento;
    private BigDecimal valorTotal;
    private List<ItemRequestDTO> itens;
}