package com.example.demo.repositories;

import com.example.demo.entities.Medicao;
import com.example.demo.enums.StatusMedicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MedicaoRepository extends JpaRepository<Medicao, Long> {
    Optional<Medicao> findByStatus(StatusMedicao status);
    Optional<Medicao> findByNumeroMedicao(String numeroMedicao);
}