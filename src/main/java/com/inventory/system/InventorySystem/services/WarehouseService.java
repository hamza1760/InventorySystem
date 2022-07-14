package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.Warehouse;

import java.util.List;
import java.util.Set;

public interface WarehouseService {

    Warehouse addWarehouse(Warehouse warehouse);

    List<Warehouse> getWarehouse();

    Warehouse getWarehouseById(Warehouse getWarehouse);

    Warehouse updateWarehouse(Warehouse warehouse, int warehouseId);

    Warehouse putInventoryInWarehouse(Set<InventoryDetail> inventoryDetails, int warehouseId);

    Warehouse setItemQuantityInSingleWarehouse(InventoryDetail inventory, int warehouseId, int inventoryId);

    List<ItemQuantity> getItemQuantityInSingleWarehouse(int warehouseId);

    List<ItemQuantity> getItemQuantityInAllWarehouses();

    void deleteWarehouseById(int warehouseId);
}
