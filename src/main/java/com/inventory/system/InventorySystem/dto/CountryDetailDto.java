package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.system.InventorySystem.constant.Constants;
import com.inventory.system.InventorySystem.entities.CityDetail;

import java.util.HashSet;
import java.util.Set;

public class CountryDetailDto {

    private int countryId;
    private String countryCode;
    private String countryName;
    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();

    @JsonIgnore
    private final Set<CityDetail> cityDetails = new HashSet<>();

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<CityDetail> getCityDetails() {
        return cityDetails;
    }
}
