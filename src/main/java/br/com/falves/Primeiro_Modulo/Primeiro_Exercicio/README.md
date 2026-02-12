# O Despertar do Servidor (Contextos Básicos)

**Objetivo:** Criar um servidor funcional que escute numa porta específica e responda mensagens diferentes para caminhos diferentes.

**Cenário:** Você precisa criar uma "página de informações" interna para o RH da empresa que mostre quem é o autor do sistema e qual a versão atual.

**Tarefas:**

1. Instancie um `HttpServer` na porta **8080**.
2. Crie dois contextos:
    - `/api/quem-sou-eu`: Deve retornar o seu nome completo.
    - `/api/versao`: Deve retornar "Versão do Sistema: 1.0-SNAPSHOT".
3. Implemente o `HttpHandler` garantindo que:
    - O Status Code enviado seja **200 (OK)**.
    - O cabeçalho `Content-Type` seja `text/plain`.