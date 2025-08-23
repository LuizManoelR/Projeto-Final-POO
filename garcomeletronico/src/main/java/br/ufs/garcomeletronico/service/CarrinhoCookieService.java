package br.ufs.garcomeletronico.service;

import br.ufs.garcomeletronico.model.*;
import br.ufs.garcomeletronico.dto.CarrinhoDTO;
import br.ufs.garcomeletronico.observer.CarrinhoObserver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CarrinhoCookieService {
    
    private ObjectMapper mapper = new ObjectMapper();
    // Essa  lista armazena todos os observers que serão notificados
    // quando algo acontece com o carrinho
    private List<CarrinhoObserver> observers = new ArrayList<>();

    // Adicionar observer
    public void adicionarObserver(CarrinhoObserver observer){
        observers.add(observer);
    }

    // Notificar todos os observers
    // esse método notifica todos os observers registrados que algo aconteceu
    private void notificar(String acao, Carrinho carrinho){
        for(CarrinhoObserver observer : observers){
            try{
                observer.onCarrinhoAlterado(acao, carrinho);
            } catch(Exception e){
                System.err.println("Erro ao notificar observer: " + e.getMessage());
            }
        }
    }


    // Salvar: Carrinho → DTO → JSON → Base64 → Cookie
    public void salvar(HttpServletResponse response, Carrinho carrinho){
        try{
            // Transforma o Carrinho real em um DTO com dados básicos
            CarrinhoDTO dto = new CarrinhoDTO();
            dto.mesaId =  carrinho.getMesa().getId();

            for (Item item : carrinho.getCarrinho()){
                CarrinhoDTO.ItemDTO itemDTO = new CarrinhoDTO.ItemDTO(
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getProduto().getPreco(),
                    item.getQuantidade()
                );
                dto.itens.add(itemDTO); 
            }

            //  DTO -> JSON -> Base64
            String json = mapper.writeValueAsString(dto); // Converte de DTO em JSON
            String encoded = Base64.getEncoder().encodeToString(json.getBytes()); // JSON para Base64

            // Criar cookie chamada "carrinho"
            Cookie cookie = new Cookie("carrinho", encoded);
            cookie.setMaxAge(7*24*60*60); // 7 dias
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            // Adiciona o cookie na resposta
            response.addCookie(cookie); // navegador grava o cookie no cliente
            notificar("SALVO", carrinho);

        } catch (Exception e){
            System.err.println("Erro ao salvar cookie: " + e.getMessage());
            notificar("ERRO AO SALVAR", carrinho);
        }
    }

        // Carregar: Cookie → Base64 → JSON → DTO → Carrinho reconstruído.
        public Carrinho carregar(HttpServletRequest request, Mesa mesa) {
            Carrinho carrinho = new Carrinho(mesa); // cria um novo carrinho vazio
            
            try {
                Cookie[] cookies = request.getCookies(); // pega os cookies da requisição
                if (cookies != null){
                    for (Cookie cookie : cookies) {
                        if ("carrinho".equals(cookie.getName())) {
                            
                            // 1. Base64 → JSON → DTO
                            String decoded = new String(Base64.getDecoder().decode(cookie.getValue()));
                            CarrinhoDTO dto = mapper.readValue(decoded, CarrinhoDTO.class);
                            
                            // 2. DTO → Carrinho
                            for (CarrinhoDTO.ItemDTO itemDTO : dto.itens) {
                                // Recriar produto com dados salvos
                                Produto produto = new Produto(itemDTO.id, itemDTO.nome, "", itemDTO.preco);
                                // Adicionar na quantidade correta
                                for (int i = 0; i < itemDTO.quantidade; i++) {
                                    carrinho.add(produto);
                                }
                            }
                            break;
                        }
                    }
                } 
                notificar("CARREGADO", carrinho);
            } catch (Exception e) {
                System.err.println("Erro ao carregar cookie: " + e.getMessage());
                notificar("ERRO AO CARREGAR", carrinho);
            }
            
            return carrinho;
    }

    
    // Limpar: Cria cookie vazio com MaxAge=0 → navegador apaga.
    public void limpar(HttpServletResponse response) {
        Cookie cookie = new Cookie("carrinho", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        notificar("LIMPO", null);
    }
}
