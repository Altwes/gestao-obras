-- 1. Criar Usuário Administrador (Senha: 123456 - criptografada em BCrypt)
-- Nota: Verifique se sua tabela se chama tb_usuarios ou usuario conforme seu código
INSERT INTO tb_usuarios (login, senha, role) VALUES ('admin', '$2a$10$wS4.MvP1B6s.6U1m4y4Pe.Oq5P9vXyR.R0X0G0X0G0X0G0X0G0X0G', 'ROLE_ADMIN');

-- 2. Criar um Orçamento de Exemplo (Status ABERTO)
INSERT INTO tb_orcamento (numero_protocolo, tipo_orcamento, valor_total, data_criacao, status) 
VALUES ('OBRA-2026-001', 'Reforma Escritório Central', 10000.00, CURRENT_DATE, 'ABERTO');

-- 3. Criar Itens para o Orçamento (Vinculados ao orcamento_id = 1)
-- Regra: Valor Total = Quantidade * Valor Unitario
INSERT INTO tb_item (descricao, quantidade, valor_unitario, valor_total, quantidade_acumulada, orcamento_id) 
VALUES ('Piso Porcelanato 60x60', 100.0, 50.00, 5000.00, 0.0, 1);

INSERT INTO tb_item (descricao, quantidade, valor_unitario, valor_total, quantidade_acumulada, orcamento_id) 
VALUES ('Argamassa AC-III', 50.0, 30.00, 1500.00, 0.0, 1);

-- 4. Criar uma Medição ABERTA para teste (Vinculada ao orcamento_id = 1)
INSERT INTO tb_medicao (numero_medicao, data_medicao, status, valor_medicao, observacao, orcamento_id) 
VALUES (1, CURRENT_DATE, 'ABERTA', 0.0, 'Medição inicial de nivelamento', 1);