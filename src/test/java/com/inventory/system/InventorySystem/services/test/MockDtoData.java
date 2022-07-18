package com.inventory.system.InventorySystem.services.test;

import com.inventory.system.InventorySystem.constant.*;
import com.inventory.system.InventorySystem.dto.*;

public class MockDtoData {

    //brand dto
    public BrandDetailDto getBrandDetailDto() {
        return new BrandDetailDto(1, "Adidas", Constants.ACTIVE.getValue());
    }

    //product dto
    public ProductTypeDto getProductTypeDto() {
        return new ProductTypeDto(1, "Shoe", Constants.ACTIVE.getValue());
    }

    //item dto
    public ItemDto getItemDto() {
        return new ItemDto(1, "AdidasShoe", Constants.ACTIVE.getValue(),getBrandDetailDto(),getProductTypeDto());
    }

    //itemType dto`
    public ItemTypeDto getItemTypeDto() {
        return new ItemTypeDto(3, "Finished Product", Constants.ACTIVE.getValue());
    }

    //inventory dto
    public InventoryDetailDto getInventoryDetailDto() {
        return new InventoryDetailDto(1, "small", 40, 20, 35, 70,
                10, 60, Constants.ACTIVE.getValue());
    }

    //warehouse dto
    public WarehouseDto getWarehouseDto() {
        return new WarehouseDto(1, "PAK", Constants.ACTIVE.getValue());
    }

    //address dto
    public AddressDto getAddressDto() {
        return new AddressDto(1, 75600, "clifton", "10A", Constants.ACTIVE.getValue());
    }

    //Item Size dto
    public ItemSizeDto getItemSizeDto() {
        return new ItemSizeDto(1, 1, "small", "AdidasShoe", "Finished Product", "Shoe", "Adidas");
    }

    //item quantity dto
    public ItemQuantityDto getItemQuantityDto() {
        return new ItemQuantityDto(1, "PAK", "Clifton", "Karachi", "Pakistan", 1, "small", 40, 20, "AdidasShoe", 1, "Finished Product", "Shoe", "Adidas");
    }
}
