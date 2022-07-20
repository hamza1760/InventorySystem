package com.inventory.system.InventorySystem.constant;

public enum Constants {

    //Already exists exception constant
    WAREHOUSE_ALREADY_EXISTS("Warehouse Already Exist"),
    ITEM_ALREADY_EXISTS("Item Already Exist"),
    INVENTORY_ALREADY_EXISTS("Inventory Already Exist"),

    //Not found exception constant
    ADDRESS_NOT_FOUND("Address Not Found"),
    WAREHOUSE_NOT_FOUND("Warehouse Not Found"),
    BRAND_NOT_FOUND("Brand Not Found"),
    PRODUCT_TYPE_NOT_FOUND("Product Type Not Found"),
    ITEM_NOT_FOUND("Item Not Found"),
    ITEM_TYPE_NOT_FOUND("Item Type Not Found"),
    INVENTORY_NOT_FOUND("Inventory Not Found"),

    //delete message
    ITEM_DELETED("Item deleted"),
    INVENTORY_DELETED("Inventory deleted"),
    WAREHOUSE_DELETED("Warehouse deleted"),

    //Status constant
    ACTIVE("Active"),
    DELETED("Deleted");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
