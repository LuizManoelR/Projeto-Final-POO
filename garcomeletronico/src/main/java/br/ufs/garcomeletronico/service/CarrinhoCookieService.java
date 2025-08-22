package br.ufs.garcomeletronico.service;

import br.ufs.garcomeletronico.model.*;
import br.ufs.garcomeletronico.dto.CarrinhoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

public class CarrinhoCookieService {
    
    private ObjectMapper mapper = new ObjectMapper();



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

            // Adiciona o cookie na resposta
            response.addCookie(cookie); // navegador grava o cookie no cliente

        } catch (Exception e){
            System.err.println("Erro ao salvar cookie: " + e.getMessage());
        }
    }

        // Carregar: Cookie → Base64 → JSON → DTO → Carrinho reconstruído.
        public Carrinho carregar(HttpServletRequest request, Mesa mesa) {
        Carrinho carrinho = new Carrinho(mesa); // cria um novo carrinho vazio
        
        try {
            Cookie[] cookies = request.getCookies(); // pega os cookies da requisição
            if (cookies == null) return carrinho;
            
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
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar cookie: " + e.getMessage());
        }
        
        return carrinho;
    }
    
    // Limpar: Cria cookie vazio com MaxAge=0 → navegador apaga.
    public void limpar(HttpServletResponse response) {
        Cookie cookie = new Cookie("carrinho", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
