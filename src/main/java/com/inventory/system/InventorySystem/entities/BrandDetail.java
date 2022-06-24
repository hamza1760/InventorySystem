package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity

@Proxy(lazy = false)
public class BrandDetail {

	@Id
	private int brandId;
	private String brandName;

	@JsonIgnore
	private String status = "active";

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER,mappedBy = "brand")
	private Item item;


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

	public Item getItem() {
		return item;
	}
}
