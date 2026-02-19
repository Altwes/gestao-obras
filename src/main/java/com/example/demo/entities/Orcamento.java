package com.example.demo.entities;

import com.example.demo.enums.StatusOrcamento;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_orcamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroProtocolo;

    private String tipoOrcamento;

    private BigDecimal valorTotal;

    private LocalDate dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusOrcamento status = StatusOrcamento.ABERTO;

    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itens = new ArrayList<>();
}