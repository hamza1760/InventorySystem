package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.system.InventorySystem.constant.Constants;

import java.util.HashSet;
import java.util.Set;

public class CityDetailDto {

    private int cityId;
    private String cityCode;
    private String cityName;
    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();
    private CountryDetailDto country;
    @JsonIgnore
    private Set<AddressDto> address = new HashSet<>();

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CountryDetailDto getCountry() {
        return country;
    }

    public void setCountry(CountryDetailDto country) {
        this.country = country;
    }

    public Set<AddressDto> getAddress() {
        return address;
    }

    public void setAddress(Set<AddressDto> address) {
        this.address = address;
    }
}
