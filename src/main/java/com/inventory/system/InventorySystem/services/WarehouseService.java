package com.inventory.system.InventorySystem.services;

import java.util.HashSet;
import java.util.List;


import com.inventory.system.InventorySystem.entities.*;

public interface WarehouseService {
	
	public Warehouse addWarehouse(Warehouse warehouse);
	public Warehouse saveWarehouse(Warehouse warehouse);
	public List<Warehouse> getWarehouse();
	public Warehouse getWarehouseById(int warehouseId);
	public Warehouse updateWarehouse(Warehouse warehouse,int warehouseId);

	public Warehouse putInventoryInWarehouse(int warehouseId,int inventoryId);

	public Warehouse setItemQuantityInSingleWarehouse(InventoryDetail inventory, int warehouseId, int inventoryId);
	public List<ItemQuantity> getItemQuantityInSingleWarehouse(int warehouseId);
	public List<ItemQuantity> getItemQuantityInAllWarehouse();

	public void deleteWarehouse(int warehouseId);



}
