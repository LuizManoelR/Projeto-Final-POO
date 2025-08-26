package br.ufs.garcomeletronico;

import br.ufs.garcomeletronico.service.ProdutoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GarcomeletronicoApplication {

    private final ProdutoService produtoService;

    GarcomeletronicoApplication(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
    public static void main(String[] args) {
        SpringApplication.run(GarcomeletronicoApplication.class, args);
    }

    @Bean
    CommandLineRunner runDemo() {
        return args -> {
            
              

        };
    }
}
