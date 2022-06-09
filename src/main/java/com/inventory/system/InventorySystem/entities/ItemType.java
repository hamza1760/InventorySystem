package com.inventory.system.InventorySystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemType {

	@Id
	private int itemTypeId;
	private String itemType;

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

}
