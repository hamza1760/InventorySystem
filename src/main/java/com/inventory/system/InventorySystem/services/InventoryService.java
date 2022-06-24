package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.InventoryDetail;

import java.util.List;


public interface InventoryService {
	
public List<InventoryDetail> getInventory();
	
	public InventoryDetail getInventoryById(int inventoryId);
	
	public InventoryDetail addInventory(InventoryDetail inventoryDetail);

	public InventoryDetail saveInventory(InventoryDetail inventoryDetail);

	public InventoryDetail setItemQuantityInAllWarehouses(InventoryDetail inventoryDetail,int inventoryId);
	
	public InventoryDetail updateInventoryById();
	
	public void deleteInventory(int inventoryId);
	
	
	

}
