# API de Gestão de Biblioteca (CRUD Básico)

**Objetivo:** Criar uma API dinâmica que permita listar, buscar e "simular" o cadastro de livros em um repositório centralizado.

### 📝 Requisitos do Projeto:

1. **Estrutura de Camadas**:
    - **Model**: Classe `Livro` (id, nome, autor).
    - **Repository**: Classe `LivroRepository` que inicia com uma lista de livros pré-cadastrados.
    - **Handler (Controller)**: Classe que recebe o `LivroRepository` via construtor.
2. **Roteamento e Regras de Negócio**:
    - **GET `/api/livros`**:
        - Sem parâmetros: Retorna a lista completa de livros (Status 200).
        - Com parâmetro `?id=X`: Retorna apenas o livro correspondente.
    - **POST `/api/livros`**:
        - Simula o cadastro de um novo livro (Status 201).
        - *Dica: Como ainda não lemos o corpo da requisição, apenas adicione um livro fixo à lista e retorne uma mensagem de sucesso.*
    - **Segurança**: Bloqueie qualquer outro método (PUT, DELETE) com o Status **405**.
3. **Tratamento de Exceções**:
    - Use blocos `try-catch` para capturar erros de conversão de ID (caso o usuário envie letras no lugar de números) e retorne Status **400**.
    - Se o ID for válido mas o livro não existir, retorne Status **404**.