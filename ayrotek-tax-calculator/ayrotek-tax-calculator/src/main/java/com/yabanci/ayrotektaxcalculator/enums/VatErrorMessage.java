package com.yabanci.ayrotektaxcalculator.enums;

public enum VatErrorMessage{

    VAT_NOT_FOUND("Vat not found!"),;

    private String message;
    VatErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}