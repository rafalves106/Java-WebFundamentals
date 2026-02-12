/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Terceiro_Exercicio;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HandlerTeste implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String resposta = "O segredo do sucesso é a consistência!";

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");

        try {
            if (exchange.getRequestMethod().contains("POST")){
                exchange.sendResponseHeaders(200, resposta.getBytes().length);
            } else {
                resposta = "Método não permitido para esta operação. Use POST.";
                exchange.sendResponseHeaders(405, resposta.getBytes().length);
            }
        } catch (Exception e) {
            resposta = "Internal Server Error.";
            exchange.sendResponseHeaders(505, resposta.getBytes().length);
        }

        OutputStream os = exchange.getResponseBody();
        os.write(resposta.getBytes());

        os.close();
    }
}