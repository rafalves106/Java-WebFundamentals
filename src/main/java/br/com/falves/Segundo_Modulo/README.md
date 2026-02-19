# A Linguagem da Web (JSON e Status Codes)

## 1. O que é JSON e por que usá-lo?

JSON é um formato leve de troca de dados, fácil de ler para humanos e fácil de processar para máquinas.

- **Estrutura:** Baseado em chave e valor `{ "chave": "valor" }`.
- **Independência:** Um servidor em Java pode enviar um JSON para um cliente escrito em Python ou JavaScript sem problemas de compatibilidade.

### 💻 Exemplo: A Classe Modelo

Para gerar um JSON, primeiro precisamos de um objeto bem estruturado.

```java
public class Produto {
    private int id;
    private String nome;
    private double preco;
    // Construtores, Getters e Setters (ou use Lombok)
}
```

## 2. Serialização Manual e Bibliotecas

Como o Java não gera JSON nativamente de forma automática, temos dois caminhos:

- **Manual:** Concatenar strings (ex: `"{ \"nome\": \"" + user.getNome() + "\" }"`). **Atenção:** Isso é propenso a erros e difícil de manter.
- **Bibliotecas (Jackson/Gson):** Usamos bibliotecas profissionais que fazem o "mapeamento" (Mapping). Elas leem os atributos da sua classe e geram o JSON automaticamente.

### 💻 Exemplo: Montando um Objeto em JSON

```java
// No seu Handler
Produto p = new Produto(1, "Notebook", 3500.0);

// Montando a String JSON manualmente
// Resultado esperado: {"id": 1, "nome": "Notebook", "preco": 3500.0}
String json = "{" +
    "\"id\": " + p.getId() + "," +
    "\"nome\": \"" + p.getNome() + "\"," +
    "\"preco\": " + p.getPreco() +
"}";
```

## 3. HTTP Status Codes (A Semântica da Web)

Não basta responder o dado; é preciso dizer *como* a requisição foi processada.

- **2xx (Sucesso):** 200 (OK), 201 (Created).
- **4xx (Erro do Cliente):** 400 (Bad Request - dados inválidos), 404 (Not Found).
- **5xx (Erro do Servidor):** 500 (Internal Server Error - seu código quebrou).

## 4. O Header application/json

Diferente do texto puro (`text/plain`), o JSON exige um cabeçalho específico para que o navegador ou o Insomnia saibam como formatar os dados.

### 💻 Configuração do Header

```java
// Sempre defina ANTES do sendResponseHeaders
exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
```