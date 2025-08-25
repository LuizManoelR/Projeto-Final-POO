package br.ufs.garcomeletronico;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.ufs.garcomeletronico.model.Carrinho;
import br.ufs.garcomeletronico.model.Mesa;
import br.ufs.garcomeletronico.model.Produto;
import br.ufs.garcomeletronico.model.Comanda;
import br.ufs.garcomeletronico.dao.ComandaDAO;
import br.ufs.garcomeletronico.dao.MesaDAO;
import br.ufs.garcomeletronico.dao.ProdutoDAO;
import br.ufs.garcomeletronico.service.ComandaService;
import br.ufs.garcomeletronico.service.MesaService;
import br.ufs.garcomeletronico.service.ProdutoService;


import java.math.BigDecimal;
import java.util.List;


@SpringBootApplication
public class GarcomeletronicoApplication {
    public static void main(String[] args) {
        SpringApplication.run(GarcomeletronicoApplication.class, args);
    }

    @Bean
    CommandLineRunner runDemo() {
        return args -> {

            
        
        };
    }
}
