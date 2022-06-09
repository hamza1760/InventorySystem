package com.inventory.system.InventorySystem.entities;

import java.util.List;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "item")
@Proxy(lazy = false)
public class Item {

	@Id
	@Column(name = "item_id")
	private int itemId;

	private String itemName;
	private String itemColor;

	private String password;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "item")
	private List<Warehouse> warehouse;

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

	public List<Warehouse> getWarehouse() {
		return warehouse;
	}

}
