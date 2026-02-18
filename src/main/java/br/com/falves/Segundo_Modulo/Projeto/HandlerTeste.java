/**
 * @author falvesmac
 */

package br.com.falves.Segundo_Modulo.Projeto;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class HandlerTeste implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        List<Produto> produtos = new ArrayList<>();

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        produtos.add(new Produto(1L, "Shampoo", 12.0));
        produtos.add(new Produto(2L, "Condicionador", 15.0));
        produtos.add(new Produto(3L, "Leave-inn", 20.0));

        StringBuilder json = new StringBuilder();

        if (!produtos.isEmpty()) {
            json.append("{").append(System.lineSeparator()).append("\"produtos\": [").append(System.lineSeparator());
            for (Produto p : produtos){
                json.append("\t{")
                        .append(System.lineSeparator())
                        .append("\t\"id\": ")
                        .append("\"")
                        .append(p.getId())
                        .append("\",")
                        .append(System.lineSeparator())
                        .append("\t\"nome\": ")
                        .append("\"")
                        .append(p.getNome())
                        .append("\",")
                        .append(System.lineSeparator())
                        .append("\t\"preço\": ")
                        .append("\"")
                        .append(p.getPreco())
                        .append("\" ")
                        .append(System.lineSeparator())
                        .append("\t}");
                if (p != produtos.get(produtos.size() - 1)) {
                    json.append(",")
                            .append(System.lineSeparator());
                }
            }
            json.append("]").append(System.lineSeparator()).append("}").append(System.lineSeparator());
            exchange.sendResponseHeaders(200, json.toString().getBytes().length);
        } else {
            json.append("{")
                    .append(System.lineSeparator())
                    .append("\t\"status\": ")
                    .append("\"erro:\"")
                    .append(", ")
                    .append(System.lineSeparator())
                    .append("\t\"mensagem\": ")
                    .append("\"")
                    .append("Produto não localizado.")
                    .append("\"")
                    .append(System.lineSeparator())
                    .append("}");
            exchange.sendResponseHeaders(404, json.toString().getBytes().length);
        }

        OutputStream os = exchange.getResponseBody();
        os.write(json.toString().getBytes());

        os.close();
    }
}