/**
 * @author falvesmac
 */

package br.com.falves.Projeto.controller;

import br.com.falves.Projeto.dto.Status;
import br.com.falves.Projeto.dto.TipoStatus;
import br.com.falves.Projeto.exception.QueryInvalidaException;
import br.com.falves.Projeto.exception.TransacaoInvalidaException;
import br.com.falves.Projeto.exception.TransacaoZeradaOuNulaException;
import br.com.falves.Projeto.model.Transacao;
import br.com.falves.Projeto.repository.TransacaoRepository;
import br.com.falves.Projeto.service.FinanceiroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TransacaoController implements HttpHandler {

    TransacaoRepository repository;
    FinanceiroService service;

    public TransacaoController(TransacaoRepository repository, FinanceiroService service) {
        this.repository = repository;
        this.service = service;
    }

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String caminho = exchange.getRequestURI().getPath();
        String metodo = exchange.getRequestMethod();
        String json = "{}";
        int codigo = 0;
        Status status;

        // CHAMADA DO MÉTODO GET
        if (metodo.equals("GET") && caminho.equals("/api/transacoes")) {

            try {
                String query = exchange.getRequestURI().getQuery();

                GetRequest getRequest = new GetRequest(service, mapper, query);
                getRequest.request();

                json = getRequest.json;
                codigo = getRequest.codigo;
            } catch (QueryInvalidaException e){
                status = new Status(TipoStatus.ERRO, e.getMessage());
                json = mapper.writeValueAsString(status);
                codigo = 400;
            }

        // CHAMADA DO MÉTODO POST
        } else if (metodo.equals("POST") && caminho.equals("/api/transacoes")) {
            try {
                Transacao t = mapper.readValue(exchange.getRequestBody(), Transacao.class);
                PostRequest postRequest = new PostRequest(service, mapper, t);

                postRequest.request();

                json = postRequest.json;
                codigo = postRequest.codigo;
            } catch (TransacaoInvalidaException | TransacaoZeradaOuNulaException | QueryInvalidaException e){
                status = new Status(TipoStatus.ERRO, e.getMessage());
                codigo = 400;
                json = mapper.writeValueAsString(status);
            } catch (Exception e){
                status = new Status(TipoStatus.ERRO, "Formato do JSON inválido ou valores incompatíveis.");
                codigo = 400;
                json = mapper.writeValueAsString(status);
            }

        // CHAMADA DO MÉTODO DELETE
        } else if (metodo.equals("DELETE") && caminho.equals("/api/transacoes")) {
            try {
                String query = exchange.getRequestURI().getQuery();
                DeleteRequest deleteRequest = new DeleteRequest(service, mapper, query);

                deleteRequest.request();

                json = deleteRequest.json;
                codigo = deleteRequest.codigo;
            } catch (Exception e) {
                status = new Status(TipoStatus.ERRO, e.getMessage());
                codigo = 404;
                json = mapper.writeValueAsString(status);
            }

        // CHAMADA DO MÉTODO PUT
        } else if (metodo.equals("PUT") && caminho.equals("/api/transacoes")) {
            try {
                String query = exchange.getRequestURI().getQuery();
                Transacao t = mapper.readValue(exchange.getRequestBody(), Transacao.class);

                UpdateRequest updateRequest = new UpdateRequest(service, mapper, query, t);

                updateRequest.request();

                json = updateRequest.json;
                codigo = updateRequest.codigo;
            } catch (Exception e) {
                status = new Status(TipoStatus.ERRO, e.getMessage());
                codigo = 400;
                json = mapper.writeValueAsString(status);
            }
        }

        exchange.sendResponseHeaders(codigo, json.getBytes(StandardCharsets.UTF_8).length);

        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes());
        os.close();
    }
}