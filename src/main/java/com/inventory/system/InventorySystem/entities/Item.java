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

    private String size;
    private String description;
    private String stock;
    private String color;
    private String brand1;
    private String brandDescription;
    private String material;
    private String seller;
    private String sellerDetail;
    private String sellerNumber;
    private String size1;
    private String description1;
    private String stock1;
    private String color1;
    private String brand2;
    private String brandDescription1;
    private String material1;
    private String seller1;
    private String sellerDetail1;
    private String sellerNumber1;



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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand1() {
        return brand1;
    }

    public void setBrand1(String brand1) {
        this.brand1 = brand1;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSellerDetail() {
        return sellerDetail;
    }

    public void setSellerDetail(String sellerDetail) {
        this.sellerDetail = sellerDetail;
    }

    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    public String getSize1() {
        return size1;
    }

    public void setSize1(String size1) {
        this.size1 = size1;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getStock1() {
        return stock1;
    }

    public void setStock1(String stock1) {
        this.stock1 = stock1;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getBrand2() {
        return brand2;
    }

    public void setBrand2(String brand2) {
        this.brand2 = brand2;
    }

    public String getBrandDescription1() {
        return brandDescription1;
    }

    public void setBrandDescription1(String brandDescription1) {
        this.brandDescription1 = brandDescription1;
    }

    public String getMaterial1() {
        return material1;
    }

    public void setMaterial1(String material1) {
        this.material1 = material1;
    }

    public String getSeller1() {
        return seller1;
    }

    public void setSeller1(String seller1) {
        this.seller1 = seller1;
    }

    public String getSellerDetail1() {
        return sellerDetail1;
    }

    public void setSellerDetail1(String sellerDetail1) {
        this.sellerDetail1 = sellerDetail1;
    }

    public String getSellerNumber1() {
        return sellerNumber1;
    }

    public void setSellerNumber1(String sellerNumber1) {
        this.sellerNumber1 = sellerNumber1;
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
