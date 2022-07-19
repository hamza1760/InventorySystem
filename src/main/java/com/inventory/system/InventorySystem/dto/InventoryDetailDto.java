package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.*;
import com.inventory.system.InventorySystem.constant.*;

public class InventoryDetailDto {

    private int inventoryId;
    private String itemSize;
    private int inStock;
    private int avlQty;
    private int inTransit;
    private int minOrderQuantity;
    private int quantityPerBox;
    private int reorderPoint;
    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();
    private ItemDto item;
    private ItemTypeDto itemType;
    @JsonIgnore
    private WarehouseDto warehouse;

    public InventoryDetailDto() {
    }

    public InventoryDetailDto(int inventoryId, String itemSize, int inStock, int avlQty, int inTransit, int minOrderQuantity, int quantityPerBox, int reorderPoint, String status) {
        this.inventoryId = inventoryId;
        this.itemSize = itemSize;
        this.inStock = inStock;
        this.avlQty = avlQty;
        this.inTransit = inTransit;
        this.minOrderQuantity = minOrderQuantity;
        this.quantityPerBox = quantityPerBox;
        this.reorderPoint = reorderPoint;
        this.status = status;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getAvlQty() {
        return avlQty;
    }

    public void setAvlQty(int avlQty) {
        this.avlQty = avlQty;
    }

    public int getInTransit() {
        return inTransit;
    }

    public void setInTransit(int inTransit) {
        this.inTransit = inTransit;
    }

    public int getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public void setMinOrderQuantity(int minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    public int getQuantityPerBox() {
        return quantityPerBox;
    }

    public void setQuantityPerBox(int quantityPerBox) {
        this.quantityPerBox = quantityPerBox;
    }

    public int getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(int reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public String getStatus() {
        return status;
    }

    public ItemDto getItem() {
        return item;
    }

    public void setItem(ItemDto item) {
        this.item = item;
    }

    public ItemTypeDto getItemType() {
        return itemType;
    }

    public void setItemType(ItemTypeDto itemType) {
        this.itemType = itemType;
    }

    public WarehouseDto getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseDto warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public String toString() {
        return "InventoryDetailDto{" +
                "inventoryId=" + inventoryId +
                ", itemSize='" + itemSize + '\'' +
                ", inStock=" + inStock +
                ", avlQty=" + avlQty +
                ", inTransit=" + inTransit +
                ", minOrderQuantity=" + minOrderQuantity +
                ", quantityPerBox=" + quantityPerBox +
                ", reorderPoint=" + reorderPoint +
                ", status='" + status + '\'' +
                ", item=" + item +
                ", itemType=" + itemType +
                ", warehouse=" + warehouse +
                '}';
    }
}
