package com.arthenyo.desafioPicPay.enums;

public enum TypeUser {

    COMMON("Common"),
    STORE("Store");

    private String description;

    TypeUser(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
