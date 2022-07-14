package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dto.ItemQuantityDto;
import com.inventory.system.InventorySystem.dto.WarehouseDto;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.Warehouse;

import java.util.List;
import java.util.Set;

public interface WarehouseService {

    WarehouseDto addWarehouse(Warehouse warehouse);

    List<WarehouseDto> getWarehouse();

    WarehouseDto getWarehouseById(Warehouse getWarehouse);

    WarehouseDto putInventoryInWarehouse(Set<InventoryDetail> inventoryDetails, int warehouseId);

    WarehouseDto setItemQuantityInSingleWarehouse(InventoryDetail inventory, int warehouseId, int inventoryId);

    List<ItemQuantityDto> getItemQuantityInSingleWarehouse(int warehouseId);

    List<ItemQuantityDto> getItemQuantityInAllWarehouses();

    void deleteWarehouseById(int warehouseId);

    WarehouseDto warehouseToWarehouseDto(Warehouse warehouse);

    ItemQuantityDto ItemQuantityToItemQuantityDto(ItemQuantity itemQuantity);
}
