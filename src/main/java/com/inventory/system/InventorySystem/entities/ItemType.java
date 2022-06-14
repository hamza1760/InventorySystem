package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ItemType {

	@Id
	private int itemTypeId;
	private String itemType;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER,mappedBy = "itemTypeSet")
	private Set<ProductDetail> products = new HashSet<>();

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

	public ItemType(int itemTypeId, String itemType) {
		super();
		this.itemTypeId = itemTypeId;
		this.itemType = itemType;
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

	public Set<ProductDetail> getProducts() {
		return products;
	}

	public void setItems(Item item){
		items.add(item);
	}

	public Set<Item> getItems() {
		return items;
	}
}
