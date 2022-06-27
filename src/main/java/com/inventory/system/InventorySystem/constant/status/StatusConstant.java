package com.inventory.system.InventorySystem.constant.status;

public enum StatusConstant {

    ACTIVE("Active"),
    DELETED("Deleted");

    private final String value;

    StatusConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
