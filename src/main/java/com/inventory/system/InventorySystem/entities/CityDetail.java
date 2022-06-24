package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CityDetail {

    @Id
    private int cityId;
    private String cityCode;
    private String cityName;
    @JsonIgnore
    private String status = "active";


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id_fk")
    private CountryDetail country;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "city")
    private Set<Address> address = new HashSet<>();


    public CityDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CityDetail(String status, int cityId, String cityCode, String cityName) {
        this.cityId = cityId;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.status = status;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public CountryDetail getCountry() {
        return country;
    }

    public void setCountry(CountryDetail country) {
        this.country = country;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
