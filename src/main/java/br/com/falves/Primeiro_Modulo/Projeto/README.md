# O Despertar do Servidor Nativo

**Objetivo:** Criar um servidor HTTP funcional utilizando apenas as bibliotecas nativas do JDK, demonstrando o entendimento do ciclo de vida de uma requisição (Request) e resposta (Response), roteamento por contextos e manipulação de fluxos de saída (Streams).

### 🏢 Cenário

Você foi contratado para criar um "Monitor de Sistema Via Web". O objetivo é que qualquer pessoa na rede da empresa possa acessar o IP do servidor e verificar informações básicas do sistema sem precisar abrir o terminal.

---

### 📝 Requisitos Técnicos (O que você deve fazer)

### 1. Classe `MonitorServer`

Esta será a sua classe principal. No método `main`, você deve:

- Instanciar um `HttpServer` na porta **8080**.
- Definir um `Executor` nulo (`server.setExecutor(null)`) para usar o gerenciamento padrão.
- Iniciar o servidor com `server.start()`.

### 2. Roteamento (Contextos)

Você deve registrar três caminhos (URIs) diferentes, cada um com sua lógica:

- **Contexto `/api/status`**: Deve retornar uma mensagem simples: `"Servidor Online - Java Nativo"`.
- **Contexto `/api/data`**: Deve retornar a data e hora atual do sistema utilizando `LocalDateTime`.
- **Contexto `/api/sistema`**: Deve retornar informações básicas da sua máquina, como a versão do Java e o Sistema Operacional (Dica: Use `System.getProperty("java.version")` e `System.getProperty("os.name")`).

### 3. Implementação do `HttpHandler`

Crie uma classe (ou classes) que implemente a interface `HttpHandler`. Dentro do método `handle(HttpExchange exchange)`, você **DEVE** seguir este fluxo:

- **Configuração de Headers:** Defina o `Content-Type` como `text/plain; charset=utf-8` para garantir a leitura correta de acentos.
- **Envio do Status:** Use `sendResponseHeaders(200, resposta.length())`.
- **Escrita do Corpo:** Utilize o `OutputStream` do objeto `exchange` para enviar a String de resposta convertida em bytes.
- **Fechamento:** Garanta que o stream de resposta seja fechado para não deixar a conexão "pendurada".

### 4. Tratamento de Erros

Certifique-se de que, se o usuário acessar uma rota que não existe, o servidor retorne automaticamente o código **404**. (O `HttpServer` já faz isso por padrão para contextos não registrados, mas tente entender como você enviaria um erro 500 manualmente se algo desse errado no seu código).

---

### 🚀 Desafio Extra (Opcional):

Adicione um contador de acessos global. Toda vez que qualquer uma das rotas for acessada, incremente um número e exiba-o junto com a resposta: `"Acessos desde o início: [X]"`.