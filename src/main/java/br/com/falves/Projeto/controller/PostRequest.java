/**
 * @author falvesmac
 */

package br.com.falves.Projeto.controller;

import br.com.falves.Projeto.dto.Status;
import br.com.falves.Projeto.dto.TipoStatus;
import br.com.falves.Projeto.exception.QueryInvalidaException;
import br.com.falves.Projeto.exception.TransacaoInvalidaException;
import br.com.falves.Projeto.model.Transacao;
import br.com.falves.Projeto.service.FinanceiroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostRequest extends Request{
    private Transacao t;

    public PostRequest(FinanceiroService service, ObjectMapper mapper, Transacao t) {
        super(service, mapper);
        this.t = t;
    }

    @Override
    public void request() throws JsonProcessingException {
        Status status;

        if (t == null) {
            throw new QueryInvalidaException("Insira os dados corretamente.");
        } else {
            try {
                service.cadastraTransacao(t);
                status = new Status(TipoStatus.SUCESSO, "Objeto id: " + t.getId() + " foi cadastrado com sucesso");
                codigo = 201;
            } catch (TransacaoInvalidaException e){
                status = new Status(TipoStatus.ERRO, e.getMessage());
                codigo = 400;
            }

            json = mapper.writeValueAsString(status);
        }
    }
}