/**
 * @author falvesmac
 */

package br.com.falves.Quarto_Modulo.Terceiro_Exercicio;

public class Resposta {
    private Status status;
    private String erro;

    public Status getStatus() {
        return status;
    }

    public String getErro() {
        return erro;
    }

    public Resposta(Status status, String erro) {
        this.status = status;
        this.erro = erro;
    }
}