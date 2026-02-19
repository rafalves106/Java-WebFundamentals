/**
 * @author falvesmac
 */

package br.com.falves.Terceiro_Modulo.Terceiro_Exercicio;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HandlerTeste implements HttpHandler {

    private final LivrosRepository repository;

    public HandlerTeste(LivrosRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringBuilder sb = new StringBuilder();

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        String query = exchange.getRequestURI().getQuery();
        String metodo = exchange.getRequestMethod();
        String caminho = exchange.getRequestURI().getPath();

        if (metodo.equals("GET") && caminho.equals("/api/livros")){

            // Verifica se possui uma query
            if (query != null && query.contains("id=")) {
                String valorId = query.split("=")[1];

                int id = Integer.parseInt(valorId);


                if (id > repository.getLivros().size()) {
                    sb.append("{")
                            .append(System.lineSeparator())
                            .append("\t\"status\": ")
                            .append("\"erro:\"")
                            .append(", ")
                            .append(System.lineSeparator())
                            .append("\t\"mensagem\": ")
                            .append("\"")
                            .append("Livro não localizado.")
                            .append("\"")
                            .append(System.lineSeparator())
                            .append("}");

                    exchange.sendResponseHeaders(404, sb.toString().getBytes().length);
                } else {
                    sb.append("{")
                            .append("\"ID\": ")
                            .append("\"")
                            .append(repository.getLivros().get(id - 1).getId())
                            .append("\", ")
                            .append("\"Nome\": \"")
                            .append(repository.getLivros().get(id - 1).getNome())
                            .append("\"}");
                    exchange.sendResponseHeaders(200, sb.toString().getBytes().length);
                }
            } else {
                sb.append("{");
                for (Livro livro : repository.getLivros()) {
                    sb.append(System.lineSeparator())
                            .append("\t\"ID\": ")
                            .append("\"")
                            .append(livro.getId())
                            .append("\", ")
                            .append("\"")
                            .append("Nome\": \"")
                            .append(livro.getNome())
                            .append("\"");
                    if (!livro.equals(repository.getLivros().get(repository.getLivros().size() - 1))) {
                        sb.append(",");
                    }
                }
                sb.append(System.lineSeparator())
                        .append("}");

                exchange.sendResponseHeaders(200, sb.toString().getBytes().length);
            }
        } else {
            sb.setLength(0);
            sb.append("Método não permitido para essa url");
            exchange.sendResponseHeaders(405, sb.toString().getBytes().length);
        }

        OutputStream os = exchange.getResponseBody();
        os.write(sb.toString().getBytes());
        os.close();
    }
}