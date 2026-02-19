# Protocolo HTTP e Servidores Nativos

Para entender como o Spring Boot ou o Quarkus funcionam, primeiro precisamos entender o que o Java faz por debaixo dos panos para transformar uma aplicação local em um **Web Service**. Neste módulo, utilizaremos as ferramentas nativas do JDK para criar nosso primeiro servidor real.

## 1. A Anatomia de uma Requisição HTTP

Quando você digita uma URL no navegador, ele envia um pacote de texto para o servidor. Esse pacote tem três partes essenciais:

- **Start Line:** Contém o **Método** (GET, POST, etc), a **URI** (`/api/usuarios`) e a **Versão do Protocolo** (HTTP/1.1).
- **Headers (Cabeçalhos):** Metadados sobre a requisição (ex: `Content-Type: application/json` diz que o que está vindo é um JSON).
- **Body (Corpo):** Os dados reais enviados (usado principalmente em POST e PUT).

---

## 2. Java HttpServer: O Servidor Embutido

Desde o Java 1.6, o JDK inclui um servidor HTTP simples mas funcional no pacote `com.sun.net.httpserver`.

### Conceitos Chave:

- **HttpServer:** É o motor principal. Ele abre o socket em uma porta específica (ex: 8080) e gerencia as conexões de entrada.
- **Context (Contexto):** É o mapeamento de um caminho (Path). Você diz ao servidor: "Quando alguém acessar `/api/produtos`, use esta lógica específica".
- **HttpHandler:** É uma interface com o método `handle(HttpExchange exchange)`. É aqui que a mágica acontece. Toda vez que o contexto é acessado, o Java chama esse método.

### 💻 Exemplo Prático: Configurando o Servidor

```java
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;

public class ServidorApp {
    public static void main(String[] args) throws IOException {
        // Criamos o servidor ouvindo na porta 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Criamos o contexto e associamos ao nosso Handler (a lógica)
        server.createContext("/api/teste", new MeuHandler());

        // Executor nulo usa o gerenciamento de threads padrão do Java
        server.setExecutor(null);

        // Inicia o servidor
        server.start();
        System.out.println("Servidor iniciado na porta 8080...");
    }
}
```

## 3. O Fluxo de Resposta (HttpExchange)

Toda a interação com o cliente acontece através do objeto `HttpExchange`. Para responder ao cliente, seguimos este fluxo obrigatório:

1. **Headers:** Definimos o tipo de conteúdo (Texto, JSON, HTML).
2. **Status Code:** Enviamos o código (200 para OK, 404 para Não Encontrado).
3. **Response Body:** Escrevemos a resposta no `OutputStream`.
4. **Close:** Fechamos o stream para liberar a conexão.

### 💻 Exemplo Prático: Implementando o Handler

```java
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.io.IOException;

public class MeuHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String resposta = "Ola! Este e um servidor Java Nativo.";

        // 1. Configurar o Header (UTF-8 garante acentuação correta)
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");

        // 2. Enviar Status Code 200 e o tamanho da resposta em bytes
        exchange.sendResponseHeaders(200, resposta.getBytes().length);

        // 3. Escrever o corpo da resposta no Stream
        OutputStream os = exchange.getResponseBody();
        os.write(resposta.getBytes());

        // 4. Fechar o Stream (Obrigatório!)
        os.close();
    }
}
```

## 4. O Fluxo de Resposta (HttpExchange)

| `create(InetSocketAddress, backlog)` | Cria a instância do servidor em uma porta específica. |
| --- | --- |
| `createContext(path, handler)` | Vincula uma URL a uma classe de processamento. |
| `setExecutor(executor)` | Define como o servidor lida com múltiplas requisições (threads). |
| `start()` | Inicia o servidor e o coloca em modo de escuta. |
| `stop(delay)` | Finaliza o servidor de forma segura. |

## 5. Cuidados no Baixo Nível

Ao trabalhar sem frameworks, somos responsáveis por detalhes que o Spring Boot costuma esconder:

- **Gerenciamento de Erros:** Se seu código lançar uma exceção e você não capturar, o cliente pode ficar esperando "para sempre" (Timeout). Use blocos `try-catch` dentro do `handle`.
- **Encoding:** É necessário garantir que o texto seja enviado como **UTF-8** para não quebrar acentos e caracteres especiais.
- **Recursos:** Streams de entrada e saída **devem** ser fechados manualmente (`os.close()`) para evitar vazamento de memória (Memory Leak).