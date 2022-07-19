package com.inventory.system.InventorySystem;

import com.inventory.system.InventorySystem.constant.*;
import com.inventory.system.InventorySystem.entities.*;

public class MockData {

    //brand entity
    public BrandDetail getBrand() {
        return new BrandDetail(1, "Adidas", Constants.ACTIVE.getValue());
    }

    //product entity
    public ProductType getProduct() {
        return new ProductType(1, "Shoe", Constants.ACTIVE.getValue());
    }

    //item entity
    public Item getItem() {
        return new Item(1, "AdidasShoe", Constants.ACTIVE.getValue());
    }

    //itemType entity
    public ItemType getItemType() {
        return new ItemType(3, "Finished Product", Constants.ACTIVE.getValue());
    }

    //inventory entity
    public InventoryDetail getInventoryDetail() {
        return new InventoryDetail(1, "small", 40, 20, 35, 70,
                10, 60, Constants.ACTIVE.getValue());
    }

    //warehouse entity
    public Warehouse getWarehouse() {
        return new Warehouse(1, "PAK", Constants.ACTIVE.getValue());
    }

    //address entity
    public Address getAddress() {
        return new Address(1, 75600, "clifton", "10A", Constants.ACTIVE.getValue());
    }

    //Item Size entity
    public ItemSize getItemSize() {
        return new ItemSize(1, 1, "small", "AdidasShoe", "Finished Product", "Shoe", "Adidas");
    }

    //item quantity entity
    public ItemQuantity getItemQuantity() {
        return new ItemQuantity(1, "PAK", "Clifton", "Karachi", "Pakistan", 1, "small", 40, 20, "AdidasShoe", 1, "Finished Product", "Shoe", "Adidas");
    }
}


