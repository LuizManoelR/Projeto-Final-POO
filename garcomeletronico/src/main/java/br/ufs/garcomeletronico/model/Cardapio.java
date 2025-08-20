package br.ufs.garcomeletronico.model;
import java.util.ArrayList;
import java.util.List;

public class Cardapio {
    private static Cardapio instancia;
    private List<Produto> produtos;
    private Cardapio(){
        produtos = new ArrayList<>();
    }

    public static synchronized Cardapio getInstancia(){
        if (instancia == null){
            instancia = new Cardapio();
        }
        return instancia;
    }
    // Métodos
    public void adicionarProduto(Produto p){
        produtos.add(p);
    }

    public Produto buscaPorID(String id){
        return produtos.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public Produto buscarPorNome(String nome) {
        return produtos.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    public List<Produto> listar() {
        return new ArrayList<>(produtos); // retorna cópia para evitar alteração externa
    }
}
