# O Fim do StringBuilder (Serialização)

**Objetivo:** Refatorar as rotas de busca (GET) do seu projeto anterior, substituindo a montagem manual de JSON pela serialização automática do Jackson.

### 📝 Passo a Passo:

1. **Configuração:** Adicione a dependência do `jackson-databind` ao seu projeto (via Maven no `pom.xml` ou adicionando o arquivo `.jar`).
2. **Instanciação:** Dentro do seu `HandlerTeste`, crie uma instância de `ObjectMapper`.
    - *Opcional:* Ative a formatação visual com `mapper.enable(SerializationFeature.INDENT_OUTPUT);`.
3. **Refatoração do GET (Lista Completa):** * Apague o laço `for` e o `StringBuilder`.
    - Use `String json = mapper.writeValueAsString(repository.getLivros());`.
    - Envie essa String como resposta.
4. **Refatoração do GET (Por ID):**
    - Encontre o livro na lista.
    - Use `mapper.writeValueAsString(livroEncontrado)` para gerar o JSON do livro específico.
    - Envie a resposta.