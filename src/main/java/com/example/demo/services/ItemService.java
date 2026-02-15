package com.example.demo.services;

import com.example.demo.entities.Item;
import com.example.demo.entities.Orcamento;
import com.example.demo.enums.StatusOrcamento;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Transactional
    public Item salvar(Long orcamentoId, Item novoItem) {
        Orcamento orcamento = orcamentoRepository.findById(orcamentoId)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));

        if (StatusOrcamento.FINALIZADO.equals(orcamento.getStatus())) {
            throw new RuntimeException("Não é permitido incluir itens em um orçamento FINALIZADO.");
        }

        novoItem.setValorTotal(novoItem.getQuantidade().multiply(novoItem.getValorUnitario()));

        BigDecimal somaAtual = orcamento.getItens().stream()
                .map(Item::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (somaAtual.add(novoItem.getValorTotal()).compareTo(orcamento.getValorTotal()) > 0) {
            throw new RuntimeException("A soma dos itens ultrapassaria o valor total do orçamento!");
        }

        novoItem.setQuantidadeAcumulada(BigDecimal.ZERO);
        novoItem.setOrcamento(orcamento);
        return repository.save(novoItem);
    }

    @Transactional
    public Item atualizar(Long id, Item dadosNovos) {
        Item itemExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if (StatusOrcamento.FINALIZADO.equals(itemExistente.getOrcamento().getStatus())) {
            throw new RuntimeException("Não é permitido editar item de orçamento FINALIZADO.");
        }

        itemExistente.setDescricao(dadosNovos.getDescricao());
        itemExistente.setQuantidade(dadosNovos.getQuantidade());
        itemExistente.setValorUnitario(dadosNovos.getValorUnitario());
        itemExistente.setValorTotal(dadosNovos.getQuantidade().multiply(dadosNovos.getValorUnitario()));

        return repository.save(itemExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if (StatusOrcamento.FINALIZADO.equals(item.getOrcamento().getStatus())) {
            throw new RuntimeException("Não é permitido excluir item de orçamento FINALIZADO.");
        }

        repository.delete(item);
    }
}