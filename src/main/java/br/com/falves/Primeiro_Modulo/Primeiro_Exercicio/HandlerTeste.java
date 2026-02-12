/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Primeiro_Exercicio;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HandlerTeste implements HttpHandler {
    public String handlerMensagem;


    public String getHandlerMensagem() {
        return handlerMensagem;
    }

    public void setHandlerMensagem(String handlerMensagem) {
        this.handlerMensagem = handlerMensagem;
    }

    public HandlerTeste(String handlerMensagem) {
        this.handlerMensagem = handlerMensagem;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");

        exchange.sendResponseHeaders(200, handlerMensagem.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(handlerMensagem.getBytes());

        os.close();
    }
}