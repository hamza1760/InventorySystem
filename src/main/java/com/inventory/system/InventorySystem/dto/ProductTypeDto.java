package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.*;
import com.inventory.system.InventorySystem.constant.*;

import java.util.*;

public class ProductTypeDto {
    private int productTypeId;
    private String productType;
    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();
    @JsonIgnore
    private ItemDto item;

    public ProductTypeDto() {
    }

    public ProductTypeDto(int productTypeId, String productType, String status) {
        this.productTypeId = productTypeId;
        this.productType = productType;
        this.status = status;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ItemDto getItem() {
        return item;
    }

    public void setItem(ItemDto item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTypeDto that = (ProductTypeDto) o;
        return productTypeId == that.productTypeId && Objects.equals(productType, that.productType) && Objects.equals(status, that.status) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productTypeId, productType, status, item);
    }
}
