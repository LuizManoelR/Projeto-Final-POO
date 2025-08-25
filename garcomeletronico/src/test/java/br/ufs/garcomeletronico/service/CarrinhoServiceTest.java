package br.ufs.garcomeletronico.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.List;

import br.ufs.garcomeletronico.model.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CarrinhoServiceTest {
    // mocks das dependências
    CarrinhoCookieService carrinhoCookieService;
    PedidoService pedidoService;
    ComandaService comandaService;

    // serviço a ser testado
    CarrinhoService carrinhoService;

    // mocks de request/response
    HttpServletRequest request;
    HttpServletResponse response;

    @BeforeEach
    void setup(){
        // Antes de cada teste, criamos mocks das dependências usando Mockito
        carrinhoCookieService = mock(CarrinhoCookieService.class);
        pedidoService = mock(PedidoService.class);
        comandaService = mock(ComandaService.class);
        // Criar uma instância real do CarrinhoService, injetando os mocks
        carrinhoService = new CarrinhoService(carrinhoCookieService, pedidoService, comandaService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    void deveIniciarCarrinho(){
        Mesa mesa = new Mesa();
        carrinhoService.iniciar(response, mesa);
        // verifica se salvar foi chamado
        verify(carrinhoCookieService).salvar(any(HttpServletResponse.class), any(Carrinho.class)); // any() é usado para dizer que qualquer objeto desse tipo é aceitável
    }

    @Test
    void deveAdicionarProdutoAoCarrinho(){
        Produto produto = new Produto("Café",  "desc", "BEBIDA", BigDecimal.valueOf(2.5));
        Carrinho carrinho = new Carrinho(new Mesa());

        // when().thenReturn(): Define comportamento do mock
        when(carrinhoCookieService.carregar(request)).thenReturn(carrinho); // mock do método carregar p/ retornar carrinho vazio

        carrinhoService.adicionar(request, response, produto);

        //  verificar se o produto foi adicionado
        List<Item> itens = carrinho.getCarrinho();
        assertEquals(1, itens.size());
        assertEquals(produto, itens.get(0).getProduto());

        // Verificar se salvar foi chamado
        verify(carrinhoCookieService).salvar(response, carrinho);
    }

    @Test
    void deveRemoverProdutoDoCarrinho() {
        // Arrange
        Produto produto = new Produto("Café", "Café forte", "BEBIDA", new BigDecimal("2.50"));
        Mesa mesa = new Mesa();
        Carrinho carrinho = new Carrinho(mesa);
        carrinho.add(produto); // adiciona 1 unidade

        when(carrinhoCookieService.carregar(any(HttpServletRequest.class))).thenReturn(carrinho);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Act
        carrinhoService.remover(request, response, produto);

        // Assert
        // Verifica se o carrinho ficou vazio e se salvar() foi chamado
        assertTrue(carrinho.getCarrinho().isEmpty(), "Carrinho deve ficar vazio após remover o produto");
        verify(carrinhoCookieService).salvar(response, carrinho);
    }
    
    @Test
    void deveListarItensDoCarrinho(){
        Produto produto = new Produto("Café", "desc", "BEBIDA", BigDecimal.valueOf(2.5));
        Carrinho carrinho = new Carrinho(new Mesa());
        carrinho.add(produto);
    
                when(carrinhoCookieService.carregar(request)).thenReturn(carrinho);

        List<Item> itens = carrinhoService.listar(request);

        assertEquals(1, itens.size());
        assertEquals(produto, itens.get(0).getProduto());
    }

    @Test
    void deveFinalizarCarrinho() {
        Mesa mesa = new Mesa();
        Produto produto = new Produto("Café", "desc", "BEBIDA", BigDecimal.valueOf(2.5));
        Carrinho carrinho = new Carrinho(mesa);
        carrinho.add(produto);

        when(carrinhoCookieService.carregar(request)).thenReturn(carrinho);

        carrinhoService.finalizar(request, response, mesa.getId());

        // verificar chamadas aos serviços

        verify(comandaService).adicionarItem(eq(mesa.getId()), anyList());
        verify(pedidoService).criarPedido(anyList(), eq(mesa));
        // Verificar se o carrinho foi esvaziado
        assertTrue(carrinho.getCarrinho().isEmpty());

        // Verificar se o carrinho foi salvo novamente
        verify(carrinhoCookieService).salvar(response, carrinho);

        // carrinho deve estar vazio
        assertTrue(carrinho.getCarrinho().isEmpty());
    }
}
