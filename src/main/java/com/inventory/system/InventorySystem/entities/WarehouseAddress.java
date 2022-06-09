package com.inventory.system.InventorySystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WarehouseAddress {

	@Id
	private long postalCode;
	private String areaName;
	private String street;

	public WarehouseAddress() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WarehouseAddress(long postalCode, String areaName, String street) {
		super();
		this.postalCode = postalCode;
		this.areaName = areaName;
		this.street = street;
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
