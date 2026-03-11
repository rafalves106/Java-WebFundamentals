/**
 * @author falvesmac
 */

package br.com.falves.Projeto.controller;

import br.com.falves.Projeto.dto.Status;
import br.com.falves.Projeto.dto.TipoStatus;
import br.com.falves.Projeto.exception.QueryInvalidaException;
import br.com.falves.Projeto.model.TipoTransacao;
import br.com.falves.Projeto.service.FinanceiroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

@Getter
@Setter
public class GetRequest extends Request{
    private String query;

    public GetRequest(FinanceiroService service, ObjectMapper mapper, String query){
        super(service, mapper);
        this.query = query;
    }

    @Override
    public void request() throws JsonProcessingException {
        if (query == null || query.isBlank()) {
            json = mapper.writeValueAsString(service.buscarTransacoes());
            codigo = 200;
            return;
        }

        try {
            if (query.contains("id=")) {
                String valorId = query.split("=")[1];
                long id = Long.parseLong(valorId);
                json = mapper.writeValueAsString(service.buscarTransacaoPorId(id));
                codigo = 200;
            } else if (query.contains("filtro=")) {
                String filtro = query.split("=")[1].toUpperCase();
                if (filtro.contains("RECEITA")){
                    json = mapper.writeValueAsString(service.buscarTransacoesPorTipo(TipoTransacao.RECEITA));
                    codigo = 200;
                } else if (filtro.contains("DESPESA")) {
                    json = mapper.writeValueAsString(service.buscarTransacoesPorTipo(TipoTransacao.DESPESA));
                    codigo = 200;
                } else {
                    throw new QueryInvalidaException("Filtro não disponível para pesquisas.");
                }
            } else {
                throw new QueryInvalidaException("Parâmetro de query desconhecido.");
            }
        } catch (Exception e) {
            codigo = 400;
            json = mapper.writeValueAsString(new Status(TipoStatus.ERRO, e.getMessage()));
        }
    }
}