/**
 * @author falvesmac
 */

package br.com.falves.Terceiro_Modulo.Terceiro_Exercicio;

public class Livro {
    private Long id;
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Livro(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}