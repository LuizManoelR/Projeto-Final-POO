package br.ufs.garcomeletronico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Controller
@SessionAttributes("carrinho")
public class HomeController {


    @ModelAttribute("carrinho")
    public Carrinho carrinho() {
        return new Carrinho();
    }

    @GetMapping("/")
    public String index(Model model, @ModelAttribute("carrinho") Carrinho carrinho) {

        model.addAttribute("mesa", "Mesa 002");

        model.addAttribute("q", "");

        List<Categoria> categorias = Arrays.asList(
                new Categoria("BEBIDAS", "Bebidas"),
                new Categoria("PRATOS PRINCIPAIS", "Pratos Principais"),
                new Categoria("SOBREMESAS", "Sobremesas"),
                new Categoria("ENTRADAS", "Entradas")
        );
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoriaAtual", null);

        List<Item> itens = Arrays.asList(
                new Item(1L, "Coca-Cola", "Refrigerante gelado", BigDecimal.valueOf(6.50), "/img/produto17.jpg"));
        model.addAttribute("itens", itens);

        return "index";
    }

    @PostMapping("/adicionar/{id}")
    public String adicionar(@PathVariable Long id, @ModelAttribute("carrinho") Carrinho carrinho) {
        Item item = encontrarItem(id);
        if (item != null) {
            carrinho.adicionar(item);
        }
        return "redirect:/";
    }

    @PostMapping("/remover/{id}")
    public String remover(@PathVariable Long id,@ModelAttribute("carrinho") Carrinho carrinho) {
        Item item = encontrarItem(id);
        if (item != null) {
            carrinho.remover(item);
        }
        return "redirect:/";
    }

    public record Categoria(String name, String rotulo) {}

    public static class Item {
        private Long id;
        private String nome;
        private String descricao;
        private BigDecimal preco;
        private String imagem;

        public Item(Long id, String nome, String descricao, BigDecimal preco, String imagem) {
            this.id = id;
            this.nome = nome;
            this.descricao = descricao;
            this.preco = preco;
            this.imagem = imagem;
        }

        public Long getId() { return id; }
        public String getNome() { return nome; }
        public String getDescricao() { return descricao; }
        public BigDecimal getPreco() { return preco; }
        public String getImagem() { return imagem; }
    }

    public static class Carrinho {
        private Map<Item, Integer> itens = new HashMap<>();

        public void adicionar(Item item) {
            itens.merge(item, 1, Integer::sum);
        }

        public void remover(Item item) {
            if (itens.containsKey(item)) {
                int qtd = itens.get(item);
                if (qtd > 1) {
                    itens.put(item, qtd - 1);
                } else {
                    itens.remove(item);
                }
            }
        }

        public int getQuantidadeTotal() {
            return itens.values().stream().mapToInt(Integer::intValue).sum();
        }

        public BigDecimal getTotal() {
            return itens.entrySet().stream()
                    .map(e -> e.getKey().getPreco().multiply(BigDecimal.valueOf(e.getValue())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        public Map<Item, Integer> getItens() {
            return itens;
        }
    }

    private Item encontrarItem(Long id) {
        return switch (id.intValue()) {
            case 1 -> new Item(1L, "Coca-Cola", "Refrigerante gelado", BigDecimal.valueOf(6.50), "/img/produto17.jpg");
            default -> null;
        };
    }
}