# 🏗️ Gestão de Obras - API de Orçamentos e Medições

Sistema desenvolvido para o controlo rigoroso de orçamentos de engenharia, permitindo o acompanhamento de itens orçados versus quantidades medidas no terreno. O projeto garante a integridade financeira e o fluxo de validação de medições.

## 🚀 Funcionalidades Principais

* **Gestão de Orçamentos:** Cadastro de protocolos únicos, tipos de obra e valores globais.
* **Controlo de Itens:** Gestão de materiais/serviços com cálculo automático de valor total e validação de teto orçamental.
* **Ciclo de Medição:** Acompanhamento do progresso da obra com trava de segurança para não ultrapassar o orçado.
* **Segurança:** Autenticação via JWT (JSON Web Token) e controlo de acesso por perfis (Roles).
* **Documentação Interativa:** Interface Swagger para testes rápidos e visualização dos endpoints.

## 🛠️ Tecnologias Utilizadas

* **Java 17** & **Spring Boot 3**
* **Spring Security** & **JWT** (Autenticação e Autorização)
* **Spring Data JPA** (Persistência de dados com PostgreSQL/H2)
* **SpringDoc OpenAPI (Swagger)** (Documentação da API)
* **Maven** (Gestor de dependências)

## 📋 Regras de Negócio Implementadas

1.  **Integridade de Valores:** A soma dos itens nunca pode ultrapassar o valor total definido no orçamento.
2.  **Trava de Edição:** Orçamentos com status `FINALIZADO` não permitem alterações em itens ou medições.
3.  **Controlo de Medição:** Não é permitido mais de uma medição com status `ABERTA` simultaneamente por orçamento.
4.  **Acumulado Automático:** Ao validar uma medição, o sistema atualiza automaticamente a quantidade acumulada no item do orçamento pai e valida se o limite foi excedido.

## 🚦 Como Executar o Projeto

1.  Clone o repositório:
    ```bash
    git clone [https://github.com/Altwes/gestao-obras.git](https://github.com/Altwes/gestao-obras.git)
    ```
2.  Instale as dependências:
    ```bash
    mvn clean install
    ```
3.  Execute a aplicação:
    ```bash
    mvn spring-boot:run
    ```

## 📖 Documentação (Swagger)

Com a aplicação em execução, aceda à documentação através do link:
`http://localhost:8080/swagger-ui/index.html`

> **Nota:** Para testar os endpoints protegidos, utilize o login `admin` e senha `123456` (carregados automaticamente via `import.sql`).

---
Desenvolvido por **Wesley** para a apresentação de SOP-CE.