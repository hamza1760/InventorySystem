package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dto.*;

import java.util.*;

public interface WarehouseService {

    WarehouseDto addWarehouse(WarehouseDto warehouse);

    List<WarehouseDto> getWarehouse();

    WarehouseDto getWarehouseById(int warehouseId);

    WarehouseDto putInventoryInWarehouse(Set<InventoryDetailDto> inventoryDetails, int warehouseId);

    List<ItemQuantityDto> getItemQuantityInAllWarehouses();

    List<ItemQuantityDto> getItemQuantityInSingleWarehouse(int warehouseId);

    WarehouseDto setItemQuantityInSingleWarehouse(InventoryDetailDto inventory, int warehouseId);

    void deleteWarehouseById(int warehouseId);
}
