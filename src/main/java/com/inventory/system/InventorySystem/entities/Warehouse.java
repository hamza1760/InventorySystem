package com.inventory.system.InventorySystem.entities;

import java.util.List;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "warehouse")
@Proxy(lazy = false)
public class Warehouse {

	@Column(name = "warehouse_id")
	@Id
	private int warehouseId;

	@Column(name = "warehouse_name")
	private String warehouseName;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Item> item;

	public Warehouse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Warehouse(int warehouseId, String warehouseName) {
		super();
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
	}

	public List<Item> getItem() {
		return item;
	}

	public void putItemIntoWareHouse(Item item2) {

		item.add(item2);

	}

}
