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

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "itemType")
	private Set<InventoryDetail> inventory = new HashSet<>();


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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<InventoryDetail> getInventory() {
		return inventory;
	}
}
