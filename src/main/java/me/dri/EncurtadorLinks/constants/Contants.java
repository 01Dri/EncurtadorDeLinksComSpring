package me.dri.EncurtadorLinks.constants;


public enum Contants {

    ZONE_ID("America/Sao_Paulo");

    private final String value;

    Contants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
