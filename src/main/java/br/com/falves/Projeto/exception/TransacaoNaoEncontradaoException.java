package br.com.falves.Projeto.exception;

public class TransacaoNaoEncontradaoException extends RuntimeException {
    public TransacaoNaoEncontradaoException(String message) {
        super(message);
    }
}
