/**
 * @author falvesmac
 */

package br.com.falves.Projeto.controller;

import br.com.falves.Projeto.dto.Status;
import br.com.falves.Projeto.dto.TipoStatus;
import br.com.falves.Projeto.exception.TransacaoNaoEncontradaoException;
import br.com.falves.Projeto.service.FinanceiroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeleteRequest extends Request {
    private String query;

    public DeleteRequest(FinanceiroService service, ObjectMapper mapper, String query) {
        super(service, mapper);
        this.query = query;
    }

    @Override
    public void request() throws JsonProcessingException {
        String valorId = query.split("=")[1];
        Status status = null;

        try {
            long id = Long.parseLong(valorId);
            boolean deletado = service.deletarTransacao(id);

            if (deletado){
                status = new Status(TipoStatus.SUCESSO, "Transação deletada com sucesso.");
                codigo = 200;
            }
        } catch (TransacaoNaoEncontradaoException e) {
            status = new Status(TipoStatus.ERRO, e.getMessage());
            codigo = 400;
        }

        json = mapper.writeValueAsString(status);
    }
}