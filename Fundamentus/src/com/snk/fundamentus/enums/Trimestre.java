package com.snk.fundamentus.enums;

public enum Trimestre {
    primeiro("31/03"), segundo("30/06"), terceiro("30/09"), quarto("31/12");

    private final String trimestre;

    Trimestre(String trimestre) {
        this.trimestre = trimestre;
    }

    public String getValue() {
        return trimestre;
    }
}
