package com.inventory.system.InventorySystem.entities;

import com.inventory.system.InventorySystem.constant.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.*;

@Entity
@Proxy(lazy = false)
public class BrandDetail {

    @Id
    private int brandId;
    private String brandName;
    private String status = Constants.ACTIVE.getValue();

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "brand")
    private Item item;

    public BrandDetail() {
    }

    public BrandDetail(int brandId, String brandName, String status) {
        super();
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

    public Item getItem() {
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandDetail that = (BrandDetail) o;
        return brandId == that.brandId && Objects.equals(brandName, that.brandName) && Objects.equals(status, that.status) && Objects.equals(item, that.item);
    }

}
