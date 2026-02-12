/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Segundo_Exercicio;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HandlerTeste implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String resposta = "Você acessou via [METODO] usando o navegador [USER-AGENT]";
        resposta = resposta.replace("[METODO]", exchange.getRequestMethod());
        resposta = resposta.replace("[USER-AGENT]", exchange.getRequestHeaders().getFirst("User-Agent"));

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");

        exchange.sendResponseHeaders(200, resposta.getBytes().length);

        OutputStream os = exchange.getResponseBody();

        os.write(resposta.getBytes());
        os.close();
    }
}