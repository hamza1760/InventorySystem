package com.inventory.system.InventorySystem.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BrandDetail {

	@Id
	private int brandId;
	private String brandName;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			joinColumns = {@JoinColumn(name = "brand_id")},
			inverseJoinColumns = {@JoinColumn(name = "product_id")}
	)
	private Set<ProductDetail> products = new HashSet<>();

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

	public Set<ProductDetail> getProducts() {
		return products;
	}

	public void setProduct(ProductDetail productDetail) {
		products.add(productDetail);
	}
}
