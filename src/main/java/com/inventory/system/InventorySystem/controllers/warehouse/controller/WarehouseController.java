package com.inventory.system.InventorySystem.controllers.warehouse.controller;


import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.services.AddressService;
import com.inventory.system.InventorySystem.services.InventoryService;
import com.inventory.system.InventorySystem.services.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WarehouseController {

    static Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private InventoryService inventoryService;


    /* Warehouse Controller */
    @PostMapping("/warehouse/")
    public Warehouse addWarehouse(@Valid @RequestBody Warehouse warehouse) {
        logger.info("calling addWarehouse function");
        return warehouseService.addWarehouse(warehouse);
    }

    @GetMapping("/warehouse")
    public List<Warehouse> getWarehouse() {
        logger.info("calling getWarehouse function");
        return warehouseService.getWarehouse();
    }

    @GetMapping("/warehouse/{warehouseId}")
    public Warehouse getWarehouseById(@PathVariable int warehouseId) {
        logger.info("calling getWarehouseById function with warehouseId: " + warehouseId);
        return warehouseService.getWarehouseById(warehouseId);
    }


    @GetMapping("/itemsinwarehouse/{warehouseId}")
    public List<ItemQuantity> getItemQuantityInSingleWarehouse(@PathVariable int warehouseId) {
        logger.info("calling getItemQuantityInSingleWarehouse function with warehouseId: " + warehouseId);
        return warehouseService.getItemQuantityInSingleWarehouse(warehouseId);
    }


    @GetMapping("/itemsinwarehouse/")
    public List<ItemQuantity> getItemQuantityInAllWarehouse() {
        logger.info("calling getItemQuantityInAllWarehouse function");
        return warehouseService.getItemQuantityInAllWarehouse();
    }

    @PutMapping("/warehouse/{warehouseId}")
    public Warehouse updateWarehouse(@RequestBody Warehouse warehouse, @PathVariable int warehouseId) {
        logger.info("calling updateWarehouse function");
        return warehouseService.updateWarehouse(warehouse, warehouseId);
    }


    @DeleteMapping("/warehouse/{warehouseId}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable int warehouseId) {
        logger.info("calling deleteWarehouse function");
        warehouseService.deleteWarehouse(warehouseId);
        return new ResponseEntity<>(new ApiResponse("warehouse deleted succesfully", warehouseId),
                HttpStatus.FOUND);
    }

    @PutMapping("warehouse/{warehouseId}/inventory/{inventoryId}")
    public Warehouse putInventoryInWarehouse(@PathVariable int warehouseId, @PathVariable int inventoryId) {
        logger.info("calling putInventoryInWarehouse function");
        return warehouseService.putInventoryInWarehouse(warehouseId, inventoryId);
    }

    @PutMapping("inventory/{inventoryId}/warehouse/{warehouseId}")
    public Warehouse setItemQuantityInSingleWarehouse(@RequestBody InventoryDetail inventoryDetail, @PathVariable int inventoryId, @PathVariable int warehouseId) {
        return warehouseService.setItemQuantityInSingleWarehouse(inventoryDetail, warehouseId, inventoryId);
    }
}
