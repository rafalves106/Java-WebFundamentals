# O Ecoador de Cabeçalhos (Headers e Protocolo)

**Objetivo:** Aprender a ler informações que o cliente envia silenciosamente e enviá-las de volta na resposta.

**Cenário:** Para depuração, você precisa de um endpoint que diga ao usuário qual o navegador ele está usando e qual o método HTTP da requisição.

**Tarefas:**

1. Crie o contexto `/api/debug`.
2. Dentro do método `handle`, utilize o objeto `HttpExchange` para extrair:
    - O método utilizado (`exchange.getRequestMethod()`).
    - O cabeçalho "User-Agent" enviado pelo navegador.
3. A resposta deve ser um texto formatado:

   > "Você acessou via [METODO] usando o navegador [USER-AGENT]".

4. Certifique-se de fechar o `OutputStream` corretamente.