# Catálogo da Loja (API REST Completa)

**Objetivo:** Construir uma API REST 100% dinâmica para o sistema de gerenciamento de estoque da sua marca de roupas, a **Unfiltered**. Você deixará o trabalho manual no passado e usará o Jackson para gerenciar toda a entrada e saída de dados.

### 📝 O Cenário

A aplicação deve permitir que o administrador consulte as peças disponíveis no catálogo e cadastre novas peças de streetwear diretamente enviando dados estruturados (JSON) através do corpo da requisição (Body) no Insomnia.

### 🏢 Arquitetura Exigida

1. **Model Principal:** Classe `Produto` (atributos sugeridos: `Long id`, `String nome`, `String tamanho`, `Double preco`).
2. **Model de Erro (Opcional, mas recomendado):** Classe `ErroPadrao` (atributos: `String status`, `String mensagem`) para garantir que até as falhas retornem um JSON organizado.
3. **Repository:** Classe `EstoqueRepository` (injetada via construtor no Handler, inicializada com 2 ou 3 produtos básicos, como camisetas oversized).
4. **Handler:** Classe `EstoqueHandler` centralizando a lógica de rede.

### ⚙️ Rotas e Regras de Negócio

Seu único Handler deve interceptar a rota `/api/unfiltered/estoque` e decidir a ação baseada no método HTTP:

- **GET (Lista ou Busca):**
    - Se **não houver** Query String: Retorne a lista completa de produtos (Status 200).
    - Se **houver** Query String (`?id=X`): Retorne o produto específico (Status 200). Se o ID não existir, retorne o seu objeto de erro serializado (Status 404).
- **POST (Criação Dinâmica):**
    - Capture o fluxo de dados enviado pelo Insomnia usando `exchange.getRequestBody()`.
    - Use o `ObjectMapper` para **desserializar** esse JSON e transformá-lo diretamente em um objeto `Produto`.
    - Adicione esse novo produto à lista do seu repositório.
    - Retorne o JSON do produto recém-criado como resposta (Status 201).
- *(Desafio Bônus)* **DELETE (Exclusão):**
    - Se o método for "DELETE" com um `?id=X`, remova o item correspondente da lista do repositório. Retorne um JSON simples de sucesso `{"mensagem": "Produto removido"}` (Status 200).

### 🚨 Regras de Ouro do Projeto:

- **Zero StringBuilder para JSON:** É expressamente proibido concatenar Strings para montar JSON. Toda a saída (incluindo as mensagens de erro de ID inválido) e toda a entrada devem ser tratadas pelo `ObjectMapper` do Jackson.
- **Headers:** Não se esqueça de manter o `Content-Type: application/json; charset=UTF-8` em **todas** as respostas.