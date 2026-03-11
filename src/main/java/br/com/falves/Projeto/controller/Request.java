/**
 * @author falvesmac
 */

package br.com.falves.Projeto.controller;

import br.com.falves.Projeto.service.FinanceiroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Request {
    String json;
    int codigo;
    FinanceiroService service;
    ObjectMapper mapper;

    public Request(FinanceiroService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public abstract void request() throws JsonProcessingException;
}