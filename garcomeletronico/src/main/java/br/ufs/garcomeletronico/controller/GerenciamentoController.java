package br.ufs.garcomeletronico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import br.ufs.garcomeletronico.model.Carrinho;
import br.ufs.garcomeletronico.service.CarrinhoCookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
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

    @PostMapping()
    public void iniciar(HttpServletResponse response, HttpServletRequest request){

       carrinhoController.iniciarCarrinho(mesaController.iniciar().getId(), response);
       comandaController.iniciar(response, request);

    }
    public void reset(HttpServletResponse response, HttpServletRequest request){
        
        Carrinho carrinho = carrinhoCookieService.carregar(request);

        mesaController.mudarStatus(carrinho.getMesa().getId());

        carrinhoCookieService.limpar(response);
        
       comandaController.resetComanda(request, response);

    }


}
