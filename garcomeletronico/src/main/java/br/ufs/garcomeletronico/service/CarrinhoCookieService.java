package br.ufs.garcomeletronico.service;

import br.ufs.garcomeletronico.model.*;
import br.ufs.garcomeletronico.event.CarrinhoEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.math.BigDecimal;

@Service
public class CarrinhoCookieService {
    
    private final ObjectMapper mapper = new ObjectMapper();
    private final ApplicationEventPublisher publisher;

    public CarrinhoCookieService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void salvar(HttpServletResponse response, Carrinho carrinho) {
        try {
            // Criar objeto JSON
            ObjectNode rootNode = mapper.createObjectNode();
            rootNode.put("mesaId", carrinho.getMesa().getId());

            ArrayNode itensArray = rootNode.putArray("itens");
            for (Item item : carrinho.getCarrinho()) {
                ObjectNode itemNode = mapper.createObjectNode();
                itemNode.put("id", item.getProduto().getId());
                itemNode.put("nome", item.getProduto().getNome());
                itemNode.put("preco", item.getProduto().getPreco());
                itemNode.put("quantidade", item.getQuantidade());
                itensArray.add(itemNode);
            }

            // Serializar e salvar no cookie
            String json = mapper.writeValueAsString(rootNode);
            String encoded = Base64.getEncoder().encodeToString(json.getBytes());

            Cookie cookie = new Cookie("carrinho", encoded);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            publisher.publishEvent(new CarrinhoEvent("SALVO", carrinho));

        } catch (Exception e) {
            System.err.println("Erro ao salvar cookie: " + e.getMessage());
            publisher.publishEvent(new CarrinhoEvent("ERRO AO SALVAR", carrinho));
        }
    }

    public Carrinho carregar(HttpServletRequest request, Mesa mesa) {
        Carrinho carrinho = new Carrinho(mesa);

        try {
            Cookie[] cookies = request.getCookies();
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

                            Produto produto = new Produto(id, nome, "", preco);
                            for (int j = 0; j < quantidade; j++) {
                                carrinho.add(produto);
                            }
                        }
                        break;
                    }
                }
            }
            publisher.publishEvent(new CarrinhoEvent("CARREGADO", carrinho));
        } catch (Exception e) {
            System.err.println("Erro ao carregar cookie: " + e.getMessage());
            publisher.publishEvent(new CarrinhoEvent("ERRO AO CARREGAR", carrinho));
        }

        return carrinho;
    }

    public void limpar(HttpServletResponse response) {
        Cookie cookie = new Cookie("carrinho", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        publisher.publishEvent(new CarrinhoEvent("LIMPO", null));
    }
}
