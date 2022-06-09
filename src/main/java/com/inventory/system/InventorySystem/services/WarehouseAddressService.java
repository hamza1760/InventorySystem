package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.WarehouseAddress;


public interface WarehouseAddressService {
	
public List<WarehouseAddress> getWarehouseAddress();
	
	public WarehouseAddress getWarehouseAddressById();
	
	public WarehouseAddress addWarehouseAddress(WarehouseAddress warehouseAddress);
	
	public void deleteWarehouseAddress();

}
