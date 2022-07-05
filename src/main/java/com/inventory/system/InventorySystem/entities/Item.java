package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

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


    private String status = StatusConstant.ACTIVE.getValue();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productTypeId")
    private ProductType productType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brandId")
    private BrandDetail brand;


    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item")
    private Set<InventoryDetail> inventory = new HashSet<>();


    public Item(int itemId, String itemName, String status) {
        super();
        this.itemId = itemId;
        this.itemName = itemName;
        this.status = status;
    }

    public Item() {
        super();
        // TODO Auto-generated constructor stub
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
}
