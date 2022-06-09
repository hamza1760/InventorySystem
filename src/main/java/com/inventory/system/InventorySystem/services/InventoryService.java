package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.InventoryDetail;




public interface InventoryService {
	
public List<InventoryDetail> getInventory();
	
	public InventoryDetail getInventoryById();
	
	public InventoryDetail addInventory(InventoryDetail inventoryDetail);
	
	public InventoryDetail updateInventoryById();
	
	public void deleteInventory();
	
	
	

}
