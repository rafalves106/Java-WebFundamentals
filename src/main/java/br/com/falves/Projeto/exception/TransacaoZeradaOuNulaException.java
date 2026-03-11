package br.com.falves.Projeto.exception;

public class TransacaoZeradaOuNulaException extends RuntimeException {
    public TransacaoZeradaOuNulaException(String message) {
        super(message);
    }
}
