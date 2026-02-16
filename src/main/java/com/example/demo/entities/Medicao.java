package com.example.demo.entities;

import com.example.demo.enums.StatusMedicao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_medicao", uniqueConstraints = { @UniqueConstraint(columnNames = { "numeroMedicao", "orcamento_id" }) })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String numeroMedicao;

    private LocalDate dataMedicao;
    private BigDecimal valorMedicao;

    @Enumerated(EnumType.STRING)
    private StatusMedicao status = StatusMedicao.ABERTA;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "orcamento_id")
    private Orcamento orcamento;

    @OneToMany(mappedBy = "medicao", cascade = CascadeType.ALL)
    private List<ItemMedicao> itensMedicao;
}