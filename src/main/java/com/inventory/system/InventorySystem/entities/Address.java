package com.inventory.system.InventorySystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {

	@Id
	private int addressId;
	private long postalCode;
	private String areaName;
	private String street;

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Address(int addressId, long postalCode, String areaName, String street) {
		this.addressId = addressId;
		this.postalCode = postalCode;
		this.areaName = areaName;
		this.street = street;
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

}
