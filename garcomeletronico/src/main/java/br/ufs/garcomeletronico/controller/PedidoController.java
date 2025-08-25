package br.ufs.garcomeletronico.controller;

import br.ufs.garcomeletronico.model.Carrinho;
import br.ufs.garcomeletronico.model.Item;
import br.ufs.garcomeletronico.model.Mesa;
import br.ufs.garcomeletronico.model.Pedido;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/pedidos")
public class PedidoController {
    private final List<Pedido> pedidos = new ArrayList<>();

    @GetMapping
    public String listar(Model model){
        model.addAttribute("pedidos", new ArrayList<>(pedidos));
        return "pedidos/list";
    }

    @GetMapping("/novo")
    public String novo(){
        return "pedidos/novo";
    }

    @PostMapping("/criar")
    public String criar(HttpSession session) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho"); // Lê o carrinho 
        if (carrinho == null || carrinho.isEmpty()){
            return "redirect:/carrinho";
        }

        List<Item> itens = new ArrayList<>(carrinho.getCarrinho()); // constrói lista de itens
        Mesa mesa = carrinho.getMesa(); // constrói mesa
        Pedido pedido = new Pedido(itens, mesa);
        pedidos.add(pedido); 

        carrinho.esvaziar();
        session.removeAttribute("carrinho");

        return "redirect:/pedidos/" + pedido.getId();
    }

    // Localizam o pedido e chamam p.metodo() para alterar o status do model
    @GetMapping("/{id}")
    public String ver(@PathVariable String id, Model model) {
        Pedido p = findById(id);
        if (p == null) return "redirect:/pedidos";
        model.addAttribute("pedido", p);
        return "pedidos/ver";
    }

    @PostMapping("/{id}/produzir")
    public String produzir(@PathVariable String id) {
        Pedido p = findById(id);
        if (p != null) p.produzir();
        return "redirect:/pedidos/" + id;
    }

    @PostMapping("/{id}/concluir")
    public String concluir(@PathVariable String id) {
        Pedido p = findById(id);
        if (p != null) p.concluir();
        return "redirect:/pedidos/" + id;
    }

    @PostMapping("/{id}/cancelar")
    public String cancelar(@PathVariable String id) {
        Pedido p = findById(id);
        if (p != null) p.cancelar();
        return "redirect:/pedidos/" + id;
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable String id) {
        pedidos.removeIf(p -> p.getId().equals(id));
        return "redirect:/pedidos";
    }

    // helper
    private Pedido findById(String id) {
        for (Pedido p : pedidos) if (p.getId().equals(id)) return p;
        return null;
    }
    
}
    