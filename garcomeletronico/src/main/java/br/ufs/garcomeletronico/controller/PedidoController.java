package br.ufs.garcomeletronico.controller;

import br.ufs.garcomeletronico.model.Pedido;
import br.ufs.garcomeletronico.service.PedidoService;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){

        this.pedidoService = pedidoService;

    }
    
    // busca os pedidos pendentes
    @GetMapping("/pendente")
    public List<Pedido> pendentes() {
        return pedidoService.listarPendente() ;
    }
    // busca os pedidos pendentes
    @GetMapping("/emProducao")
    public List<Pedido> emProducao() {
        return pedidoService.listarEmProducao() ;
    }
    // busca os pedidos pendentes
    @GetMapping("/concluido")
    public List<Pedido> concluidos() {
        return pedidoService.listarConcluido() ;
    }
    // busca os pedidos pendentes
    @GetMapping("/cancelado")
    public List<Pedido> cancelado() {
        return pedidoService.listarCancelado() ;
    }

    @PostMapping("/produzir/{id}")
    public void produzir(@PathVariable String id){
        pedidoService.produzir(id);
    }
    @PostMapping("/concluir/{id}")
    public void concluir(@PathVariable String id){
        pedidoService.concluir(id);
    }
    @PostMapping("/cancelar/{id}")
    public void cancelar(@PathVariable String id){
        pedidoService.cancelar(id);
    }
    @PostMapping("/coletar/{id}")
    public void coletar(@PathVariable String id){
        pedidoService.pedidoColetado(id);
    }

    @DeleteMapping("/resetCancelados")
    public void resetCanselados(){

        pedidoService.removeCancelados();

    }


    
}
    