# O Guardião de Erros (Status Codes e Exceções)

**Objetivo:** Praticar o controle manual de erros e a semântica dos Status Codes.

**Cenário:** Você tem um endpoint de segurança que só deve responder se o caminho for acessado exatamente de uma forma. Caso contrário, deve simular erros.

**Tarefas:**

1. Crie o contexto `/api/segredo`.
2. Implemente uma lógica de verificação:
    - Se o usuário acessar via **POST**, responda "Acesso concedido ao segredo!" com Status **200**.
    - Se o usuário acessar via **GET** (ou qualquer outro), responda "Método não permitido para esta operação" com Status **405 (Method Not Allowed)**.
3. Tente forçar um erro **500 (Internal Server Error)** caso ocorra qualquer falha inesperada na conversão de bytes.