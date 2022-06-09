package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.InventoryDetail;



public interface InventoryService {
	
public List<InventoryDetail> getInventory();
	
	public InventoryDetail getInventoryById(int inventoryId);
	
	public InventoryDetail addInventory(int inventoryId,InventoryDetail inventoryDetail,int itemId);
	
	public InventoryDetail updateInventoryById();
	
	public void deleteInventory();
	
	
	

}
