package com.inventory.system.InventorySystem.controllers.inventory.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.services.InventoryService;
import com.inventory.system.InventorySystem.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* inventory detail Controller*/
@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/inventory")
    public InventoryDetail addInventory(@RequestBody InventoryDetail inventoryDetail) {
        return inventoryService.addInventory(inventoryDetail);
    }

    @GetMapping("/inventory")
    public List<InventoryDetail> getInventory() {
        return inventoryService.getInventory();
    }


    @GetMapping("/inventory/{inventoryId}")
    public InventoryDetail getInventoryById(@PathVariable int inventoryId) {
        return inventoryService.getInventoryById(inventoryId);
    }

    @PutMapping("/inventory/{inventoryId}")
    public InventoryDetail setItemQuantityInAllWarehouses(@RequestBody InventoryDetail inventoryDetail, @PathVariable int inventoryId) {
        return inventoryService.setItemQuantityInAllWarehouses(inventoryDetail, inventoryId);
    }


    @DeleteMapping("/inventory/{inventoryId}")
    public ResponseEntity<?> deleteinventoryById(@PathVariable int inventoryId) {
        inventoryService.deleteInventory(inventoryId);
        return new ResponseEntity<>((new ApiResponse("Inventory Deleted", inventoryId)), HttpStatus.FOUND);
    }
}
