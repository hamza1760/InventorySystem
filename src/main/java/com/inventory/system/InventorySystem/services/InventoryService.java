package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.InventoryDetail;



public interface InventoryService {
	
public List<InventoryDetail> getInventory();
	
	public InventoryDetail getInventoryById(int inventoryId);
	
	public InventoryDetail addInventory(InventoryDetail inventoryDetail,int itemId);

	public InventoryDetail saveInventory(InventoryDetail inventoryDetail);
	
	public InventoryDetail updateInventoryById();
	
	public void deleteInventory(int inventoryId);
	
	
	

}
