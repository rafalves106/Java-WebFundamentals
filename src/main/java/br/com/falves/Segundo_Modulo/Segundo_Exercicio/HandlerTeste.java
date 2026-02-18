/**
 * @author falvesmac
 */

package br.com.falves.Segundo_Modulo.Segundo_Exercicio;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class HandlerTeste implements HttpHandler {

    void formataJson(StringBuilder sb, List<Produto> produtos){
        sb.append("[");
        sb.append(System.lineSeparator());
        for (Produto p : produtos){
            sb.append("\t{")
                    .append("\"id\": ")
                    .append(p.getId())
                    .append(", ")
                    .append("\"nome\": ")
                    .append("\"")
                    .append(p.getNome())
                    .append("\"")
                    .append(", ")
                    .append("\"preço\": ")
                    .append(p.getPreco())
                    .append("}");
            if (!p.equals(produtos.get(produtos.size() - 1))) {
                sb.append(",");
            }
            sb.append(System.lineSeparator());
        }
        sb.append("]");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto(1L,"Desinfetante", 120D));
        produtos.add(new Produto(2L, "Rodo", 10D));
        produtos.add(new Produto(3L, "Pano de Chão", 500D));

        StringBuilder sb = new StringBuilder();

        formataJson(sb, produtos);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        if (exchange.getRequestMethod().equals("GET")){
            if (exchange.getRequestURI().getPath().equals("/api/produtos")) {
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