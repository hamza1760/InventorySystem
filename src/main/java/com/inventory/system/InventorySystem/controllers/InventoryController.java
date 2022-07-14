package com.inventory.system.InventorySystem.controllers;

import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.services.InventoryService;
import com.inventory.system.InventorySystem.services.ItemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/* inventory detail Controller*/
@RestController
public class InventoryController {

    static Logger logger = Logger.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/inventory")
    public InventoryDetail addInventory(@Valid @RequestBody InventoryDetail inventoryDetail) {
        logger.info("Calling addInventory method from controller");
        return inventoryService.addInventory(inventoryDetail);
    }

    @GetMapping("/inventory")
    public List<InventoryDetail> getInventory() {
        logger.info("Calling getInventory method from controller");
        return inventoryService.getInventory();
    }

    @GetMapping("/inventory/{inventoryId}")
    public InventoryDetail getInventoryById(@PathVariable int inventoryId) {
        logger.info("Calling getInventoryById method from controller");
        return inventoryService.getInventoryById(inventoryId);
    }

    @PutMapping("/inventory/{inventoryId}")
    public InventoryDetail setItemQuantityInAllWarehouses(@RequestBody InventoryDetail inventoryDetail, @PathVariable int inventoryId) {
        logger.info("Calling setItemQuantityInAllWarehouses method from controller");
        return inventoryService.setItemQuantityInAllWarehouses(inventoryDetail, inventoryId);
    }

    @DeleteMapping("/inventory/{inventoryId}")
    public ResponseEntity<?> deleteInventoryById(@PathVariable int inventoryId) {
        logger.info("Calling deleteInventoryById method from controller");
        inventoryService.deleteInventoryById(inventoryId);
        return new ResponseEntity<>((new ApiResponse("Inventory Deleted", inventoryId)), HttpStatus.FOUND);
    }
}
