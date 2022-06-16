package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProductDetail {

	@Id
	private int productId;
	private String productType;

	@JsonIgnore
	private String status = "active";


	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "products")
	private Set<BrandDetail> brands = new HashSet<>();

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			joinColumns = {@JoinColumn(name = "product_id")},
			inverseJoinColumns = {@JoinColumn(name = "item_type_id")}
	)
	private Set<ItemType> itemTypeSet = new HashSet<>();

	public ProductDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductDetail(String status ,int productId, String productType) {
		super();
		this.productId = productId;
		this.productType = productType;
		this.status = status;
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


	public void setItemType(ItemType itemType) {
		itemTypeSet.add(itemType);
	}


	public Set<ItemType> getItemTypeSet() {
		return itemTypeSet;
	}


}
