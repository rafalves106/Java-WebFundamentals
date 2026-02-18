/**
 * @author falvesmac
 */

package br.com.falves.Terceiro_Modulo.Primeiro_Exercicio;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ConvidadoHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        List<String> convidados = new ArrayList<>();
        convidados.add("Maria");
        convidados.add("João");
        String metodo = exchange.getRequestMethod();
        StringBuilder sb = new StringBuilder();

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        if (metodo.equals("GET")){

            sb.append("Lista de Convidados:").append(System.lineSeparator());
            for (String convidado : convidados) {
                sb.append("- ").append(convidado).append(System.lineSeparator());
            }
            exchange.sendResponseHeaders(200, sb.toString().getBytes().length);
        } else if (metodo.equals("POST")) {
            convidados.add("Teste");
            sb.append("Convidado adicionado: Teste");
            exchange.sendResponseHeaders(201, sb.toString().getBytes().length);
        } else {
            sb.append("Método não permitido. Use GET ou POST.");
            exchange.sendResponseHeaders(405, sb.toString().getBytes().length);
        }

        OutputStream os = exchange.getResponseBody();

        os.write(sb.toString().getBytes());

        os.close();
    }
}