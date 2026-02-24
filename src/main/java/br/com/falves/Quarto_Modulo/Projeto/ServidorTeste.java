/**
 * @author falvesmac
 */

package br.com.falves.Quarto_Modulo.Projeto;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServidorTeste {
    public static void main(String[] args) throws IOException {
        EstoqueRepository repo = new EstoqueRepository();
        EstoqueHandler handler = new EstoqueHandler(repo);
        HttpServer servidor = HttpServer.create(new InetSocketAddress(8080), 0);

        servidor.createContext("/api/unfiltered/estoque", handler);
        servidor.createContext("/api/unfiltered/cadastro", handler);

        servidor.setExecutor(null);

        servidor.start();
        System.out.println("Servidor rodando na porta 8080...");
    }
}