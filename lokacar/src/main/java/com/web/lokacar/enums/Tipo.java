package com.web.lokacar.enums;

public enum Tipo {
    CARRO("carro"),
    MOTO("moto"),
    VAN("van");

    private String tipo;

    private Tipo(String tipo){
        this.tipo = tipo;
    }
}
