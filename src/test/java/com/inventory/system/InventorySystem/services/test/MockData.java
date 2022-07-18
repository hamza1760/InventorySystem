package com.inventory.system.InventorySystem.services.test;

import com.inventory.system.InventorySystem.constant.Constants;
import com.inventory.system.InventorySystem.entities.*;

import java.util.Arrays;
import java.util.List;

public class MockData {

    //item entity
    public Item getItem() {
        return new Item(1, "AdidasShoe", Constants.ACTIVE.getValue());
    }

    public List<Item> getItems() {
        return Arrays.asList(getItem());
    }

    //product entity
    public ProductType getProduct() {
        return new ProductType(Constants.ACTIVE.getValue(), 1, "Shoe");
    }

    //brand entity
    public BrandDetail getBrand() {
        return new BrandDetail(Constants.ACTIVE.getValue(), 1, "Adidas");
    }

    //inventory entity
    public InventoryDetail getInventoryDetail() {
        return new InventoryDetail(1, "small", 40, 20, 35, 70,
                10, 60, Constants.ACTIVE.getValue());
    }

    public List<InventoryDetail> getInventoryDetails() {
        return Arrays.asList(getInventoryDetail());
    }

    //Item Size entity
    public ItemSize getItemSize() {
       return new ItemSize(1, 1, "small", "AdidasShoe", "Finished Product", "Shoe", "Adidas");
    }
}

