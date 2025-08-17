package br.ufs.projetopoo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import br.ufs.projetopoo.utils.BigDecimalUtils;

public class Comanda implements Identificavel {
    private String id;
    private List<Item> pedidos;

    public Comanda(String id) {
        this.id = id;
        this.pedidos = new ArrayList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    public void adicionarItem(Item item) {
        pedidos.add(item);
    }

    public BigDecimal valorTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : pedidos) {
            total = BigDecimalUtils.somar(total, item.valorTotal());
        }
        return total;
    }

    public void fecharComanda() {
        System.out.println("Comanda " + id + " fechada. Total: R$ " + valorTotal());
    }

    public void reset() {
        pedidos.clear();
        System.out.println("Comanda " + id + "A Comanda foi resetada e esta vazia novamente. ");
    }

    @Override
    public String toString() {
        return "Comanda{id='" + id +"', pedidos=" + pedidos + "}";
    }

    public void exibir() {
        System.out.println(this);
    }
}