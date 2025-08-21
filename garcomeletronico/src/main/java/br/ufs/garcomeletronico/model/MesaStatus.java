package br.ufs.garcomeletronico.model;


public enum MesaStatus {

    LIVRE("Livre"),
    OCUPADA("Ocupada");

    private String status;

    MesaStatus(String status){

        this.status = status;

    }

    public String getStatus() {
        return status;
    }

}