# O Padrão Controller e Roteamento Manual

Neste módulo, aprenderemos a organizar nossa aplicação seguindo padrões de arquitetura profissional. O foco é o **Roteamento Inteligente**: como fazer uma única URL se comportar de formas diferentes dependendo do que o usuário pede (GET ou POST) e como capturar dados que vêm na própria URL.

## 1. O que é um Controller?

O Controller é o "porteiro" da sua aplicação. Ele é a primeira classe a receber a requisição HTTP. Sua única responsabilidade é:

1. **Receber** a requisição.
2. **Extrair** os dados necessários (da URL ou do Corpo).
3. **Delegar** a tarefa para um Service ou Repository.
4. **Responder** ao cliente com o Status Code correto.

## 2. Diferenciando Verbos (GET vs POST)

Até agora, apenas "buscamos" dados (GET). Mas e se quisermos "enviar" um produto para o estoque? Dentro do seu `HttpHandler`, precisamos de uma estrutura de decisão baseada no método da requisição.

### 💻 Exemplo: Lógica de Verbos

```java
@Override
public void handle(HttpExchange exchange) throws IOException {
    // Captura o método (GET, POST, PUT, DELETE, etc)
    String metodo = exchange.getRequestMethod(); 

    if ("GET".equals(metodo)) {
        // Lógica para listar dados (Status 200)
        System.out.println("Usuário quer ler dados.");
    } else if ("POST".equals(metodo)) {
        // Lógica para receber e salvar dados (Status 201)
        System.out.println("Usuário quer cadastrar algo.");
    } else {
        // Retorna erro se o verbo não for suportado
        exchange.sendResponseHeaders(405, -1);
    }
}
```

## 3. Parâmetros de URL (Query Strings)

Às vezes queremos filtrar um dado específico, como `localhost:8080/api/produtos?id=1`. O servidor recebe a String completa e precisamos "quebrar" essa String para descobrir os valores.

### 💻 Exemplo: Quebrando a Query String

```java
// Supondo a URL: /api/produtos?id=1
String query = exchange.getRequestURI().getQuery(); // Retorna "id=1"

if (query != null && query.contains("id=")) {
    // Dividimos a string pelo sinal de igual e pegamos a posição 1 (o valor)
    String partes[] = query.split("=");
    String valorId = partes[1]; 
    
    int id = Integer.parseInt(valorId);
    System.out.println("Buscando produto com ID: " + id);
}
```

## 4. Injeção de Dependência Manual

Como ainda não temos o Spring Boot para fazer o `@Autowired`, aprenderemos a conectar nossas peças "no braço" através do construtor. Isso garante que o Controller tenha acesso ao que precisa sem precisar criar as instâncias internamente.

### 💻 Exemplo: Conexão das Peças

```java
// 1. Criamos a classe que gerencia os dados (Repository ou Service)
List<Produto> meuEstoque = new ArrayList<>();

// 2. Passamos essa lista para o construtor do Controller
// Isso é Injeção de Dependência Manual!
HttpHandler estoqueController = new EstoqueController(meuEstoque);

// 3. Registramos o Controller no HttpServer
server.createContext("/api/estoque", estoqueController);
```

## 5. Cuidados no Roteamento

Ao trabalhar com roteamento manual, a atenção deve ser redobrada:

- **Null Safety:** Sempre verifique se `getQuery()` não retornou nulo antes de processar.
- **Tipagem:** Lembre-se que tudo que vem da URL é `String`. Se precisar de um número, o `Integer.parseInt()` deve estar dentro de um bloco de tratamento de erro para evitar que o servidor caia se o usuário digitar letras no lugar de números.