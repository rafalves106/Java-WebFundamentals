/**
 * @author falvesmac
 */

package br.com.falves.Segundo_Modulo.Terceiro_Exercicio;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HandlerTeste implements HttpHandler {

    public String montaMensagemResposta(String mensagem){
        String resposta = "{" +
                System.lineSeparator() +
                "\t\"status\": " +
                "\"erro:\"" +
                ", " +
                System.lineSeparator() +
                "\t\"mensagem\": " +
                "\"" +
                mensagem +
                "\"" +
                System.lineSeparator() +
                "}";

        return resposta;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        String resposta = "";

        int idTeste = 101;

        if (idTeste > 1 && idTeste <= 100){
            resposta = "{" +
                    System.lineSeparator() +
                    "\t\"status\": " +
                    "\"sucesso:\"" +
                    ", " +
                    System.lineSeparator() +
                    "\t\"mensagem\": " +
                    "\"" +
                    "Produto disponível." +
                    "\"" +
                    System.lineSeparator() +
                    "}";
            exchange.sendResponseHeaders(200, resposta.getBytes().length);
        } else if (idTeste > 100) {
            resposta = montaMensagemResposta("Produto não localizado.");
            exchange.sendResponseHeaders(404, resposta.getBytes().length);
        } else if (idTeste <= 0) {
            resposta = montaMensagemResposta("ID inválido. Informe um valor positivo.");
            exchange.sendResponseHeaders(400, resposta.getBytes().length);
        }

        OutputStream os = exchange.getResponseBody();
        os.write(resposta.getBytes());

        os.close();
    }
}