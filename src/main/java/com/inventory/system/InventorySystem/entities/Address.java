package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Address {

	@Id
	private int addressId;
	private long postalCode;
	private String areaName;
	private String street;

	@JsonIgnore
	private String status = "active";

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id_fk")
	private CityDetail city;

	@OneToMany(fetch = FetchType.EAGER,mappedBy = "address")
	private Set<Warehouse> warehouses = new HashSet<>();


	public Address() {
		super();
	}

	public Address(String status ,int addressId, long postalCode, String areaName, String street) {
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

	public Set<Warehouse> getWarehouses() {
		return warehouses;
	}

	public void setWarehouse(Warehouse warehouse) {
		warehouses.add(warehouse);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
