package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dto.*;

import java.util.*;

public interface InventoryService {

    InventoryDetailDto addInventory(InventoryDetailDto inventoryDetail);

    List<InventoryDetailDto> getInventory();

    InventoryDetailDto getInventoryById(int inventoryId);

    InventoryDetailDto setItemQuantityInAllWarehouses(InventoryDetailDto inventoryDetail);

    void deleteInventoryById(int inventoryId);
}
