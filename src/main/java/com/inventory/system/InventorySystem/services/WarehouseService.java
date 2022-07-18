package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.entities.*;

import java.util.*;

public interface WarehouseService {

    WarehouseDto addWarehouse(WarehouseDto warehouse);

    List<WarehouseDto> getWarehouse();

    WarehouseDto getWarehouseById(Warehouse getWarehouse);

    WarehouseDto putInventoryInWarehouse(Set<InventoryDetailDto> inventoryDetails, int warehouseId);

    WarehouseDto setItemQuantityInSingleWarehouse(InventoryDetailDto inventory, int warehouseId, int inventoryId);

    List<ItemQuantityDto> getItemQuantityInSingleWarehouse(int warehouseId);

    List<ItemQuantityDto> getItemQuantityInAllWarehouses();

    void deleteWarehouseById(int warehouseId);
}
