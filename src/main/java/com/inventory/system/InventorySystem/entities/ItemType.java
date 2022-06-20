package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Proxy(lazy = false)
public class ItemType {

	@Id
	private int itemTypeId;
	private String itemType;

	@JsonIgnore
	private String status = "active";


	@ManyToMany(fetch = FetchType.EAGER,mappedBy = "itemTypeSet")
	private Set<ProductType> products = new HashSet<>();

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			joinColumns = {@JoinColumn(name = "item_type_id")},
			inverseJoinColumns = {@JoinColumn(name = "item_id")}
	)
	private Set<Item> items = new HashSet<>();

	public ItemType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemType(String status ,int itemTypeId, String itemType) {
		super();
		this.itemTypeId = itemTypeId;
		this.itemType = itemType;
		this.status = status;
	}

	public int getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(int itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Set<ProductType> getProducts() {
		return products;
	}

	public void setItems(Item item){
		items.add(item);
	}

	public Set<Item> getItems() {
		return items;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
