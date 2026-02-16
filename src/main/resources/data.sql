-- 1. Usuário Admin (Senha: 123456)
INSERT INTO tb_usuario (login, senha, role) VALUES ('admin','$2a$10$T3AKqPn1fNjuxpqxl1OrauvBcQ0GGuLsRfEKxAd7WNdEtqedS57Vy','ROLE_ADMIN');

-- 2. Orçamento (ID será 1)
INSERT INTO tb_orcamento (numero_protocolo, tipo_orcamento, valor_total, data_criacao, status) 
VALUES ('OBRA-2026-001','Reforma Escritório Central',10000.00,CURRENT_DATE,'ABERTO');

-- 3. Itens (Vinculados ao ID 1 do orçamento acima)
INSERT INTO tb_item (descricao, quantidade, valor_unitario, valor_total, quantidade_acumulada, orcamento_id) 
VALUES ('Piso Porcelanato 60x60',100.0,50.00,5000.00,0.0,1);

INSERT INTO tb_item (descricao, quantidade, valor_unitario, valor_total, quantidade_acumulada, orcamento_id) 
VALUES ('Argamassa AC-III', 50.0, 30.00, 1500.00, 0.0, 1);

-- 4. Medição (Vinculada ao ID 1 do orçamento acima)
INSERT INTO tb_medicao (numero_medicao, data_medicao, status, valor_medicao, observacao, orcamento_id) 
VALUES ('1', CURRENT_DATE, 'ABERTA', 0.0, 'Medição inicial de nivelamento', 1);