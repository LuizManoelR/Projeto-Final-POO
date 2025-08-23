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
    public void atualizar(List<Produto> produtos){
        this.produtos = produtos;
    }

    public Produto buscaPorID(String id){
        return produtos.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtos.stream()
                .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList();
                
    }

    public List<Produto> listar() {
        return new ArrayList<>(produtos); // retorna cópia para evitar alteração externa
    }
}
