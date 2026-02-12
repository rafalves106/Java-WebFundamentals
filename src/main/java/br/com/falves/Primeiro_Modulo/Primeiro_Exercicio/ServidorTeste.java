/**
 * @author falvesmac
 */

package br.com.falves.Primeiro_Modulo.Primeiro_Exercicio;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServidorTeste {

    public static void main(String[] args) throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress(8080), 0);

        servidor.createContext("/api/quem-sou-eu", new HandlerTeste("Rafael Martins Alves"));
        servidor.createContext("/api/versao", new HandlerTeste("Versão do Sistema: 1.0-SNAPSHOT"));

        servidor.setExecutor(null);

        servidor.start();
        System.out.println("Servidor rodando na porta 8080...");
    }


}