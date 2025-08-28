package br.ufs.garcomeletronico.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MesaStatus {

    LIVRE("Livre"),
    OCUPADA("Ocupada");

    private String status;

    MesaStatus(String status){

        this.status = status;

    }
    @JsonValue
    public String getStatus() {
        return status;
    }

     @JsonCreator
    public static MesaStatus fromValue(String value) {
        for (MesaStatus s : MesaStatus.values()) {
            if (s.status.equalsIgnoreCase(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + value);
    }

}