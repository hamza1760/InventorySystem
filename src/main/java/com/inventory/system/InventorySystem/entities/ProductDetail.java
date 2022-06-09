package com.inventory.system.InventorySystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductDetail {

	@Id
	private int productId;
	private String productType;

	public ProductDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductDetail(int productId, String productType) {
		super();
		this.productId = productId;
		this.productType = productType;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

}
