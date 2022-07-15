package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dto.InventoryDetailDto;
import com.inventory.system.InventorySystem.entities.InventoryDetail;

import java.util.List;

public interface InventoryService {

    List<InventoryDetailDto> getInventory();

    InventoryDetailDto getInventoryById(int inventoryId);

    InventoryDetailDto addInventory(InventoryDetail inventoryDetail);

    InventoryDetailDto setItemQuantityInAllWarehouses(InventoryDetailDto inventoryDetail);

    void deleteInventoryById(int inventoryId);

    InventoryDetailDto inventoryDetailToInventoryDetailDto(InventoryDetail inventoryDetail);
}
