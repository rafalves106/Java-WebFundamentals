# Injeção de Dependência Manual (Service + Controller)

**O objetivo:** Fazer com que o seu Handler não precise dar `new` na lista de livros. Ele deve recebê-la pronta.

### 📝 Suas Tarefas:

1. **Classe `LivroRepository`**: Crie uma classe simples que tenha apenas uma `List<Livro>` interna e um método `getLivros()`.
2. **Refatore o `HandlerTeste`**:
    - Remova a criação da lista de dentro do `handle`.
    - Crie um **construtor** para o Handler que recebe o `LivroRepository`.
    - Armazene esse repositório em um atributo `private final`.
3. **Na sua classe `Main` (ServidorTeste)**:
    - Instancie o `LivroRepository`.
    - Instancie o `HandlerTeste` passando o repositório criado.
    - Isso é a famosa **Injeção de Dependência**.