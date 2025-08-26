package br.ufs.garcomeletronico.controller;

import br.ufs.garcomeletronico.model.Item;
import br.ufs.garcomeletronico.service.CardapioService;
import br.ufs.garcomeletronico.model.Carrinho;
import br.ufs.garcomeletronico.model.Pedido;
import br.ufs.garcomeletronico.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RestauranteController {

    private final CardapioService cardapioService;
    private final Carrinho carrinho;
    private final PedidoService pedidoService;

    public RestauranteController(CardapioService cardapioService, Carrinho carrinho, PedidoService pedidoService) {
        this.cardapioService = cardapioService;
        this.carrinho = carrinho;
        this.pedidoService = pedidoService;
    }


    @GetMapping("/")
    public String home(@RequestParam(required = false) String q, Model model) {
        return listarPorCategoria(null, q, model);
    }


    @GetMapping("/categoria/{categoria}")
    public String listarPorCategoria(
            @PathVariable(required = false) String categoria,
            @RequestParam(required = false) String q,
            Model model) {


        List<Item> itens = (categoria == null || categoria.isEmpty()) 
                ? cardapioService.listarTodos();
                : cardapioService.filtrarCategoria(categoria);

        if (q != null && !q.isEmpty()) {
            String busca = q.toLowerCase();
            itens = itens.stream()
                    .filter(i -> i.getProduto().get().toLowerCase().contains(busca))
                    .collect(Collectors.toList());
        }


        List<String> mesasChamaramGarcom = pedidoService.listarMesasComChamado();


        List<Pedido> pedidosEmAndamento = pedidoService.listarPorStatus("EM_ANDAMENTO");
        List<Pedido> pedidosProntos = pedidoService.listarPorStatus("PRONTO");
        List<Pedido> pedidosEntregues = pedidoService.listarPorStatus("ENTREGUE");


        model.addAttribute("itens", itens);
        model.addAttribute("categorias", cardapioService.listarCategorias());
        model.addAttribute("categoriaAtual", categoria);
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("mesa", "Mesa 002");
        model.addAttribute("q", q != null ? q : "");

        model.addAttribute("mesasChamaramGarcom", mesasChamaramGarcom);
        model.addAttribute("pedidosEmAndamento", pedidosEmAndamento);
        model.addAttribute("pedidosProntos", pedidosProntos);
        model.addAttribute("pedidosEntregues", pedidosEntregues);

        return "home";
    }
}
@GetMapping("/cozinha")
public String viewCozinha(Model model) {
    List<Pedido> pedidos = pedidoService.listarPedidosAbertos();

    model.addAttribute("pedidos", pedidos);
    return "cozinha";
}