package com.example.demo.services;

import com.example.demo.dtos.MedicaoRequestDTO;
import com.example.demo.entities.*;
import com.example.demo.enums.StatusMedicao;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MedicaoService {

    @Autowired
    private MedicaoRepository repository;

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public Medicao criarMedicao(MedicaoRequestDTO dto) {
        repository.findByStatus(StatusMedicao.ABERTA).ifPresent(m -> {
            throw new RuntimeException("Não será permitido mais de uma medição aberta!");
        });

        Orcamento orcamento = orcamentoRepository.findById(dto.getOrcamentoId())
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
        long proximoNumero = repository.countByOrcamentoId(dto.getOrcamentoId()) + 1;
        Medicao medicao = new Medicao();
        medicao.setNumeroMedicao(String.valueOf(proximoNumero));
        medicao.setOrcamento(orcamento);
        medicao.setDataMedicao(LocalDate.now());
        medicao.setObservacao(dto.getObservacao());
        medicao.setStatus(StatusMedicao.ABERTA);

        List<ItemMedicao> itensMedicao = dto.getItens().stream().map(iDto -> {
            Item itemOriginal = itemRepository.findById(iDto.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item do orçamento não encontrado"));

            ItemMedicao im = new ItemMedicao();
            im.setItem(itemOriginal);
            im.setQuantidadeMedida(iDto.getQuantidadeMedida());
            im.setMedicao(medicao);
            im.setValorTotalMedido(itemOriginal.getValorUnitario().multiply(iDto.getQuantidadeMedida()));
            return im;
        }).collect(Collectors.toList());

        medicao.setItensMedicao(itensMedicao);

        BigDecimal valorTotalMedicao = itensMedicao.stream()
                .map(ItemMedicao::getValorTotalMedido)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        medicao.setValorMedicao(valorTotalMedicao);

        return repository.save(medicao);
    }

    @Transactional
    public Medicao validarMedicao(Long id) {
        Medicao medicao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medição não encontrada"));

        if (medicao.getStatus() == StatusMedicao.VALIDADA) {
            throw new RuntimeException("Esta medição já foi validada.");
        }

        for (ItemMedicao itemMed : medicao.getItensMedicao()) {
            Item itemOriginal = itemMed.getItem();

            BigDecimal novaQuantidadeAcumulada = itemOriginal.getQuantidadeAcumulada()
                    .add(itemMed.getQuantidadeMedida());

            if (novaQuantidadeAcumulada.compareTo(itemOriginal.getQuantidade()) > 0) {
                throw new RuntimeException("Erro: A quantidade medida do item [" + itemOriginal.getDescricao()
                        + "] ultrapassa o total do orçamento!");
            }

            itemOriginal.setQuantidadeAcumulada(novaQuantidadeAcumulada);
            itemRepository.save(itemOriginal);
        }

        medicao.setStatus(StatusMedicao.VALIDADA);
        return repository.save(medicao);
    }

    public List<Medicao> listarTodas() {
        return repository.findAllWithOrcamento();
    }

    public Medicao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medição não encontrada"));
    }

    public long obterProximoNumero(Long orcamentoId) {
        return repository.countByOrcamentoId(orcamentoId) + 1;
    }
}