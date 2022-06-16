package com.inventory.system.InventorySystem.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.sql.Update;

@Entity
@Table(name = "item")
@Proxy(lazy = false)

public class Item {

	@Id
	@Column(name = "item_id")
	private int itemId;

	private String itemName;
	private String itemColor;

	@JsonIgnore
	private String password;

	private String status = "active";

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "item")
	private Set<InventoryDetail> inventoryDetail = new HashSet<>();


	@ManyToMany(fetch = FetchType.EAGER,mappedBy = "items")
	private Set<ItemType> itemTypeSet = new HashSet<>();

	public Item(int itemId, String itemName, String itemColor, String password) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemColor = itemColor;
		this.password = password;
	}

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getItemId() {

		return itemId;
	}

	public void setItemId(int itemId) {

		this.itemId = itemId;
	}

	public String getItemName() {

		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemColor() {
		return itemColor;
	}

	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}




	public Set<ItemType> getItemTypeSet() {
		return itemTypeSet;
	}



	public Set<InventoryDetail> getInventoryDetail() {
		return inventoryDetail;
	}
}
