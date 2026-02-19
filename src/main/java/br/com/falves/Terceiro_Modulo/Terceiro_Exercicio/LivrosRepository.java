/**
 * @author falvesmac
 */

package br.com.falves.Terceiro_Modulo.Terceiro_Exercicio;

import java.util.ArrayList;
import java.util.List;

public class LivrosRepository {
    private List<Livro> livros = new ArrayList<>();

    public List<Livro> getLivros() {
        return livros;
    }

    public LivrosRepository() {
        livros.add(new Livro(1L, "O Programador Pragmático"));
        livros.add(new Livro(2L, "Use a Cabeça: Java"));
        livros.add(new Livro(3L, "Código Limpo"));
    }
}