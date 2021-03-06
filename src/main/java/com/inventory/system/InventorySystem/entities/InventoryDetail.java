package com.inventory.system.InventorySystem.entities;

import com.inventory.system.InventorySystem.constant.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.*;

@Entity
@Proxy(lazy = false)
public class InventoryDetail {

    @Id
    private int inventoryId;
    private String itemSize;
    private int inStock;
    private int avlQty;
    private int inTransit;
    private int minOrderQuantity;
    private int quantityPerBox;
    private int reorderPoint;
    private String status = Constants.ACTIVE.getValue();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itemTypeId")
    private ItemType itemType;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    public InventoryDetail() {
    }

    public InventoryDetail(int inventoryId, String itemSize, int inStock, int avlQty, int inTransit, int minOrderQuantity,
                           int quantityPerBox, int reorderPoint, String status) {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    @Override
    public String toString() {
        return "InventoryDetail{" +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryDetail that = (InventoryDetail) o;
        return inventoryId == that.inventoryId && inStock == that.inStock && avlQty == that.avlQty && inTransit == that.inTransit && minOrderQuantity == that.minOrderQuantity && quantityPerBox == that.quantityPerBox && reorderPoint == that.reorderPoint && Objects.equals(itemSize, that.itemSize) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, itemSize, inStock, avlQty, inTransit, minOrderQuantity, quantityPerBox, reorderPoint, status);
    }
}
