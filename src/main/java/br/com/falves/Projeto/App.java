/**
 * @author falvesmac
 */

package br.com.falves.Projeto;

import br.com.falves.Projeto.controller.TransacaoController;
import br.com.falves.Projeto.repository.TransacaoRepository;
import br.com.falves.Projeto.service.FinanceiroService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {
        TransacaoRepository repository = new TransacaoRepository();
        FinanceiroService service = new FinanceiroService(repository);
        TransacaoController controller = new TransacaoController(repository, service);

        HttpServer servidor = HttpServer.create(new InetSocketAddress(8080), 0);

        servidor.createContext("/api/transacoes", controller);

        servidor.setExecutor(null);

        servidor.start();
    }
}