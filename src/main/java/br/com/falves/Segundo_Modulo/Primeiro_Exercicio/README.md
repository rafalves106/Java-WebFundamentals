# O Conversor Manual (Entendendo a Sintaxe JSON)

**Objetivo:** Compreender a estrutura de um objeto JSON e como montá-lo manualmente usando `StringBuilder` antes de usar bibliotecas automáticas.

**Cenário:** Você precisa criar uma API que retorne os dados de um usuário logado.

**Tarefas:**

1. Crie uma classe `Usuario` simples (id, nome, email).
2. No seu Handler, instancie um usuário fixo.
3. Use um **`StringBuilder`** para montar a String do JSON manualmente seguindo o padrão: `{"id": 1, "nome": "Rafael", "email": "rafa@email.com"}`.
4. **Obrigatório:** Defina o Header `Content-Type` como `application/json`.
5. Retorne o JSON com o Status **200 (OK)**.