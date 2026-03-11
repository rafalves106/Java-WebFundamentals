/**
 * @author falvesmac
 */

package br.com.falves.Projeto.service;

import br.com.falves.Projeto.exception.TransacaoInvalidaException;
import br.com.falves.Projeto.exception.TransacaoNaoEncontradaoException;
import br.com.falves.Projeto.exception.TransacaoZeradaOuNulaException;
import br.com.falves.Projeto.model.TipoTransacao;
import br.com.falves.Projeto.model.Transacao;
import br.com.falves.Projeto.repository.TransacaoRepository;
import org.bson.Document;

import java.util.List;

public class FinanceiroService {
    private final TransacaoRepository transacaoRepository;

    public FinanceiroService(TransacaoRepository repo) {
        this.transacaoRepository = repo;
    }

    public void validaTransacao(Transacao t){
        if (t == null){
            throw new TransacaoInvalidaException("JSON Inválido");
        }

        if (t.getDescricao().isEmpty()){
            throw new TransacaoInvalidaException("Descrição não pode estar vazia.");
        }

        if(t.getValor() <= 0){
            throw new TransacaoZeradaOuNulaException("Não é possível cadastrar uma transação com valor zero ou menor.");
        }
    }

    public Document parseDocument(Transacao t){
        return new Document("id", t.getId())
                .append("descricao", t.getDescricao())
                .append("valor", t.getValor())
                .append("tipo", t.getTipo().toString())
                .append("data", t.getData());
    }

    public Transacao parseTransacao(Document doc){
        Transacao t = new Transacao();
        t.setId(doc.getLong("id"));
        t.setDescricao(doc.getString("descricao"));
        t.setValor(doc.getDouble("valor"));
        TipoTransacao tipo = TipoTransacao.valueOf(doc.getString("tipo"));
        t.setTipo(tipo);
        t.setData(doc.getString("data"));

        return t;
    }

    public void cadastraTransacao(Transacao t){
        validaTransacao(t);

        Document doc = parseDocument(t);

        transacaoRepository.criarTransacao(doc);
    }

    public boolean deletarTransacao(Long id){
        Document doc = transacaoRepository.buscarTransacaoPorId(id);

        if (doc != null){
            transacaoRepository.deletarTransacao(doc);
            return true;
        } else {
            throw new TransacaoNaoEncontradaoException("Não foi possível encontrar a transação com id fornecido.");
        }
}

public void alterarTransacao(Long id, Transacao t){
    Transacao tAnterior = buscarTransacaoPorId(id);

    validaTransacao(t);

    Document doc2 = parseDocument(t);

    if (parseDocument(tAnterior) != null){
        transacaoRepository.alterarTransacao(parseDocument(tAnterior), doc2);
    } else {
        throw new TransacaoNaoEncontradaoException("Não foi possível encontrar a transação com id fornecido.");
    }
}

public List<Document> buscarTransacoes(){
    return transacaoRepository.listarTransacoes();
}

public Transacao buscarTransacaoPorId(Long id){

    Document doc = transacaoRepository.buscarTransacaoPorId(id);

    if (doc == null){
        throw new TransacaoNaoEncontradaoException("Transação não encontrada com o ID fornecido.");
    }

    return parseTransacao(doc);
}

public List<Document> buscarTransacoesPorTipo(TipoTransacao tipo){
    return transacaoRepository.buscarTransacaoPorTipo(tipo);
}
}