/**
 * @author falvesmac
 */

package br.com.falves.Segundo_Modulo.Primeiro_Exercicio;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HandlerTeste implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        StringBuilder json = new StringBuilder();
        Usuario usuario = new Usuario(1L, "Rafael Martins", "rafael@email.com");

        json.append("{")
                .append("\"id\": ")
                .append(usuario.getId())
                .append(",")
                .append("\"nome\": \"")
                .append(usuario.getNome())
                .append("\",")
                .append("\"email\": \"")
                .append(usuario.getEmail())
                .append("\"}");

        if (exchange.getRequestMethod().equals("GET")){
            if (exchange.getRequestURI().getPath().equals("/api/usuario")){
                exchange.sendResponseHeaders(200, json.toString().getBytes().length);
            } else if (exchange.getRequestURI().getPath().equals("/api/usuario/vazio")){
                json.setLength(0);
                json.append("{")
                        .append("mensagem: ")
                        .append("Nenhum usuário logado")
                        .append("}");
                exchange.sendResponseHeaders(404, json.toString().getBytes().length);
            }
        } else {
            json.setLength(0);
            json.append("Método não permitido para essa url");
            exchange.sendResponseHeaders(405, json.toString().getBytes().length);
        }

        OutputStream os = exchange.getResponseBody();
        os.write(json.toString().getBytes());

        os.close();
    }
}