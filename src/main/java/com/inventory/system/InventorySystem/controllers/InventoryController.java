package com.inventory.system.InventorySystem.controllers;

import com.inventory.system.InventorySystem.apiresponse.*;
import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.services.*;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

/* inventory detail Controller*/
@RestController
public class InventoryController {

    static Logger logger = Logger.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/inventory")
    public InventoryDetailDto addInventory(@Valid @RequestBody InventoryDetailDto inventoryDetail) {
        logger.info("Calling addInventory method from controller");
        return inventoryService.addInventory(inventoryDetail);
    }

    @GetMapping("/inventory")
    public List<InventoryDetailDto> getInventory() {
        logger.info("Calling getInventory method from controller");
        return inventoryService.getInventory();
    }

    @GetMapping("/inventory/{inventoryId}")
    public InventoryDetailDto getInventoryById(@PathVariable int inventoryId) {
        logger.info("Calling getInventoryById method from controller");
        return inventoryService.getInventoryById(inventoryId);
    }

    @PutMapping("/inventory/edit")
    public InventoryDetailDto setItemQuantityInAllWarehouses(@RequestBody InventoryDetailDto inventoryDetail) {
        logger.info("Calling setItemQuantityInAllWarehouses method from controller");
        return inventoryService.setItemQuantityInAllWarehouses(inventoryDetail);
    }

    @DeleteMapping("/inventory/{inventoryId}")
    public ResponseEntity<?> deleteInventoryById(@PathVariable int inventoryId) {
        logger.info("Calling deleteInventoryById method from controller");
        inventoryService.deleteInventoryById(inventoryId);
        return new ResponseEntity<>((new ApiResponse("Inventory Deleted", inventoryId)), HttpStatus.FOUND);
    }
}
