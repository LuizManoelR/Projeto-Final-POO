package br.ufs.garcomeletronico.service;

import br.ufs.garcomeletronico.model.*;
import br.ufs.garcomeletronico.observer.CarrinhoObserver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.math.BigDecimal;

@Service
public class CarrinhoCookieService {
    
    private ObjectMapper mapper = new ObjectMapper(); // usado para montar/ler JDON
    private List<CarrinhoObserver> observers = new ArrayList<>(); // lista de interessados que serãao avisados em cada ação

    public void adicionarObserver(CarrinhoObserver observer) {
        observers.add(observer);
    }

    // chama onCarrinhoAlternado(...) em cada observer;  Exceções de um observer não interrompem os outros
    private void notificar(String acao, Carrinho carrinho) {
        for(CarrinhoObserver observer : observers) {
            try {
                observer.onCarrinhoAlterado(acao, carrinho);
            } catch(Exception e) {
                System.err.println("Erro ao notificar observer: " + e.getMessage());
            }
        }
    }

    public void salvar(HttpServletResponse response, Carrinho carrinho) {
        try {
            // Criar objeto JSON diretamente
            ObjectNode rootNode = mapper.createObjectNode();
            rootNode.put("mesaId", carrinho.getMesa().getId()); // contém mesaId e um vetor de itens
            
            ArrayNode itensArray = rootNode.putArray("itens");
            
            for (Item item : carrinho.getCarrinho()) { // Para cada item do carrinho, cria-se um itemNode
                ObjectNode itemNode = mapper.createObjectNode();
                itemNode.put("id", item.getProduto().getId());
                itemNode.put("nome", item.getProduto().getNome());
                itemNode.put("preco", item.getProduto().getPreco());
                itemNode.put("quantidade", item.getQuantidade());
                itensArray.add(itemNode);
            }

            /*
             * Exemplo gerado:
             * {
                "mesaId": "MESA-123",
                "itens": [
                    {"id":"P01","nome":"Refrigerante","preco":6.50,"quantidade":2},
                    {"id":"P02","nome":"Hambúrguer","preco":25.00,"quantidade":1}
                ]
                }
             */

            String json = mapper.writeValueAsString(rootNode);
            String encoded = Base64.getEncoder().encodeToString(json.getBytes());

            Cookie cookie = new Cookie("carrinho", encoded);
            cookie.setMaxAge(7*24*60*60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie); // envia o cookie ao cliente
            notificar("SALVO", carrinho);

        } catch (Exception e) {
            System.err.println("Erro ao salvar cookie: " + e.getMessage());
            notificar("ERRO AO SALVAR", carrinho);
        }
    }

    public Carrinho carregar(HttpServletRequest request, Mesa mesa) {
        Carrinho carrinho = new Carrinho(mesa); // cria um carrinho vazio para a Mesa recebida como argumento
        try {
            Cookie[] cookies = request.getCookies(); // Lê os cookies da requisição
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("carrinho".equals(cookie.getName())) {
                        String decoded = new String(Base64.getDecoder().decode(cookie.getValue()));
                        ObjectNode rootNode = (ObjectNode) mapper.readTree(decoded);
                        
                        ArrayNode itensArray = (ArrayNode) rootNode.get("itens");
                        for (int i = 0; i < itensArray.size(); i++) {
                            ObjectNode itemNode = (ObjectNode) itensArray.get(i);
                            
                            String id = itemNode.get("id").asText();
                            String nome = itemNode.get("nome").asText();
                            BigDecimal preco = new BigDecimal(itemNode.get("preco").asText());
                            int quantidade = itemNode.get("quantidade").asInt();
                            
                            Produto produto = new Produto(id, nome, "", preco); // reconstroi Produto
                            for (int j = 0; j < quantidade; j++) {
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

    public void limpar(HttpServletResponse response) {
        Cookie cookie = new Cookie("carrinho", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        notificar("LIMPO", null);
    }
}