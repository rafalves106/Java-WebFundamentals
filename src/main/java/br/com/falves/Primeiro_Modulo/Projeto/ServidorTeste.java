/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Projeto;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServidorTeste {
    public static void main(String[] args) throws IOException {
        // Criamos o servidor ouvindo na porta 8080
        HttpServer servidor = HttpServer.create(new InetSocketAddress(8080), 0);

        // Cria os contextos e associa-os ao Handler
        servidor.createContext("/api/status", new HandlerTeste());
        servidor.createContext("/api/data", new HandlerTeste());
        servidor.createContext("/api/sistema", new HandlerTeste());

        // Executor nulo usa o gerenciamento de threads padrão do Java
        servidor.setExecutor(null);

        // Inicia o servidor
        servidor.start();
        System.out.println("Servidor rodando na porta 8080...");
    }
}