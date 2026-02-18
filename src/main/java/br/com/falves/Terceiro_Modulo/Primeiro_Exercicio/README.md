# O Controlador de Verbos (GET vs POST)

**Objetivo:** Implementar o padrão Controller simplificado, criando uma lógica que diferencia a intenção do usuário (buscar ou salvar) no mesmo endereço.

**Cenário:** Você precisa gerenciar uma lista de nomes de convidados para um evento. O mesmo endpoint `/api/convidados`deve listar os nomes ou adicionar um novo.

**Tarefas:**

1. Crie uma classe `ConvidadoController` que implementa `HttpHandler`.
2. No método `handle`, capture o método da requisição usando `exchange.getRequestMethod()`.
3. **Lógica GET:** Se for "GET", retorne a lista de convidados em JSON.
4. **Lógica POST:** Se for "POST", simule o salvamento. Como ainda não leremos o corpo (body), apenas adicione um nome fixo à lista, imprima no console "Convidado adicionado" e retorne **Status 201 (Created)**.
5. Se for qualquer outro método (PUT, DELETE), retorne **Status 405 (Method Not Allowed)**.