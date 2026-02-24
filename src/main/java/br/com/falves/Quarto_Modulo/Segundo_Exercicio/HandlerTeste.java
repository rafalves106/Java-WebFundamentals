/**
 * @author falvesmac
 */

package br.com.falves.Quarto_Modulo.Segundo_Exercicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HandlerTeste implements HttpHandler {

    ObjectMapper mapper = new ObjectMapper();

    private final LivroRepository repository;

    public HandlerTeste(LivroRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String metodo = exchange.getRequestMethod();
        String caminho = exchange.getRequestURI().getPath();
        String json = "";

        if (metodo.equals("GET") && caminho.equals("/api/livros")) {
            String query = exchange.getRequestURI().getQuery();

            if (query != null && query.contains("id=")){
                try {
                    String valorID = query.split("=")[1];
                    long id = Long.parseLong(valorID);

                    if (id > repository.getLivros().size() || id <= 0){
                        json = "Livro não encontrado com id fornecido.";
                        exchange.sendResponseHeaders(404, json.getBytes().length);
                    } else {
                        Livro livro = repository.buscaLivroPorId(id);
                        json = mapper.writeValueAsString(livro);
                        exchange.sendResponseHeaders(200, json.getBytes().length);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                    json = "Id inválido. Use apenas números.";
                    exchange.sendResponseHeaders(400, json.getBytes().length);
                }
            } else {
                json = mapper.writeValueAsString(repository.getLivros());
                exchange.sendResponseHeaders(200, json.getBytes().length);
            }
        } else if (metodo.equals("POST") && caminho.equals("/api/cadastro")) {
            Livro novoLivro = mapper.readValue(exchange.getRequestBody(), Livro.class);

            repository.adicionarLivro(novoLivro);

            json = "Novo livro adicionado com sucesso! ID: " + novoLivro.getId();

            exchange.sendResponseHeaders(201, json.getBytes().length);
        } else {
            json = "Método não disponível para esta url";
            exchange.sendResponseHeaders(405, json.getBytes().length);
        }

        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes());

        os.close();
    }
}