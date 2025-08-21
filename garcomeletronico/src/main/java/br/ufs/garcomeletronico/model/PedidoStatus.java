package br.ufs.garcomeletronico.model;

public enum PedidoStatus {

    PENDENTE("Pendente"),
    EM_PRODUCAO("Em Produçâo"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private String status;

    PedidoStatus(String status){

        this.status = status;

    }

    public String getStatus(){return status;}

    public static void main(String[] args) {
        System.out.println(PedidoStatus.valueOf("CANCELADO").getStatus());
    }
}
