package com.inventory.system.InventorySystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BrandDetail {

	@Id
	private int brandId;
	private String brandName;

	public BrandDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BrandDetail(int brandId, String brandName) {
		super();
		this.brandId = brandId;
		this.brandName = brandName;
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

}
