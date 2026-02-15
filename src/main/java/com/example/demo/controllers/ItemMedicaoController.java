package com.example.demo.controllers;

import com.example.demo.dtos.ApiResponse;
import com.example.demo.entities.ItemMedicao;
import com.example.demo.services.ItemMedicaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itens-medicao")
@SecurityRequirement(name = "bearerAuth")
public class ItemMedicaoController {

    @Autowired
    private ItemMedicaoService service;

    @PostMapping("/medicao/{medicaoId}")
    @Operation(summary = "Cadastra um novo item em uma medição aberta")
    public ResponseEntity<ApiResponse<ItemMedicao>> criar(@PathVariable Long medicaoId, @RequestBody ItemMedicao novoItem) {
        ItemMedicao salvo = service.salvar(medicaoId, novoItem);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item da medição cadastrado!", salvo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edita a quantidade medida de um item (apenas se ABERTA)")
    public ResponseEntity<ApiResponse<ItemMedicao>> atualizar(@PathVariable Long id, @RequestBody ItemMedicao dadosNovos) {
        ItemMedicao atualizado = service.atualizar(id, dadosNovos);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item da medição atualizado!", atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um item de uma medição aberta")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item removido da medição.", null));
    }
}