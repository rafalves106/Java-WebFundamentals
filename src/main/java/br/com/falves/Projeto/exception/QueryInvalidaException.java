package br.com.falves.Projeto.exception;

public class QueryInvalidaException extends RuntimeException {
    public QueryInvalidaException(String message) {
        super(message);
    }
}
