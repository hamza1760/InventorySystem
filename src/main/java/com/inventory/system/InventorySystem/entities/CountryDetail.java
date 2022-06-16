package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity

public class CountryDetail {

    @Id
    private int countryId;
    private String countryCode;
    private String countryName;
    @JsonIgnore
    private String status = "active";


    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER , mappedBy = "country")
    private Set<CityDetail> cityDetails = new HashSet<>();

    public CountryDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CountryDetail(String status,int countryId, String countryCode, String countryName) {
        this.countryId = countryId;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.status = status;
    }



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

    public Set<CityDetail> getCityDetails() {
        return cityDetails;
    }

    public  void setCity(CityDetail cityDetail) {
        cityDetails.add(cityDetail);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
