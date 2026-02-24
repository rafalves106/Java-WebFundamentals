/**
 * @author falvesmac
 */

package br.com.falves.Terceiro_Modulo.Projeto;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HandlerTeste implements HttpHandler {

    private final LivroRepository repository;

    public HandlerTeste(LivroRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        String metodo = exchange.getRequestMethod();
        String caminho = exchange.getRequestURI().getPath();

        StringBuilder sb = new StringBuilder();

        if (metodo.equals("GET") && caminho.equals("/api/livros")) {
            String query = exchange.getRequestURI().getQuery();

            if (query != null && query.contains("id=")){
                try {
                    String valorID = query.split("=")[1];
                    int id = Integer.parseInt(valorID);

                    if (id > repository.getLivros().size() || id <= 0){
                        sb.append("Livro não encontrado com id fornecido.");
                        exchange.sendResponseHeaders(404, sb.toString().getBytes().length);
                    } else {
                        sb.append("{\"ID\": \"").append(repository.getLivros().get(id -1).getId()).append("\"");
                        sb.append(", \"Nome\": \"").append(repository.getLivros().get(id -1).getNome()).append("\"");
                        sb.append(", \"Autor\": \"").append(repository.getLivros().get(id -1).getAutor()).append("\"");
                        sb.append("}");

                        exchange.sendResponseHeaders(200, sb.toString().getBytes().length);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                    sb.append("Id inválido. Use apenas números.");
                    exchange.sendResponseHeaders(400, sb.toString().getBytes().length);
                }
            } else {
                sb.append("[");
                sb.append(System.lineSeparator());
                for (Livro l : repository.getLivros()){
                    sb.append("\t{");
                    sb.append(System.lineSeparator());
                    sb.append("\t\t\"ID\": \"").append(l.getId()).append("\",");
                    sb.append(System.lineSeparator());
                    sb.append("\t\t\"Nome\": \"").append(l.getNome()).append("\",");
                    sb.append(System.lineSeparator());
                    sb.append("\t\t\"Autor\": \"").append(l.getAutor()).append("\"");
                    sb.append(System.lineSeparator());
                    sb.append("\t}");

                    if (l != repository.getLivros().get(repository.getLivros().size() - 1)) {
                        sb.append(",");
                        sb.append(System.lineSeparator());
                    }
                }
                sb.append(System.lineSeparator());
                sb.append("]");

                exchange.sendResponseHeaders(200, sb.toString().getBytes().length);
            }
        } else if (metodo.equals("POST")) {
            long id = 4L;
            String nome = "Teste adição livro";
            String autor = "Teste adição autor";
            repository.getLivros().add(new Livro(id, nome, autor));
            sb.append("Novo livro adicionado: ID ").append(id).append(", Nome ").append(nome).append(", Autor ").append(autor);
            exchange.sendResponseHeaders(201, sb.toString().getBytes().length);
        } else {sb.append("Método não disponível para esta url");
            exchange.sendResponseHeaders(405, sb.toString().getBytes().length);
        }

        OutputStream os = exchange.getResponseBody();
        os.write(sb.toString().getBytes());

        os.close();
    }
}