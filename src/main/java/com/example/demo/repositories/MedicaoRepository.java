package com.example.demo.repositories;

import com.example.demo.entities.Medicao;
import com.example.demo.enums.StatusMedicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Adicione esta importação
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface MedicaoRepository extends JpaRepository<Medicao, Long> {
    Optional<Medicao> findByStatus(StatusMedicao status);
    Optional<Medicao> findByNumeroMedicao(String numeroMedicao);
    @Query("SELECT m FROM Medicao m JOIN FETCH m.orcamento ORDER BY m.id DESC")
    List<Medicao> findAllWithOrcamento();
    @Query("SELECT COUNT(m) FROM Medicao m WHERE m.orcamento.id = :orcamentoId")
    long countByOrcamentoId(@Param("orcamentoId") Long orcamentoId);
    boolean existsByOrcamentoIdAndStatus(Long orcamentoId, StatusMedicao status);
}