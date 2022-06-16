package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.entities.WarehouseAddress;

public interface WarehouseService {
	
	public Warehouse addWarehouse(Warehouse warehouse,int addressId);
	public Warehouse saveWarehouse(Warehouse warehouse);
	public List<Warehouse> getWarehouse();
	public Warehouse getWarehouseById(int warehouseId);
	public Warehouse updateWarehouse(Warehouse warehouse,int warehouseId);
	public Warehouse setItemQuantityInSingleWarehouse(InventoryDetail inventory, int warehouseId, int inventoryId);
	public void deleteWarehouse(int warehouseId);


	public List<WarehouseAddress> getWarehouseAddress(int countryId, int cityId, int addressId);

}
