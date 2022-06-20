package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Proxy(lazy = false)
public class ProductType {

	@Id
	private int productTypeId;
	private String productType;

	@JsonIgnore
	private String status = "active";

	public ProductType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductType(String status , int productTypeId, String productType) {
		super();
		this.productTypeId = productTypeId;
		this.productType = productType;
		this.status = status;
	}

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}


}
