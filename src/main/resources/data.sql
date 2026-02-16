-- 1. Criar Usuário Administrador (Senha: 123456)
INSERT INTO tb_usuario (login, senha, role) 
VALUES ('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnC', 'ROLE_ADMIN')
ON CONFLICT (login) DO NOTHING;

-- 2. Criar um Orçamento de Exemplo
INSERT INTO tb_orcamento (numero_protocolo, tipo_orcamento, valor_total, data_criacao, status) 
VALUES ('OBRA-2026-001', 'Reforma Escritório Central', 10000.00, CURRENT_DATE, 'ABERTO')
ON CONFLICT (numero_protocolo) DO NOTHING;

-- 3. Criar Itens para o Orçamento (apenas se a tabela de itens estiver vazia para evitar duplicatas simples)
INSERT INTO tb_item (descricao, quantidade, valor_unitario, valor_total, quantidade_acumulada, orcamento_id)
SELECT 'Piso Porcelanato 60x60', 100.0, 50.00, 5000.00, 0.0, 1
WHERE NOT EXISTS (SELECT 1 FROM tb_item WHERE descricao = 'Piso Porcelanato 60x60');

INSERT INTO tb_item (descricao, quantidade, valor_unitario, valor_total, quantidade_acumulada, orcamento_id)
SELECT 'Argamassa AC-III', 50.0, 30.00, 1500.00, 0.0, 1
WHERE NOT EXISTS (SELECT 1 FROM tb_item WHERE descricao = 'Argamassa AC-III');

-- 4. Criar uma Medição ABERTA
INSERT INTO tb_medicao (numero_medicao, data_medicao, status, valor_medicao, observacao, orcamento_id)
SELECT '1', CURRENT_DATE, 'ABERTA', 0.0, 'Medição inicial de nivelamento', 1
WHERE NOT EXISTS (SELECT 1 FROM tb_medicao WHERE numero_medicao = '1');