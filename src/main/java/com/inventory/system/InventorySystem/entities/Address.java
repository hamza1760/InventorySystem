package com.inventory.system.InventorySystem.entities;

import com.inventory.system.InventorySystem.constant.Constants;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Proxy(lazy = false)
public class Address {

    @Id
    private int addressId;
    private long postalCode;
    private String areaName;
    private String street;
    private String status = Constants.ACTIVE.getValue();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id_fk")
    private CityDetail city;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "address")
    private Warehouse warehouse;

    public Address() {
    }

    public Address(int addressId, long postalCode, String areaName, String street,String status) {
        this.addressId = addressId;
        this.postalCode = postalCode;
        this.areaName = areaName;
        this.street = street;
        this.status = status;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public CityDetail getCity() {
        return city;
    }

    public void setCity(CityDetail city) {
        this.city = city;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
