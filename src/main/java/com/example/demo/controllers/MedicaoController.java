package com.example.demo.controllers;

import com.example.demo.dtos.ApiResponse;
import com.example.demo.dtos.MedicaoRequestDTO;
import com.example.demo.entities.Medicao;
import com.example.demo.services.MedicaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicoes")
@SecurityRequirement(name = "bearerAuth")
public class MedicaoController {

    @Autowired
    private MedicaoService service;

    @PostMapping
    @Operation(summary = "Abre uma nova medição para um orçamento")
    public ResponseEntity<ApiResponse<Medicao>> criar(@RequestBody MedicaoRequestDTO dto) {
        Medicao criada = service.criarMedicao(dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Medição aberta com sucesso! Lembre-se de validá-la após a conferência.", criada));
    }

    @PatchMapping("/{id}/validar")
    @Operation(summary = "Valida a medição e atualiza o acumulado dos itens no orçamento")
    public ResponseEntity<ApiResponse<Medicao>> validar(@PathVariable Long id) {
        Medicao validada = service.validarMedicao(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Medição validada! O saldo dos itens no orçamento foi atualizado.", validada));
    }

    @GetMapping
    @Operation(summary = "Lista todas as medições cadastradas")
    public ResponseEntity<ApiResponse<List<Medicao>>> listarTodas() {
        List<Medicao> lista = service.listarTodas();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de medições carregada.", lista));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca detalhes de uma medição específica")
    public ResponseEntity<ApiResponse<Medicao>> buscarPorId(@PathVariable Long id) {
        Medicao medicao = service.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Detalhes da medição recuperados.", medicao));
    }
}