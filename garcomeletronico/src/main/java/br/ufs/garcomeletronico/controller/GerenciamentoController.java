package br.ufs.garcomeletronico.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.garcomeletronico.model.Carrinho;
import br.ufs.garcomeletronico.service.CarrinhoCookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/gereciamento")
public class GerenciamentoController {

    private final CarrinhoCookieService carrinhoCookieService;

    private final ComandaController comandaController;

    private final CarrinhoController carrinhoController;

    private final MesaController mesaController;

    GerenciamentoController(MesaController mesaController, CarrinhoController carrinhoController, ComandaController comandaController, CarrinhoCookieService carrinhoCookieService) {
        this.mesaController = mesaController;
        this.carrinhoController = carrinhoController;
        this.comandaController = comandaController;
        this.carrinhoCookieService = carrinhoCookieService;
    }

    @PostMapping("/iniciar")
    public void iniciar(HttpServletResponse response){

        comandaController.iniciar(response);
        carrinhoController.iniciarCarrinho(mesaController.iniciar().getId(), response);

    }
    @PostMapping("/resetar")
    public void reset(HttpServletResponse response, HttpServletRequest request){
        
        Carrinho carrinho = carrinhoCookieService.carregar(request);

        mesaController.mudarStatus(carrinho.getMesa().getId());

        carrinhoCookieService.limpar(response);
        
        comandaController.resetComanda(request, response);

    }


}
