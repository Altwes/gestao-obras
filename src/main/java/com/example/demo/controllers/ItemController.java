package com.example.demo.controllers;

import com.example.demo.dtos.ApiResponse;
import com.example.demo.entities.Item;
import com.example.demo.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itens")
@SecurityRequirement(name = "bearerAuth")
public class ItemController {

    @Autowired
    private ItemService service;

    @PostMapping("/orcamento/{orcamentoId}")
    @Operation(summary = "Adiciona um novo item a um orçamento existente")
    public ResponseEntity<ApiResponse<Item>> criar(@PathVariable Long orcamentoId, @RequestBody Item novoItem) {
        Item salvo = service.salvar(orcamentoId, novoItem);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item adicionado com sucesso!", salvo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edita as informações de um item")
    public ResponseEntity<ApiResponse<Item>> atualizar(@PathVariable Long id, @RequestBody Item itemAtualizado) {
        Item salvo = service.atualizar(id, itemAtualizado);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item atualizado com sucesso!", salvo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um item do orçamento")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item removido com sucesso!", null));
    }
}