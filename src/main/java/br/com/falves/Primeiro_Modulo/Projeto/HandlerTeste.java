/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Projeto;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class HandlerTeste implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringBuilder sb = new StringBuilder();

        // Configura o Header (UTF-8 garante acentuação correta)
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");

        if (exchange.getRequestMethod().contains("GET")) {
            if (exchange.getRequestURI().getPath().contains("/api/status")) {
                sb.append("Servidor Online - Java Nativo");
            } else if (exchange.getRequestURI().getPath().contains("/api/data")) {
                sb.append(LocalDateTime.now());
            } else if (exchange.getRequestURI().getPath().contains("/api/sistema")) {
                sb.append("Versão Java: ").append(System.getProperty("java.version")).append(" | ").append("Sistema Operacional: ").append(System.getProperty("os.name"));
            }
            // Envia Status Code 200 e o tamanho da resposta em bytes
            exchange.sendResponseHeaders(200, sb.toString().getBytes().length);
        } else if (exchange.getRequestMethod().contains("POST")) {
            sb.append("Apenas consultas (GET) são permitidas nesta API.");

            // Envia Status Code 405 e o tamanho da resposta em bytes
            exchange.sendResponseHeaders(405, sb.toString().getBytes().length);
        }

        // Escreve o corpo da resposta no Stream
        OutputStream os = exchange.getResponseBody();
        os.write(sb.toString().getBytes());

        // Fechar o Stream (Obrigatório!)
        os.close();
    }
}