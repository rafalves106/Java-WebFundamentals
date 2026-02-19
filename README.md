## 1. A Arquitetura Cliente-Servidor

A Web funciona baseada em uma conversa. O **Cliente** (Navegador, Celular ou Postman) faz uma pergunta, e o **Servidor** (seu código Java) processa e envia uma resposta.

- **Request (Requisição):** Contém a URL, o Verbo (GET, POST, etc.) e, às vezes, dados (Body).
- **Response (Resposta):** Contém o Status Code (sucesso ou erro) e o conteúdo (texto, HTML ou JSON).

## 2. O Protocolo HTTP e Verbos

Para a comunicação ser padronizada, usamos o protocolo HTTP. Cada ação no servidor é definida por um "Verbo":

- **GET:** Usado para buscar informações (ex: listar produtos).
- **POST:** Usado para enviar novos dados (ex: cadastrar um usuário).
- **PUT/PATCH:** Usado para atualizar dados existentes.
- **DELETE:** Usado para remover informações.

## 3. Servidores e Handlers (HttpServer)

Diferente das seções anteriores onde o código rodava e parava, um **Servidor** precisa ficar em "loop infinito" ouvindo uma porta (como a 8080).

- **HttpServer:** A classe do Java que abre o canal de comunicação na rede.
- **Contextos (Routes):** São os caminhos da URL (ex: `/clientes`).
- **HttpHandler:** É a classe "trabalhadora". Para cada contexto, definimos um Handler que decide o que responder quando aquela rota for acessada.

## 4. Serialização e JSON

Computadores diferentes (um servidor Java e um celular Android, por exemplo) não compartilham objetos Java. Eles precisam de uma linguagem comum.

- **JSON (JavaScript Object Notation):** É o formato padrão da web hoje. É um texto que representa os dados do seu objeto.
- **Serialização:** O processo de transformar seu `Usuario usuario = new Usuario()` em um texto `{"nome": "Rafael"}`. Usamos bibliotecas como **Jackson** ou **Gson** para automatizar isso.

## 5. O Padrão Controller

À medida que a API cresce, não podemos colocar toda a lógica dentro do servidor. Introduzimos o conceito de **Controller**:

- É a classe responsável por "receber o impacto" da internet.
- Ela lê o que o usuário enviou, valida e chama o **Service** correto.
- Ela é a porta de entrada da sua aplicação, isolando as regras de negócio das complexidades do protocolo HTTP.

## 6. HTTP Status Codes (A Voz do Servidor)

O servidor não fala, ele usa códigos numéricos para dizer o que aconteceu:

- **200 (OK):** Tudo certo!
- **201 (Created):** Cadastro feito com sucesso.
- **400 (Bad Request):** O cliente enviou algo errado (ex: esqueceu o e-mail).
- **404 (Not Found):** Essa URL não existe.
- **500 (Internal Server Error):** O seu código Java deu erro (bug).