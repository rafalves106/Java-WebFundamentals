/**
 * @author falvesmac
 */

package br.com.falves.Projeto.controller;

import br.com.falves.Projeto.dto.Status;
import br.com.falves.Projeto.dto.TipoStatus;
import br.com.falves.Projeto.model.Transacao;
import br.com.falves.Projeto.service.FinanceiroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateRequest extends Request{
    private String query;
    private Transacao t;

    public UpdateRequest(FinanceiroService service, ObjectMapper mapper, String query, Transacao t) {
        super(service, mapper);
        this.query = query;
        this.t = t;
    }

    @Override
    public void request() throws JsonProcessingException {
        Status status;

        if (query == null || !query.contains("id=")) {
            this.codigo = 400;
            status = new Status(TipoStatus.ERRO, "O parâmetro 'id' é obrigatório na URL.");
            this.json = mapper.writeValueAsString(status);
            return;
        }

        try {
            String valorId = query.split("=")[1];
            long id = Long.parseLong(valorId);

            if (t == null) {
                throw new IllegalArgumentException("Dados da transação não fornecidos.");
            }

            service.alterarTransacao(id, t);
            status = new Status(TipoStatus.SUCESSO, "Transação alterada com sucesso");
            codigo = 200;
        } catch (Exception e) {
            status = new Status(TipoStatus.ERRO, "Erro ao atualizar: " + e.getMessage());
            codigo = 400;
        }

        json = mapper.writeValueAsString(status);
    }
}