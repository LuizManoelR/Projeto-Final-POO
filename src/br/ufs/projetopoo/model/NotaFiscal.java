package br.ufs.projetopoo.model;

import java.time.LocalDateTime;

public class NotaFiscal implements Identificavel{
    private Comanda comanda;
    private LocalDateTime data;
    private String id;
    
    public Comanda getComanda() {
        return comanda;
    }

    public LocalDateTime getData() {
        return data;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "NotaFiscal{id='" + id + "', data=" + data + ", comanda=" + comanda + "}";
    }

    public void exibir() {
        System.out.println(this.toString());
    }
}

