/**
 * @author falvesmac
 */

package br.com.falves.Quarto_Modulo.Projeto;

public class MensagemStatus {
    private Status status;
    private String mensagem;

    public Status getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public MensagemStatus(Status status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }
}