package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dto.*;

import java.util.*;

public interface InventoryService {

    InventoryDetailDto addInventory(InventoryDetailDto inventoryDetailDto);

    List<InventoryDetailDto> getInventory();

    InventoryDetailDto getInventoryById(int inventoryId);

    InventoryDetailDto setItemQuantityInAllWarehouses(InventoryDetailDto inventoryDetailDto,int inventoryId);

    void deleteInventoryById(int inventoryId);
}
