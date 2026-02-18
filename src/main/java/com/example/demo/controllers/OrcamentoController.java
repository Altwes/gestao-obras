package com.example.demo.controllers;

import com.example.demo.dtos.ApiResponse;
import com.example.demo.dtos.OrcamentoRequestDTO;
import com.example.demo.entities.Orcamento;
import com.example.demo.services.OrcamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orcamentos")
@SecurityRequirement(name = "bearerAuth")
public class OrcamentoController {

    @Autowired
    private OrcamentoService service;

    @PostMapping
    @Operation(summary = "Cria um novo orçamento com itens")
    public ResponseEntity<ApiResponse<Orcamento>> criar(@RequestBody OrcamentoRequestDTO dto) {
        Orcamento salvo = service.salvar(dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Orçamento criado com sucesso!", salvo));
    }

    @GetMapping
    @Operation(summary = "Lista todos os orçamentos")
    public ResponseEntity<ApiResponse<List<Orcamento>>> listarTodos() {
        List<Orcamento> lista = service.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de orçamentos recuperada.", lista));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca orçamento por ID")
    public ResponseEntity<ApiResponse<Orcamento>> buscarPorId(@PathVariable Long id) {
        Orcamento orcamento = service.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Orçamento encontrado.", orcamento));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edita um orçamento existente")
    public ResponseEntity<ApiResponse<Orcamento>> atualizar(@PathVariable Long id, @RequestBody OrcamentoRequestDTO dto) {
        Orcamento atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Orçamento atualizado com sucesso!", atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um orçamento")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Orçamento excluído com sucesso!", null));
    }
}