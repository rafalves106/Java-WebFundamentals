# **Automação e Serialização de Dados (Jackson)**

Até agora, construímos nossos retornos JSON no "braço", usando `StringBuilder` para concatenar chaves, valores, aspas duplas e vírgulas. Embora seja um excelente exercício didático, no mundo real isso é insustentável. Qualquer campo novo em uma classe exigiria reescrever a lógica de formatação.

Neste módulo, introduzimos bibliotecas profissionais para fazer o **Data Binding** (Mapeamento de Dados). A mais famosa no ecossistema Java, e a mesma utilizada nativamente pelo Spring Boot, é o **Jackson**.

## 1. O que é o Jackson e o ObjectMapper?

O Jackson é uma biblioteca que automatiza a conversão entre Objetos Java e texto JSON. O coração dessa biblioteca é a classe `ObjectMapper`.

O `ObjectMapper` funciona como um tradutor bidirecional:

- **Serialização (Saída):** Lê os atributos de um objeto Java (usando os Getters) e escreve uma String JSON válida.
- **Desserialização (Entrada):** Lê uma String JSON vinda do cliente e cria um objeto Java correspondente (usando o construtor vazio e os Setters).

## 2. Configurando o Jackson no Projeto

A forma padrão de adicionar bibliotecas externas no Java moderno é através de um gerenciador de dependências, como o **Maven**. Ao utilizar o Maven, basta adicionar o seguinte trecho no arquivo `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.2</version> </dependency>
</dependencies>
```

## 3. Serialização: Transformando Java em JSON (Saída)

Em vez de dezenas de linhas de `StringBuilder`, usamos apenas um método: `writeValueAsString()`. O Jackson cuida das aspas, das listas e até da formatação de datas (se configurado).

### 💻 Exemplo Prático: Substituindo o StringBuilder

```java
import com.fasterxml.jackson.databind.ObjectMapper;

// Dentro do seu Handler:
ObjectMapper mapper = new ObjectMapper();

// Cria um objeto ou busca do Banco/Lista
Livro meuLivro = new Livro(1L, "Clean Code", "Robert C. Martin");

// A mágica: Transforma o objeto em JSON em 1 linha
String jsonResposta = mapper.writeValueAsString(meuLivro);

// Prepara e envia a resposta
exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
exchange.sendResponseHeaders(200, jsonResposta.getBytes().length);

OutputStream os = exchange.getResponseBody();
os.write(jsonResposta.getBytes());
os.close();
```

## 4. Desserialização: Lendo JSON do Cliente (Entrada)

A verdadeira força de uma API REST é **receber** dados complexos. Quando o usuário faz um POST, ele não envia as informações na URL, ele envia no **Body (Corpo)** da requisição em formato JSON. O Jackson consegue ler esse fluxo de dados e transformá-lo diretamente em um objeto Java usando o método `readValue()`.

### 💻 Exemplo Prático: Recebendo um Objeto via POST

```java
if ("POST".equals(exchange.getRequestMethod())) {
    ObjectMapper mapper = new ObjectMapper();

    // Lê o corpo da requisição (InputStream) e transforma no objeto Livro
    Livro livroRecebido = mapper.readValue(exchange.getRequestBody(), Livro.class);

    // Agora você pode usar o objeto Java normalmente!
    System.out.println("O cliente enviou o livro: " + livroRecebido.getNome());
    
    // Adiciona ao banco/lista e retorna sucesso...
}
```

## 5. Dicas de Ouro e Configurações Visuais

Por padrão, o Jackson gera o JSON em uma única linha para economizar banda de rede (o que chamamos de *minificado*). Para facilitar a leitura humana (no Insomnia ou no console), podemos habilitar o **Pretty Print** (Impressão Bonita).

### 💻 Exemplo: Configurando a Indentação

```java
ObjectMapper mapper = new ObjectMapper();

// Diz ao Jackson para usar "Enter" e "Tab" ao gerar a String JSON
mapper.enable(SerializationFeature.INDENT_OUTPUT);

String jsonBonito = mapper.writeValueAsString(minhaListaDeLivros);
```