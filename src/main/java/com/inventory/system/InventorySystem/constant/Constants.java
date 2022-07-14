package com.inventory.system.InventorySystem.constant;

public enum Constants {

    //Already exists exception constant
    COUNTRY_ALREADY_EXISTS("Country Already Exist"),
    CITY_ALREADY_EXISTS("City Already Exist"),
    ADDRESS_ALREADY_EXISTS("Address Already Exist"),
    WAREHOUSE_ALREADY_EXISTS("Warehouse Already Exist"),
    BRAND_ALREADY_EXISTS("Brand Already Exist"),
    PRODUCT_TYPE_ALREADY_EXISTS("Product Type Already Exist"),
    ITEM_ALREADY_EXISTS("Item Already Exist"),
    ITEM_TYPE_ALREADY_EXISTS("Item Type Already Exist"),
    INVENTORY_ALREADY_EXISTS("Inventory Already Exist"),

    //Not found exception constant
    COUNTRY_NOT_FOUND("Country Not Found"),
    CITY_NOT_FOUND("City Not Found"),
    ADDRESS_NOT_FOUND("Address Not Found"),
    WAREHOUSE_NOT_FOUND("Warehouse Not Found"),
    BRAND_NOT_FOUND("Brand Not Found"),
    PRODUCT_TYPE_NOT_FOUND("Product Type Not Found"),
    ITEM_NOT_FOUND("Item Not Found"),
    ITEM_TYPE_NOT_FOUND("Item Type Not Found"),
    INVENTORY_NOT_FOUND("Inventory Not Found"),

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
