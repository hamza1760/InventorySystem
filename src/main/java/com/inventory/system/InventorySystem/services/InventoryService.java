package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dto.*;

import java.util.*;

public interface InventoryService {

    List<InventoryDetailDto> getInventory();

    InventoryDetailDto getInventoryById(int inventoryId);

    InventoryDetailDto addInventory(InventoryDetailDto inventoryDetail);

    InventoryDetailDto setItemQuantityInAllWarehouses(InventoryDetailDto inventoryDetail);

    void deleteInventoryById(int inventoryId);
}
