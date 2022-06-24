package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.InventoryDetail;

import java.util.List;


public interface InventoryService {

    List<InventoryDetail> getInventory();

    InventoryDetail getInventoryById(int inventoryId);

    InventoryDetail addInventory(InventoryDetail inventoryDetail);

    InventoryDetail setItemQuantityInAllWarehouses(InventoryDetail inventoryDetail, int inventoryId);

    InventoryDetail updateInventoryById();

    void deleteInventory(int inventoryId);
}
