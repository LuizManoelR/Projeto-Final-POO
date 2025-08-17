package br.ufs.projetopoo.model;

import br.ufs.projetopoo.utils.BigDecimalUtils;

public class Mesa implements Identificavel {
    private String id;
    private String status;
    private Boolean chamouGarcom;

    public Mesa(String id) {
        this.id = id;
        this.status = "Livre";
        this.chamouGarcom = false;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getChamouGarcom() {
        return chamouGarcom;
    }

@Override
    public String getId() {
        return this.id;
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
    }

    @Override
    public String toString() {
        return "Mesa{" + "id='" + id + '\'' +", status='" + status + '\'' +", chamouGarcom=" + chamouGarcom +'}';
    }

    public void exibir() {
        System.out.println(this);
    }
}