package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.Warehouse;

import java.util.List;

public interface WarehouseService {

    Warehouse addWarehouse(Warehouse warehouse);

    List<Warehouse> getWarehouse();

    Warehouse getWarehouseById(int warehouseId);

    Warehouse updateWarehouse(Warehouse warehouse, int warehouseId);

    Warehouse putInventoryInWarehouse(int warehouseId, int inventoryId);

    Warehouse setItemQuantityInSingleWarehouse(InventoryDetail inventory, int warehouseId, int inventoryId);

    List<ItemQuantity> getItemQuantityInSingleWarehouse(int warehouseId);

    List<ItemQuantity> getItemQuantityInAllWarehouse();

    void deleteWarehouseById(int warehouseId);
}
