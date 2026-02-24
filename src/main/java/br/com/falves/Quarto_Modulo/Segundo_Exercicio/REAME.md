# Lendo a Mente do Cliente (Desserialização)

**Objetivo:** Evoluir a sua rota POST. Em vez de adicionar um livro com dados "fixos" no código (hardcoded), o seu servidor deve ler o JSON que o cliente envia no corpo da requisição e transformá-lo em um objeto Java.

### 📝 Passo a Passo:

1. **Lógica do POST:** Vá até o bloco `if (metodo.equals("POST"))` do seu Handler.
2. **Captura do Corpo (Body):**
    - Utilize o método `exchange.getRequestBody()` para obter o fluxo de dados que o usuário enviou (ex: via Insomnia).
3. **A Mágica da Desserialização:**
    - Use o Jackson para ler esse fluxo e convertê-lo diretamente para a sua classe: `Livro novoLivro = mapper.readValue(exchange.getRequestBody(), Livro.class);`
4. **Persistência Simula:** Adicione o `novoLivro` à sua lista: `repository.getLivros().add(novoLivro);`.
5. **Resposta de Sucesso:** Retorne o Status **201 (Created)** e envie o JSON do próprio livro criado de volta para o cliente como confirmação.