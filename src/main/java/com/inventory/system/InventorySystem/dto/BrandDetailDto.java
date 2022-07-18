package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.system.InventorySystem.constant.Constants;

import java.util.*;

public class BrandDetailDto {
    private int brandId;
    private String brandName;
    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();
    @JsonIgnore
    private ItemDto item;

    public BrandDetailDto() {
    }

    public BrandDetailDto(int brandId, String brandName,String status) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.status = status;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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
        BrandDetailDto that = (BrandDetailDto) o;
        return brandId == that.brandId && Objects.equals(brandName, that.brandName) && Objects.equals(status, that.status) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, brandName, status, item);
    }
}
