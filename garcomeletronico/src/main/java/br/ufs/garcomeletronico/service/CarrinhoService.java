package br.ufs.garcomeletronico.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import br.ufs.garcomeletronico.model.Carrinho;
import br.ufs.garcomeletronico.model.Item;
import br.ufs.garcomeletronico.model.Mesa;
import br.ufs.garcomeletronico.model.Produto;


@Service
public class CarrinhoService {

    CarrinhoCookieService carrinhoCookieService;
    PedidoService pedidoService;
    ComandaService comandaService;

    public CarrinhoService(CarrinhoCookieService carrinhoCookieService, 
                           PedidoService pedidoService, 
                           ComandaService comandaService){

        this.carrinhoCookieService = carrinhoCookieService;
        this.pedidoService = pedidoService;
        this.comandaService = comandaService;
    }

    public void iniciar(HttpServletResponse response,Mesa mesa){
        
        carrinhoCookieService.salvar(response, new Carrinho(mesa));

    }

    public void adicionar(HttpServletRequest request,HttpServletResponse response,Produto produto){

        Carrinho carrinho = carrinhoCookieService.carregar(request);

        carrinho.add(produto);

        carrinhoCookieService.salvar(response, carrinho);

    }
    public String valorTotal(HttpServletRequest request){

        Carrinho carrinho = carrinhoCookieService.carregar(request);
        
        if (carrinho == null) return "0";

        return String.format("%.2f", carrinho.getValorTotal());

    }
    public void remover(HttpServletRequest request,HttpServletResponse response,Produto produto){

        Carrinho carrinho = carrinhoCookieService.carregar(request);

        carrinho.remove(produto);

        carrinhoCookieService.salvar(response, carrinho);

    }
    
    public List<Item> listar(HttpServletRequest request){

        Carrinho carrinho = carrinhoCookieService.carregar(request);

        return carrinho.getCarrinho();

    }



    public void finalizar(HttpServletRequest request,HttpServletResponse response, String id){

        Carrinho carrinho = carrinhoCookieService.carregar(request);

        comandaService.adicionarItem(id,carrinho.getCarrinho());
        pedidoService.criarPedido(carrinho.getCarrinho(), carrinho.getMesa());

        carrinho.esvaziar();
        
        carrinhoCookieService.salvar(response, carrinho);

    }

    public void limpar(HttpServletResponse response){

        carrinhoCookieService.limpar(response);

    }


}
