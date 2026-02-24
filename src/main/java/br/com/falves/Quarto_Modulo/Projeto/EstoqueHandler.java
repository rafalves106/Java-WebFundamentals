/**
 * @author falvesmac
 */

package br.com.falves.Quarto_Modulo.Projeto;

import br.com.falves.Quarto_Modulo.Terceiro_Exercicio.Resposta;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class EstoqueHandler implements HttpHandler {

    ObjectMapper mapper = new ObjectMapper();

    private final EstoqueRepository repo;

    public EstoqueHandler(EstoqueRepository repo) {
        this.repo = repo;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String metodo = exchange.getRequestMethod();
        String caminho = exchange.getRequestURI().getPath();
        String json = "";
        int code;

        if (caminho.equals("/api/unfiltered/estoque")){
            String query = exchange.getRequestURI().getQuery();

            if (query != null && query.contains("id=")){
                try {
                    String valorId = query.split("=")[1];
                    long id = Long.parseLong(valorId);

                    if(metodo.equals("GET")){
                        json = mapper.writeValueAsString(repo.buscarProdutoPorId(id));
                        code = 200;

                        exchange.sendResponseHeaders(code, json.getBytes().length);
                    } else if (metodo.equals("DELETE")){
                        MensagemStatus status;
                        if (repo.deletarProduto(id)){
                            status = new MensagemStatus(Status.SUCESSO, "Produto excluído com sucesso");
                            code = 200;
                        } else {
                            status = new MensagemStatus(Status.ERRO, "Erro ao excluir produto");
                            code = 400;
                        }

                        json = mapper.writeValueAsString(status);

                        exchange.sendResponseHeaders(code, json.getBytes().length);

                    } else {
                        MensagemStatus status = new MensagemStatus(Status.ERRO, "Método não permitido para esta url");
                        json = mapper.writeValueAsString(status);
                        exchange.sendResponseHeaders(405, json.getBytes().length);
                    }

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                    MensagemStatus status = new MensagemStatus(Status.ERRO, "Id inválido. Use apenas números.");
                    json = mapper.writeValueAsString(status);
                    exchange.sendResponseHeaders(400, json.getBytes().length);
                }
            } else {
                json = mapper.writeValueAsString(repo.getEstoque());
                code = 200;

                exchange.sendResponseHeaders(code, json.getBytes().length);
            }

        } else if (caminho.equals("/api/unfiltered/cadastro")) {
            if (metodo.equals("POST")){
                Produto p = mapper.readValue(exchange.getRequestBody(), Produto.class);
                repo.adicionaProduto(p);

                MensagemStatus status = new MensagemStatus(Status.SUCESSO, "Produto adicionado com sucesso ID: " + p.getId());
                json = mapper.writeValueAsString(status);
                exchange.sendResponseHeaders(201, json.getBytes().length);
            } else {
                MensagemStatus status = new MensagemStatus(Status.ERRO, "Método não permitido para esta url");
                json = mapper.writeValueAsString(status);
                exchange.sendResponseHeaders(405, json.getBytes().length);
            }
        }

        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}