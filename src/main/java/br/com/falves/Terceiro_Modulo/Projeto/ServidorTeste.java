/**
 * @author falvesmac
 */

package br.com.falves.Terceiro_Modulo.Projeto;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServidorTeste {
    public static void main(String[] args) throws IOException {
        LivroRepository repo = new LivroRepository();
        HandlerTeste handler = new HandlerTeste(repo);

        HttpServer servidor = HttpServer.create(new InetSocketAddress(8080), 0);

        servidor.createContext("/api/livros", handler);

        servidor.setExecutor(null);

        servidor.start();
        System.out.println("Servidor rodando na porta 8080...");
    }
}