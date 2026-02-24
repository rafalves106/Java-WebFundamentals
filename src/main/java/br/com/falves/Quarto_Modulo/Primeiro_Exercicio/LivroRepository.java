/**
 * @author falvesmac
 */

package br.com.falves.Quarto_Modulo.Primeiro_Exercicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivroRepository {
    private List<Livro> livros = new ArrayList<>();

    public Livro buscaLivroPorId(Long id){
        return livros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public LivroRepository() {
        livros.add(new Livro(1L, "Use a Cabeça Java", "Autor Desconhecido"));
        livros.add(new Livro(2L, "O Programador Pragmático", "Autor Desconhecido"));
        livros.add(new Livro(3L, "Código Limpo", "Autor Desconhecido"));
    }
}