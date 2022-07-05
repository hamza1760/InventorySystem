package com.inventory.system.InventorySystem.controllers.warehouse.controller;


import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.services.AddressService;
import com.inventory.system.InventorySystem.services.InventoryService;
import com.inventory.system.InventorySystem.services.WarehouseService;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WarehouseController {

    static Logger logger = Logger.getLogger(WarehouseController.class);

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private InventoryService inventoryService;


    /* Warehouse Controller */
    @PostMapping("/warehouse/")
    public Warehouse addWarehouse(@Valid @RequestBody Warehouse warehouse) {
        logger.info("Calling addWarehouse function");
        return warehouseService.addWarehouse(warehouse);
    }

    @GetMapping("/warehouse")
    public List<Warehouse> getWarehouse() {
        logger.info("Calling getWarehouse method from controller");
        return warehouseService.getWarehouse();
    }

    @GetMapping("/warehouse/{warehouseId}")
    public Warehouse getWarehouseById(@PathVariable int warehouseId) {
        logger.info("Calling getWarehouseById method from controller");
        return warehouseService.getWarehouseById(warehouseId);
    }


    @GetMapping("/itemsinwarehouse/{warehouseId}")
    public List<ItemQuantity> getItemQuantityInSingleWarehouse(@PathVariable int warehouseId) {
        logger.info("Calling getItemQuantityInSingleWarehouse method from controller");
        return warehouseService.getItemQuantityInSingleWarehouse(warehouseId);
    }


    @GetMapping("/itemsinwarehouse/")
    public List<ItemQuantity> getItemQuantityInAllWarehouses() {
        logger.info("Calling getItemQuantityInAllWarehouses method from controller");
        return warehouseService.getItemQuantityInAllWarehouses();
    }

    @PutMapping("/warehouse/{warehouseId}")
    public Warehouse updateWarehouse(@RequestBody Warehouse warehouse, @PathVariable int warehouseId) {
        logger.info("Calling updateWarehouse method from controller");
        return warehouseService.updateWarehouse(warehouse, warehouseId);
    }


    @DeleteMapping("/warehouse/{warehouseId}")
    public ResponseEntity<?> deleteWarehouseById(@PathVariable int warehouseId) {
        logger.info("Calling deleteWarehouse method from controller");
        warehouseService.deleteWarehouseById(warehouseId);
        return new ResponseEntity<>(new ApiResponse("warehouse deleted succesfully", warehouseId),
                HttpStatus.FOUND);
    }

    @PutMapping("warehouse/{warehouseId}/inventory/{inventoryId}")
    public Warehouse putInventoryInWarehouse(@PathVariable int warehouseId, @PathVariable int inventoryId) {
        logger.info("Calling putInventoryInWarehouse method from controller");
        return warehouseService.putInventoryInWarehouse(warehouseId, inventoryId);
    }

    @PutMapping("inventory/{inventoryId}/warehouse/{warehouseId}")
    public Warehouse setItemQuantityInSingleWarehouse(@RequestBody InventoryDetail inventoryDetail, @PathVariable int inventoryId, @PathVariable int warehouseId) {
        logger.info("Calling setItemQuantityInSingleWarehouse method from controller");
        return warehouseService.setItemQuantityInSingleWarehouse(inventoryDetail, warehouseId, inventoryId);
    }
}
