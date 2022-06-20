package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class BrandDetail {

	@Id
	private int brandId;
	private String brandName;

	@JsonIgnore
	private String status = "active";




	public BrandDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BrandDetail(String status,int brandId, String brandName) {
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
}
