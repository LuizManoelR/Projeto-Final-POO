package br.ufs.garcomeletronico.model;

// Import das anotações do JUnit
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*; // import das assertivas

public class ProdutoTest {

    @BeforeEach // Esse bloco roda antes de cada teste, isolando os testes
    void resetUltimo() throws Exception {
        // zera o contador estático para testes determinísticos
        Field f = Produto.class.getDeclaredField("ultimo"); // pega o campo privado
        f.setAccessible(true);
        f.setInt(null, 1); 
    }

    /* Produto gera o id com base em um contador estático, sem o reset, a sequência de IDs dependerá da ordem/quantidade de criações em testes anteriores */
    /* assetEquals é usada para comparar dois valores, esperados e reais */
    @Test
    void constructorEGetters() {
        Produto p = new Produto("Café", "Café forte", "BEBIDA", new BigDecimal("2.50"));
        assertEquals("P0001", p.getId()); // como o ultimo está resetado para 1, P0001 é o primeiro id
        assertEquals("Café", p.getNome());
        assertEquals("Café forte", p.getDescricao());
        assertEquals("Bebida", p.getCategoria());
        assertEquals(new BigDecimal("2.50"), p.getPreco());
        assertTrue(p.getImg().endsWith("P0001.png"));
    }

    @Test
    void settersFuncionam() {
        Produto p = new Produto("X", "Y", "BEBIDA", new BigDecimal("1.00"));
        p.setNome("Novo");
        p.setDescricao("Desc");
        p.setCategoria("BEBIDA");
        p.setImg("novaImg.png");
        p.setpreco(new BigDecimal("9.99"));

        assertEquals("Novo", p.getNome());
        assertEquals("Desc", p.getDescricao());
        assertEquals("Bebida", p.getCategoria());
        assertEquals("novaImg.png", p.getImg());
        assertEquals(new BigDecimal("9.99"), p.getPreco());
    }
}
