package com.example.demo.services;

import com.example.demo.dtos.OrcamentoRequestDTO;
import com.example.demo.entities.Orcamento;
import com.example.demo.repositories.MedicaoRepository;
import com.example.demo.entities.Item;
import com.example.demo.enums.StatusMedicao;
import com.example.demo.enums.StatusOrcamento;
import com.example.demo.repositories.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class OrcamentoService {

    @Autowired
    private OrcamentoRepository repository;
    @Autowired
    private MedicaoRepository medicaoRepository;
    
    @Transactional
    public Orcamento salvar(OrcamentoRequestDTO dto) {
        if (repository.existsByNumeroProtocolo(dto.getNumeroProtocolo())) {
        throw new RuntimeException("Já existe um orçamento cadastrado com o protocolo: " + dto.getNumeroProtocolo());
    }
        validarSomaItens(dto);

        Orcamento orcamento = new Orcamento();
        orcamento.setNumeroProtocolo(dto.getNumeroProtocolo());
        orcamento.setTipoOrcamento(dto.getTipoOrcamento());
        orcamento.setValorTotal(dto.getValorTotal());
        orcamento.setDataCriacao(LocalDate.now());
        orcamento.setStatus(StatusOrcamento.ABERTO);

        mapItens(dto, orcamento);

        return repository.save(orcamento);
    }

    @Transactional
    public Orcamento atualizar(Long id, OrcamentoRequestDTO dto) {
        Orcamento orcamentoExistente = buscarPorId(id);
        if (StatusOrcamento.FINALIZADO.equals(orcamentoExistente.getStatus())) {
            throw new RuntimeException("Este orçamento está FINALIZADO e não permite mais alterações.");
        }
        if ("FINALIZADO".equals(dto.getStatus())) {
            boolean temMedicaoAberta = medicaoRepository.existsByOrcamentoIdAndStatus(id, StatusMedicao.ABERTA);
            if (temMedicaoAberta) {
                throw new RuntimeException("Não é permitido finalizar o orçamento enquanto houver medições com status ABERTA.");
            }
        }
        validarSomaItens(dto);
        orcamentoExistente.setNumeroProtocolo(dto.getNumeroProtocolo());
        orcamentoExistente.setTipoOrcamento(dto.getTipoOrcamento());
        orcamentoExistente.setValorTotal(dto.getValorTotal());
        if (dto.getStatus() != null) {
            try {
                orcamentoExistente.setStatus(StatusOrcamento.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Status inválido: " + dto.getStatus());
            }
        }
        orcamentoExistente.getItens().clear();
        mapItens(dto, orcamentoExistente);
        return repository.save(orcamentoExistente);
    }

    public List<Orcamento> listarTodos() {
        return repository.findAll();
    }

    public Orcamento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado com ID: " + id));
    }

    @Transactional
    public void deletar(Long id) {
        Orcamento orcamento = buscarPorId(id);
        if (StatusOrcamento.FINALIZADO.equals(orcamento.getStatus())) {
            throw new RuntimeException("Não é permitido excluir um orçamento FINALIZADO.");
        }
        repository.delete(orcamento);
    }

    private void validarSomaItens(OrcamentoRequestDTO dto) {
        BigDecimal somaItens = dto.getItens().stream()
                .map(i -> i.getValorUnitario().multiply(i.getQuantidade()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (somaItens.compareTo(dto.getValorTotal()) != 0) {
            throw new RuntimeException("A soma dos valores dos itens (" + somaItens +
                    ") não coincide com o valor total do orçamento (" + dto.getValorTotal() + ")!");
        }
    }

    private void mapItens(OrcamentoRequestDTO dto, Orcamento orcamento) {
        List<Item> itens = dto.getItens().stream().map(iDto -> {
            Item item = new Item();
            item.setDescricao(iDto.getDescricao());
            item.setQuantidade(iDto.getQuantidade());
            item.setValorUnitario(iDto.getValorUnitario());
            item.setValorTotal(iDto.getValorUnitario().multiply(iDto.getQuantidade()));
            item.setQuantidadeAcumulada(BigDecimal.ZERO);
            item.setOrcamento(orcamento);
            return item;
        }).collect(Collectors.toList());

        orcamento.getItens().addAll(itens);
    }
}