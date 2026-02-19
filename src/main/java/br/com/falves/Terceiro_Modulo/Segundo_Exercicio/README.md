# O Capturador de Query Strings (?id=X)

**Objetivo:** Aprender a extrair parâmetros de consulta da URL e utilizá-los para filtrar dados no seu repositório fictício.

**Cenário:** O usuário quer buscar os detalhes de um livro específico em uma biblioteca digital passando o ID na URL, por exemplo: `/api/livros?id=2`.

**Tarefas:**

1. Crie uma lista estática de 3 livros (id, titulo, autor).
2. No Handler, use `exchange.getRequestURI().getQuery()` para capturar a string de consulta.
3. **Desafio de Lógica:** Crie um método utilitário para "quebrar" essa String (ex: de `id=2` extrair apenas o número `2`).
4. Busque o livro correspondente na lista:
    - Se achar: Retorne o JSON do livro (**Status 200**).
    - Se não achar: Retorne `{"erro": "Livro nao encontrado"}` (**Status 404**).