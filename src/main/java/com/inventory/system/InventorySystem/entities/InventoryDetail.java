package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Proxy(lazy = false)
@Where(clause = "status='active'")
public class InventoryDetail {

	@Id
	private int inventoryId;
	private String size;
	private int inStock;
	private int avlQty;
	private int inTransit;
	private int minOrderQuantity;
	private int quantityPerBox;
	private int reorderPoint;
	@JsonIgnore
	private String status = "active";


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id")
	private Item item;

	public InventoryDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InventoryDetail(String status ,int inventoryId ,String size, int inStock, int avlQty, int inTransit, int minOrderQuantity,
			int quantityPerBox, int reorderPoint) {
		this.inventoryId = inventoryId;
		this.size = size;
		this.inStock = inStock;
		this.avlQty = avlQty;
		this.inTransit = inTransit;
		this.minOrderQuantity = minOrderQuantity;
		this.quantityPerBox = quantityPerBox;
		this.reorderPoint = reorderPoint;
		this.status = status;
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {

		this.inventoryId = inventoryId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public int getAvlQty() {
		return avlQty;
	}

	public void setAvlQty(int avlQty) {
		this.avlQty = avlQty;
	}

	public int getInTransit() {
		return inTransit;
	}

	public void setInTransit(int inTransit) {
		this.inTransit = inTransit;
	}

	public int getMinOrderQuantity() {
		return minOrderQuantity;
	}

	public void setMinOrderQuantity(int minOrderQuantity) {
		this.minOrderQuantity = minOrderQuantity;
	}

	public int getQuantityPerBox() {
		return quantityPerBox;
	}

	public void setQuantityPerBox(int quantityPerBox) {
		this.quantityPerBox = quantityPerBox;
	}

	public int getReorderPoint() {
		return reorderPoint;
	}

	public void setReorderPoint(int reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status ;
	}

	public Item getItem() {

		return item;
	}

	public void setItemToInventory(Item item) {

		this.item = item;
	}


}
