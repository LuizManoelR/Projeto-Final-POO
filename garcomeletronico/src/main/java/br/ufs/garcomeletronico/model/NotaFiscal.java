package br.ufs.garcomeletronico.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotaFiscal implements Identificavel{
    private String id;
    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private LocalDateTime data;
    private String dataformatada;
    private Comanda comanda;
    private static int ultimo = 1;

    public NotaFiscal(Comanda comanda){

        this.comanda = comanda;

        data = LocalDateTime.now();

        dataformatada = data.format(formatter);

        this.id = String.format("NF%04d", ultimo++);

    }
    
    public Comanda getComanda() {
        return comanda;
    }

    public String getData() {
        return dataformatada;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
            "Data: %s\n"+
            "Id  : %s\n\n\n",
            getData(),getId()));
        
       for (int i = 0; i < comanda.getPedidos().size(); i++) {
            sb.append(String.format("%d. %s\n\n", (i + 1), comanda.getPedidos()
                                                                           .get(i).toString()));
        }
        sb.append(String.format("TOTAL: R$ %.2f\n", comanda.valorTotal()));
        
        return sb.toString();
    }

    public void exibir() {
        System.out.println(this.toString());
    }
}

