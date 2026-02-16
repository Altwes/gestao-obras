package com.example.demo.repositories;

import com.example.demo.entities.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    boolean existsByNumeroProtocolo(String numeroProtocolo);
}