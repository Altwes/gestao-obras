package com.example.demo.repositories;

import com.example.demo.entities.ItemMedicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemMedicaoRepository extends JpaRepository<ItemMedicao, Long> {
    List<ItemMedicao> findByItemId(Long itemId);
}