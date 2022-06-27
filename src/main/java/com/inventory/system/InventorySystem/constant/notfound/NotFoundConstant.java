package com.inventory.system.InventorySystem.constant.notfound;

public enum NotFoundConstant {

    COUNTRY_NOT_FOUND("Country Not Found"),
    CITY_NOT_FOUND("City Not Found"),
    ADDRESS_NOT_FOUND("Address Not Found"),
    WAREHOUSE_NOT_FOUND("Warehouse Not Found"),
    BRAND_NOT_FOUND("Brand Not Found"),
    PRODUCT_TYPE_NOT_FOUND("Product Type Not Found"),
    ITEM_NOT_FOUND("Item Not Found"),
    ITEM_TYPE_NOT_FOUND("Item Type Not Found"),
    INVENTORY_NOT_FOUND("Inventory Not Found");

    private final String value;

    NotFoundConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
