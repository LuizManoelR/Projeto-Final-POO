package br.ufs.garcomeletronico.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufs.garcomeletronico.model.Carrinho;
import br.ufs.garcomeletronico.model.Comanda;
import br.ufs.garcomeletronico.model.Produto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cliente")
public class ClienteViewController {

    private final WebClient webClient;

    public ClienteViewController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping
    public String paginaCliente(@RequestParam(required = false, defaultValue = "todos") String categoria,
                                @RequestParam(required = false) String busca,
                                Model model,
                                HttpSession session,
                                HttpServletRequest request) {

        String cookieHeader = Arrays.stream(request.getCookies())
                                .map(c -> c.getName() + "=" + c.getValue())
                                .collect(Collectors.joining("; "));                                
        // Busca produtos da API
        List<Produto> produtos = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/cardapio")
                        .queryParam("categoria", categoria)
                        .queryParam("busca", busca)
                        .build())
                .retrieve()
                .bodyToFlux(Produto.class)
                .collectList()
                .block();

                // Busca carrinho
        Carrinho carrinho = webClient.get()
                .uri("/carrinho")
                .header("Cookie", cookieHeader)
                .retrieve()
                .bodyToMono(Carrinho.class)
                .block();
        // Busca carrinho
        Comanda comanda = webClient.get()
                .uri("/comanda")
                .header("Cookie", cookieHeader)
                .retrieve()
                .bodyToMono(Comanda.class)
                .block();
        

        //injeta no Thymeleaf
        model.addAttribute("produtos", produtos);
        model.addAttribute("categoria", categoria);
        model.addAttribute("busca", busca);
        model.addAttribute("atendimentoAtivo", carrinho != null);
        model.addAttribute("carrinho", carrinho != null ? carrinho.getCarrinho() : List.of());
        model.addAttribute("totalCarrinho", carrinho != null ? carrinho.getValorTotal() : 0.0);
        model.addAttribute("mesaId", carrinho != null ? carrinho.getMesa().getId() : null);
        model.addAttribute("comandaId", carrinho != null ? comanda.getId() : null);

        return "cliente"; 
    }

    @PostMapping("/iniciar")
    public String iniciarAtendimento(RedirectAttributes redirectAttributes,
                                     HttpSession session,
                                     HttpServletRequest request) {

        String cookieHeader = Arrays.stream(request.getCookies())
                                    .map(c -> c.getName() + "=" + c.getValue())
                                    .collect(Collectors.joining("; "));

                                             
        webClient.post().uri("/gerenciamento/iniciar").header("Cookie", cookieHeader).retrieve().toBodilessEntity().block();
        redirectAttributes.addFlashAttribute("mensagem", "Atendimento iniciado com sucesso!");

        return "redirect:/cliente";
    }

    @PostMapping("/adicionar")
    public String adicionarProduto(@RequestParam String produtoId,
                                   @RequestParam String categoria,
                                   @RequestParam(required = false) String busca,
                                   HttpServletRequest request) {

        String cookieHeader = Arrays.stream(request.getCookies())
                                .map(c -> c.getName() + "=" + c.getValue())
                                .collect(Collectors.joining("; "));       
        
        webClient.post()
                .uri("/carrinho/adicionar?produtoId=" + produtoId)
                .header("Cookie", cookieHeader)
                .retrieve()
                .toBodilessEntity()
                .block();

        return "redirect:/cliente?categoria=" + categoria + "&busca=" + (busca != null ? busca : "");
    }

    @PostMapping("/remover")
    public String removerProduto(@RequestParam String produtoId,
                                 @RequestParam String categoria,
                                 @RequestParam(required = false) String busca,
                                HttpServletRequest request) {

        String cookieHeader = Arrays.stream(request.getCookies())
                                .map(c -> c.getName() + "=" + c.getValue())
                                .collect(Collectors.joining("; "));                                               


        webClient.post()
                .uri("/carrinho/remover?produtoId=" + produtoId)
                .header("Cookie", cookieHeader)
                .retrieve()
                .toBodilessEntity()
                .block();

        return "redirect:/cliente?categoria=" + categoria + "&busca=" + (busca != null ? busca : "");
    }

    @PostMapping("/finalizar")
    public String finalizarPedido(RedirectAttributes redirectAttributes) {
        webClient.post().uri("/comanda/finalizar")
                .retrieve().toBodilessEntity().block();
        redirectAttributes.addFlashAttribute("mensagem", "Pedido enviado para a cozinha!");
        return "redirect:/cliente";
    }

    @PostMapping("/chamar-garcom")
    public String chamarGarcom(RedirectAttributes redirectAttributes) {
        webClient.post().uri("/mesa/chamarGarcom")
                .retrieve().toBodilessEntity().block();
        redirectAttributes.addFlashAttribute("mensagem", "Garçom chamado!");
        return "redirect:/cliente";
    }

    @PostMapping("/resetar")
    public String resetarAtendimento(RedirectAttributes redirectAttributes,
                                     HttpSession session,
                                     HttpServletRequest request) {

        String cookieHeader = Arrays.stream(request.getCookies())
                                    .map(c -> c.getName() + "=" + c.getValue())
                                    .collect(Collectors.joining("; "));
                               


        webClient.post().uri("/comanda/fechar")
                .header("Cookie", cookieHeader)
                .retrieve().toBodilessEntity().block();
        webClient.post().uri("/gerenciamento/resetar")
                .header("Cookie", cookieHeader)
                .retrieve().toBodilessEntity().block();
        redirectAttributes.addFlashAttribute("mensagem", "Atendimento resetado!");
        return "redirect:/cliente";
    }
}
