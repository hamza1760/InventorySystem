package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dto.*;

import java.util.*;

public interface WarehouseService {

    WarehouseDto addWarehouse(WarehouseDto warehouse);

    List<WarehouseDto> getWarehouse();

    WarehouseDto getWarehouseById(WarehouseDto getWarehouse);

    WarehouseDto putInventoryInWarehouse(Set<InventoryDetailDto> inventoryDetails, int warehouseId);

    WarehouseDto setItemQuantityInSingleWarehouse(InventoryDetailDto inventory, int warehouseId);

    List<ItemQuantityDto> getItemQuantityInSingleWarehouse(int warehouseId);

    List<ItemQuantityDto> getItemQuantityInAllWarehouses();

    void deleteWarehouseById(int warehouseId);
}
