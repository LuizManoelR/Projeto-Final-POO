package br.ufs.garcomeletronico.model;

import br.ufs.garcomeletronico.utils.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Mesa implements Identificavel {
    private String id;
    private String status;
    private Boolean chamouGarcom;
    private List<Comanda> comandas; // Lista de comandas da mesa

    public Mesa(String id) {
        this.id = id;
        this.status = "Livre";
        this.chamouGarcom = false;
        this.comandas = new ArrayList<>();
    }

@Override
    public String getId() {
        return this.id;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getChamouGarcom() {
        return chamouGarcom;
    }

    public List<Comanda> getComandas(){
        return new ArrayList<>(comandas); // cópia
    }

    public void mudarStatus() {
        if ("Livre". equalsIgnoreCase(this.status)) {
            this.status = "Ocupado";
        } else {
            this.status = "Livre";
        }
    }

    public void chamouGarcom() {
        this.chamouGarcom = true;
        System.out.println("Mesa " + id + " chamou o garçom.");
    }

    public void resetarChamadaGarcom(){
        this.chamouGarcom = false;
    }

    // Integração com comandas
    public void adicionarComanda(Comanda comanda){
        if (comanda != null){
            this.comandas.add(comanda);
            if (comanda.getMesaId() == null){ // se a Mesa estiver desocupada, é atríbuida uma comanda para ela
                comanda.setMesaId(this.id);
            }
            // Auto-ocupar mesa
            if ("Livre".equalsIgnoreCase(this.status)){
                this.status =  "Ocupado";
            }
        }
    }

    public BigDecimal valorTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (Comanda comanda : comandas){
            if(!"Cancelada".equals(comanda.getStatus())){
                total = total.add(comanda.valorTotal());
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "Mesa{" + "id='" + id + '\'' +", status='" + status + '\'' +", chamouGarcom=" + chamouGarcom +
        ',' + "comandas = " + comandas.size() + "}";
    }

    public void exibir() {
        System.out.println(this);
    }
}