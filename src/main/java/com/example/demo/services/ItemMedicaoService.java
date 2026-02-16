package com.example.demo.services;

import com.example.demo.entities.ItemMedicao;
import com.example.demo.entities.Medicao;
import com.example.demo.enums.StatusMedicao;
import com.example.demo.repositories.ItemMedicaoRepository;
import com.example.demo.repositories.MedicaoRepository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemMedicaoService {

    @Autowired
    private ItemMedicaoRepository repository;

    @Autowired
    private MedicaoRepository medicaoRepository;

    @Transactional
    public ItemMedicao salvar(Long medicaoId, ItemMedicao novoItem) {
        Medicao medicao = medicaoRepository.findById(medicaoId)
                .orElseThrow(() -> new RuntimeException("Medição não encontrada"));
        if (StatusMedicao.VALIDADA.equals(medicao.getStatus())) {
            throw new RuntimeException("Não é permitido incluir itens em uma medição VALIDADA.");
        }
        novoItem.setValorTotalMedido(
                novoItem.getItem().getValorUnitario().multiply(novoItem.getQuantidadeMedida()));
        novoItem.setMedicao(medicao);
        ItemMedicao salvo = repository.save(novoItem);
        recalcularTotalMedicao(medicao);
        return salvo;
    }

    @Transactional
    public ItemMedicao atualizar(Long id, ItemMedicao dadosNovos) {
        ItemMedicao itemMedExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item da medição não encontrado"));
        if (StatusMedicao.VALIDADA.equals(itemMedExistente.getMedicao().getStatus())) {
            throw new RuntimeException("Não é permitido editar itens de uma medição já VALIDADA!");
        }
        itemMedExistente.setQuantidadeMedida(dadosNovos.getQuantidadeMedida());
        itemMedExistente.setValorTotalMedido(
                itemMedExistente.getItem().getValorUnitario().multiply(dadosNovos.getQuantidadeMedida()));
        ItemMedicao atualizado = repository.save(itemMedExistente);
        recalcularTotalMedicao(atualizado.getMedicao());
        return atualizado;
    }

    @Transactional
    public void deletar(Long id) {
        ItemMedicao itemMed = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item da medição não encontrado"));
        if (StatusMedicao.VALIDADA.equals(itemMed.getMedicao().getStatus())) {
            throw new RuntimeException("Não é permitido excluir itens de uma medição VALIDADA!");
        }
        Medicao medicao = itemMed.getMedicao();
        medicao.getItensMedicao().remove(itemMed);
        repository.delete(itemMed);
        recalcularTotalMedicao(medicao);
    }

    private void recalcularTotalMedicao(Medicao medicao) {
        BigDecimal novoTotalGeral = medicao.getItensMedicao().stream()
                .map(ItemMedicao::getValorTotalMedido)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        medicao.setValorMedicao(novoTotalGeral);
        medicaoRepository.save(medicao);
    }
}