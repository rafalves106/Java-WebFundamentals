/**
 * @author falvesmac
 */

package br.com.falves.Quarto_Modulo.Projeto;

public class Produto {
    private Long id;
    private double preco;
    private String titulo;
    private Categorias categoria;
    private Tamanhos tamanho;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public Tamanhos getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanhos tamanho) {
        this.tamanho = tamanho;
    }

    public Produto(Long id, double preco, String titulo, Categorias categoria, Tamanhos tamanho) {
        this.id = id;
        this.preco = preco;
        this.titulo = titulo;
        this.categoria = categoria;
        this.tamanho = tamanho;
    }

    public Produto() {
    }
}