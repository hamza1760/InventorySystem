package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.system.InventorySystem.constant.Constants;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Proxy(lazy = false)
public class ProductType {

    @Id
    private int productTypeId;
    private String productType;

    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "productType")
    private Item item;

    public ProductType() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ProductType(String status, int productTypeId, String productType) {
        super();
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

    public Item getItem() {
        return item;
    }
}
