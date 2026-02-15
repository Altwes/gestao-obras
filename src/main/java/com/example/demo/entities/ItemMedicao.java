package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_item_medicao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMedicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal quantidadeMedida;
    private BigDecimal valorTotalMedido;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "medicao_id")
    private Medicao medicao;
}