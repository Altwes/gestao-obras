package com.example.demo.dtos;

import lombok.Data;
import java.util.List;

@Data
public class MedicaoRequestDTO {
    private String numeroMedicao;
    private Long orcamentoId;
    private String observacao;
    private List<ItemMedicaoRequestDTO> itens;
}