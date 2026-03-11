# Projeto Épico da Seção: O Micro-SaaS de Gestão Financeira (Nativo)

**Objetivo:** Construir uma API robusta de controle financeiro que funcione de ponta a ponta. O projeto deve ser capaz de receber transações via Web, processar regras de negócio, persistir no banco de dados real e ser validado por testes automatizados.

---

### 📝 O Cenário

Você vai desenvolver o núcleo de uma aplicação de finanças. O sistema deve permitir o registro de receitas e despesas, calcular o saldo atual e garantir que transações inválidas não cheguem ao banco de dados. Tudo isso exposto através de um servidor HTTP nativo.

---

### 🚀 Requisitos Técnicos (A Integração Total)

### 1. Persistência (MongoDB)

- Utilize a biblioteca `mongodb-driver-sync`.
- Crie a entidade `Transacao` (id, descricao, valor, tipo [RECEITA/DESPESA], data).
- Implemente o `TransacaoRepository` para salvar e buscar dados do Mongo.

### 2. Regras de Negócio (Service + Testes)

- Crie o `FinanceiroService`.
- **Regra:** Não permitir transações com valor zero ou negativo.
- **Regra:** Ao deletar uma transação, verificar se ela existe.
- **Testes:** Você **DEVE** criar uma suíte de testes unitários com **JUnit e Mockito** para o `FinanceiroService`, mockando o repositório.

### 3. A Camada Web (HttpServer + Controller)

- Crie o `TransacaoController` que implementa `HttpHandler`.
- **Endpoints:**
    - `GET /transacoes`: Retorna todas as transações em JSON.
    - `GET /transacoes?tipo=RECEITA`: Filtra transações pelo tipo via Query Parameter.
    - `POST /transacoes`: Recebe uma transação (simule o recebimento dos dados).
- **Status Codes:** Use 200 para buscas, 201 para criados, 400 para erros de validação e 404 para não encontrado.

### 4. Clean Code e Organização

O projeto deve estar rigorosamente dividido em pacotes:

- `br.com.falves.api.controller`
- `br.com.falves.api.service`
- `br.com.falves.api.repository`
- `br.com.falves.api.model`
- `br.com.falves.api.exception`