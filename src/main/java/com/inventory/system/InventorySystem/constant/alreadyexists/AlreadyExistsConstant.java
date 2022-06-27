package com.inventory.system.InventorySystem.constant.alreadyexists;

public enum AlreadyExistsConstant {

    COUNTRY_ALREADY_EXISTS("Country Already Exist"),
    CITY_ALREADY_EXISTS("City Already Exist"),
    ADDRESS_ALREADY_EXISTS("Address Already Exist"),
    WAREHOUSE_ALREADY_EXISTS("Warehouse Already Exist"),
    BRAND_ALREADY_EXISTS("Brand Already Exist"),
    PRODUCT_TYPE_ALREADY_EXISTS("Product Type Already Exist"),
    ITEM_ALREADY_EXISTS("Item Already Exist"),
    ITEM_TYPE_ALREADY_EXISTS("Item Type Already Exist"),
    INVENTORY_ALREADY_EXISTS("Inventory Already Exist");


    private final String value;


    AlreadyExistsConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
