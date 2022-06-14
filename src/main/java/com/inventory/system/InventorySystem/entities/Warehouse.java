package com.inventory.system.InventorySystem.entities;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class Warehouse {

	@Id
	private int warehouseId;
	private String warehouseName;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id_fk")
	private Address address;




	public Warehouse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Warehouse(int warehouseId, String warehouseName) {
		super();
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
