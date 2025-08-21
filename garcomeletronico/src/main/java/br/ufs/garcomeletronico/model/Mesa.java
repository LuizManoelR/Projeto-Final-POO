package br.ufs.garcomeletronico.model;

public class Mesa implements Identificavel {
    private String id;
    private MesaStatus status;
    private Boolean chamouGarcom;
    private static int ultimo = 1;

    public Mesa() {
        this.id = String.format("Mesa%04d", ultimo++);
        this.status = MesaStatus.LIVRE;
        this.chamouGarcom = false;
    }

@Override
    public String getId() {
        return this.id;
    }

    public String getStatus() {
        return status.getStatus();
    }

    public Boolean getChamouGarcom() {
        return chamouGarcom;
    }

    public void mudarStatus() {
        if (status. equals(MesaStatus.LIVRE)) {
            this.status = MesaStatus.OCUPADA;
        } else {
            this.status = MesaStatus.LIVRE;
        }
    }

    public void chamouGarcom() {
        this.chamouGarcom = true;
        System.out.println("Mesa " + id + " chamou o garçom.");
    }

    public void resetarChamadaGarcom(){
        this.chamouGarcom = false;
    }

 
    

    @Override
    public String toString() {
        return String.format
        ("Mesa         : %s\n"+ 
         "Status       : %s\n" + 
         "Chamou Garçom: %s\n",
          getId(),
          getStatus(),
          getChamouGarcom());
    }

    public void exibir() {
        System.out.println(this);
    }
}