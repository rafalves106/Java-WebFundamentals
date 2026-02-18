# O Validador de Recursos (HTTP 404 e 400)

**Objetivo:** Praticar o uso de diferentes Status Codes para indicar situações de erro ao cliente.

**Cenário:** Você vai simular uma busca por produto onde o ID pode ou não existir.

**Tarefas:**

1. Crie o contexto `/api/produto-detalhe`.
2. Simule uma lógica de busca:
    - Se o ID consultado (você pode fixar um ID para teste) for encontrado, retorne o JSON do produto e **Status 200**.
    - Se o ID for inválido (ex: um número negativo), retorne uma mensagem de erro em JSON `{"erro": "ID inválido"}`e **Status 400 (Bad Request)**.
    - Se o ID for válido mas o produto não existir, retorne **Status 404 (Not Found)**.
3. **Desafio:** Tente usar o `exchange.sendResponseHeaders` com o tamanho correto do corpo para cada cenário.

### 📝 O que observar nestes exercícios:

- **Aspas Escapadas:** No Java, para colocar aspas dentro de uma String, você usará `\"`. Ex: `"{\"id\": 1}"`.
- **Content-Type:** Se você esquecer esse cabeçalho, o navegador vai baixar o arquivo ou exibir como texto sujo, em vez de processar como dados.
- **Tamanho do Body:** Lembre-se que o método `sendResponseHeaders` precisa do número de **bytes** da String, e não necessariamente o número de caracteres (embora para o alfabeto padrão sejam iguais).