package com.inventory.system.InventorySystem.entities;

import com.inventory.system.InventorySystem.constant.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "item")
@Proxy(lazy = false)
public class Item {

    @Id
    @Column(name = "item_id")
    @Positive
    private int itemId;
    @NotEmpty
    private String itemName;
    private String status = Constants.ACTIVE.getValue();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productTypeId")
    private ProductType productType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brandId")
    private BrandDetail brand;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item")
    private Set<InventoryDetail> inventory = new HashSet<>();

    public Item() {
    }

    public Item(int itemId, String itemName, String status) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.status = status;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setBrand(BrandDetail brand) {
        this.brand = brand;
    }

    public BrandDetail getBrand() {
        return brand;
    }

    public void setInventory(Set<InventoryDetail> inventory) {
        this.inventory = inventory;
    }

    public Set<InventoryDetail> getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", status='" + status + '\'' +
                ", productType=" + productType +
                ", brand=" + brand +
                ", inventory=" + inventory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId && Objects.equals(itemName, item.itemName) && Objects.equals(status, item.status) && Objects.equals(productType, item.productType) && Objects.equals(brand, item.brand) && Objects.equals(inventory, item.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, status, productType, brand, inventory);
    }
}
