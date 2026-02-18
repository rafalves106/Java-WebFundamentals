# O Simulador de API de Estoque

**Objetivo:** Unificar o roteamento do Módulo 1 com a entrega de dados estruturados do Módulo 2, simulando uma consulta real de banco de dados.

### 📝 Requisitos do Projeto

1. **Contexto Principal:** `/api/estoque`.
2. **Lógica de Dados:**
    - Crie uma "Base de Dados" fictícia (uma `List<Produto>` estática).
    - Se a lista estiver vazia, o servidor deve retornar **Status 404** com a mensagem `"Nenhum produto em estoque"`.
    - Se a lista contiver produtos, o servidor deve retornar **Status 200** e o JSON contendo todos os produtos.
3. **Formatação Especial:**
    - Utilize `StringBuilder` para montar o JSON de forma mais organizada e performática antes de enviar para o `OutputStream`.