/**
 * @author falvesmac
 */

package br.com.falves.Terceiro_Modulo.Projeto;

import java.util.ArrayList;
import java.util.List;

public class LivroRepository {
    private List<Livro> livros =  new ArrayList<>();

    public List<Livro> getLivros() {
        return livros;
    }

    public LivroRepository() {
        livros.add(new Livro(1L,"Livro 1", "Autor 1"));
        livros.add(new Livro(2L, "Livro 2", "Autor 2"));
        livros.add(new Livro(3L, "Livro 3", "Autor 3"));
    }
}