package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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


	@JsonIgnore
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


	public void setProduct(ProductDetail productDetail) {
		products.add(productDetail);
	}


	public Set<ProductDetail> getProducts() {
		return products;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
