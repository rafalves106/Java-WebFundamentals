/**
 * @author falvesmac
 */

package br.com.falves.Quarto_Modulo.Projeto;

import java.util.ArrayList;
import java.util.List;

public class EstoqueRepository {
    private List<Produto> estoque = new ArrayList<>();

    public Produto buscarProdutoPorId(Long id){
        return estoque.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void adicionaProduto(Produto p){
        estoque.add(p);
    }

    public boolean deletarProduto(Long id){
        Produto produto = buscarProdutoPorId(id);

        if (produto != null){
            estoque.remove(produto);
            return true;
        } else {
            return false;
        }
    }

    public List<Produto> getEstoque() {
        return estoque;
    }

    public EstoqueRepository() {
        estoque.add(new Produto(1L, 129.99, "Moletom Say More", Categorias.HOODIE, Tamanhos.M));
        estoque.add(new Produto(2L, 149.99, "Camiseta Oversized Think", Categorias.OVERSIZED, Tamanhos.P));
        estoque.add(new Produto(3L, 119.99, "Camiseta Pima Jesus", Categorias.PIMA, Tamanhos.G));
    }
}