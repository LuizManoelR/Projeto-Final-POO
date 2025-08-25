package br.ufs.garcomeletronico.controller;

import org.springframework.web.bind.annotation.RestController;

import br.ufs.garcomeletronico.model.Comanda;
import br.ufs.garcomeletronico.service.ComandaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("/api/comanda")
public class ComandaController {

    private ComandaService comandaService;

    public ComandaController(ComandaService comandaService){

        this.comandaService = comandaService;
    }

    @GetMapping("/getLivre")
    public String buscarLivres(){

        Comanda comanda = comandaService.buscarLivre();

        return comanda.getId();

    }

    // abri a comanda
    @PostMapping("/abrir")
    public void abrirComanda(HttpServletRequest request) {
        System.out.println("Post recebido");
       
        comandaService.abrirComanda(resgatarId(request));
    }
 
    // fecha a comadna
    @PostMapping("/fechar")
    public void fecharComanda(HttpServletRequest request) {
        System.out.println("Post recebido");
       
        comandaService.fecharComanda(resgatarId(request));
    }
 
    // reseta a comanda
    @PostMapping("/reset")
    public void resetComanda(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("Post recebido");
       
        comandaService.resetComanda(resgatarId(request));
        limpar(response);
    }
    @PostMapping("/iniciar")
    public void iniciar(HttpServletResponse response, HttpServletRequest request) {
        System.out.println("Post recebido");
       
        comandaService.iniciar(response);
        abrirComanda(request);
    }

    @GetMapping("/id")
    public String resgatarId(HttpServletRequest request){

        return comandaService.resgatarId(request);
    }

    @GetMapping
    public Comanda getComanda(HttpServletRequest request){
        
        Comanda comanda = comandaService.buscar(request);
        if (comanda == null) {
        throw new RuntimeException("Comanda não encontrada");
    }
        return comanda;

    }

    @DeleteMapping("/limpar")
    public void limpar(HttpServletResponse response){
        comandaService.limpar(response);
    }
    
    
    

}
