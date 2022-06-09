package com.inventory.system.InventorySystem.services;

import java.util.List;



import com.inventory.system.InventorySystem.entities.Warehouse;

public interface WarehouseService {
	
	public Warehouse addWarehouse(Warehouse warehouse);
	public List<Warehouse> getWarehouse();
	public Warehouse getWarehouseById(int warehouseId);
	public Warehouse updateWarehouse(Warehouse warehouse,int warehouseId);
	public void deleteWarehouse(int warehouseId);
	
	public void getItemFromWarehouse(Warehouse warehouse,int warehouseId,int itemId);
	
	

}
