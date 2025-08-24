package br.ufs.garcomeletronico.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.ufs.garcomeletronico.model.Carrinho;
import br.ufs.garcomeletronico.model.Mesa;
import br.ufs.garcomeletronico.model.Produto;
import br.ufs.garcomeletronico.model.Comanda;
import br.ufs.garcomeletronico.dao.ProdutoDAO;
import br.ufs.garcomeletronico.service.ProdutoService;


import java.math.BigDecimal;


@SpringBootApplication
public class GarcomeletronicoApplication {
    public static void main(String[] args) {
        SpringApplication.run(GarcomeletronicoApplication.class, args);
    }

    @Bean
    CommandLineRunner runDemo() {
        return args -> {
            ProdutoDAO pd = new ProdutoDAO();
            ProdutoService ps = new ProdutoService();

            ps.criarProduto("Café coado", "Café tradicional coado","BEBIDA",new BigDecimal("4.00"));
            ps.criarProduto("Café espresso", "Café tradicional espresso","BEBIDA",new BigDecimal("4.00"));
            ps.criarProduto("Café latte", "Café tradicional latte","BEBIDA",new BigDecimal("4.00"));

            Mesa mesa = new Mesa();
            Carrinho carrinho = new Carrinho(mesa);

            Produto p1 = pd.buscarPorCodigo("P0001");
            Produto p2 = pd.buscarPorCodigo("P0002");
            Produto p3 = pd.buscarPorCodigo("P0003");

            carrinho.add(p1);
            carrinho.add(p2);
            carrinho.add(p3);

            Comanda comanda = new Comanda();
            comanda.abrirComanda();
            comanda.adicionarItem(carrinho.getCarrinho());
            comanda.exibir();
        };
    }
}
