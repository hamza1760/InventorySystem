package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProductDetail {

	@Id
	private int productId;
	private String productType;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "products")
	private Set<BrandDetail> brands = new HashSet<>();

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

	public Set<BrandDetail> getBrands() {
		return brands;
	}

	public void setBrand(BrandDetail brand) {
		brands.add(brand);
	}
}
