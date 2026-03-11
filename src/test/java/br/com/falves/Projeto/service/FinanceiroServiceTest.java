package br.com.falves.Projeto.service;

import br.com.falves.Projeto.exception.TransacaoInvalidaException;
import br.com.falves.Projeto.exception.TransacaoNaoEncontradaoException;
import br.com.falves.Projeto.exception.TransacaoZeradaOuNulaException;
import br.com.falves.Projeto.model.TipoTransacao;
import br.com.falves.Projeto.model.Transacao;
import br.com.falves.Projeto.repository.TransacaoRepository;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinanceiroServiceTest {

    @Mock
    private TransacaoRepository repository;

    @InjectMocks
    private FinanceiroService service;


    @Test
    @DisplayName("Deve cadastrar uma transação preenchendo todos os dados da mesma corretamente.")
    void cadastrarUmaTransacaoComSucesso(){
        Transacao t = new Transacao(1L, "Teste", 100.0, TipoTransacao.RECEITA, "2026-05-20");

        Document doc = service.parseDocument(t);
        when(repository.buscarTransacaoPorId(1L)).thenReturn(doc);

        service.cadastraTransacao(t);

        Transacao tEncontrada = service.buscarTransacaoPorId(1L);

        verify(repository, times(1)).criarTransacao(doc);
        verify(repository, times(1)).buscarTransacaoPorId(1L);
        Assertions.assertEquals(tEncontrada.getId(), t.getId());
    }

    @Test
    @DisplayName("Não deve cadastrar uma transação ao seta a sua descrição vazia.")
    void naoCadastraUmaTransacaoSemDescricao(){
        Transacao t = new Transacao(1L, "", 100.0, TipoTransacao.DESPESA, "2026-05-20");
        Document doc = service.parseDocument(t);
        verify(repository, times(0)).criarTransacao(doc);
        Assertions.assertThrows(TransacaoInvalidaException.class, () -> service.cadastraTransacao(t));
    }

    @Test
    @DisplayName("Não deve cadastrar uma transação ao setar seu valor 0 ou menor.")
    void naoCadastraUmaTransacaoComValorZeroOuNegativo(){
        Transacao t = new Transacao(1L, "Teste", 0.0, TipoTransacao.DESPESA, "2026-05-20");
        Document doc = service.parseDocument(t);
        verify(repository, times(0)).criarTransacao(doc);
        Assertions.assertThrows(TransacaoZeradaOuNulaException.class, () -> service.cadastraTransacao(t));
    }

    @Test
    @DisplayName("Altera transação com todos os dados válidos.")
    void alteraTransacao(){
        Transacao t = new Transacao(1L, "Teste", 100.0, TipoTransacao.DESPESA, "2026-04-20");
        Transacao tNova = new Transacao(2L, "Teste Novo", 50.0, TipoTransacao.RECEITA, "2024-04-20");


        Document doc = service.parseDocument(t);
        Document doc2 = service.parseDocument(tNova);

        service.cadastraTransacao(t);

        when(repository.buscarTransacaoPorId(1L)).thenReturn(doc);

        service.alterarTransacao(1L, tNova);

        when(repository.buscarTransacaoPorId(2L)).thenReturn(doc2);

        Transacao alterada = service.buscarTransacaoPorId(2L);

        verify(repository, times(1)).criarTransacao(doc);
        verify(repository, times(1)).alterarTransacao(doc, doc2);
        Assertions.assertEquals(2L, alterada.getId());
    }

    @Test
    @DisplayName("Não deve alterar uma transação com nova descrição vazia.")
    void naoAlteraTransacaoComDescricaoVazia(){
        Transacao t = new Transacao(1L, "Teste", 100.0, TipoTransacao.DESPESA, "2026-04-20");
        Transacao tNova = new Transacao(1L, "", 100.0, TipoTransacao.DESPESA, "2026-04-20");

        Document doc = service.parseDocument(t);

        service.cadastraTransacao(t);

        when(repository.buscarTransacaoPorId(1L)).thenReturn(doc);

        verify(repository, times(1)).criarTransacao(doc);
        Assertions.assertThrows(TransacaoInvalidaException.class, () -> service.alterarTransacao(1L, tNova));
    }

    @Test
    @DisplayName("Não deve alterar uma transação com novo valor zero ou negativo.")
    void naoAlteraTransacaoComValorZeroOuNegativo(){
        Transacao t = new Transacao(1L, "Teste", 100.0, TipoTransacao.DESPESA, "2026-04-20");
        Transacao tNova = new Transacao(1L, "Teste", 0.0, TipoTransacao.DESPESA, "2026-04-20");

        Document doc = service.parseDocument(t);

        service.cadastraTransacao(t);

        when(repository.buscarTransacaoPorId(1L)).thenReturn(doc);

        verify(repository, times(1)).criarTransacao(doc);
        Assertions.assertThrows(TransacaoZeradaOuNulaException.class, () -> service.alterarTransacao(1L, tNova));
    }

    @Test
    @DisplayName("Não deve alterar uma transação com novo valor zero ou negativo.")
    void excluiTransacao(){
        Transacao t = new Transacao(1L, "Teste", 100.0, TipoTransacao.DESPESA, "2026-04-20");

        Document doc = service.parseDocument(t);

        service.cadastraTransacao(t);

        when(repository.buscarTransacaoPorId(1L)).thenReturn(doc);
        boolean deletada = service.deletarTransacao(1L);
        Assertions.assertTrue(deletada);
        verify(repository, times(1)).deletarTransacao(doc);

    }

    @Test
    @DisplayName("Não deve excluir uma transação que não existe no banco de dados")
    void naoDeveExcluirTransacaoQueNaoExiste(){
        when(repository.buscarTransacaoPorId(1L)).thenReturn(null);
        Assertions.assertThrows(TransacaoNaoEncontradaoException.class, () -> service.deletarTransacao(1L));
        verify(repository, times(1)).buscarTransacaoPorId(1L);
    }
}