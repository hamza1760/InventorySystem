package com.inventory.system.InventorySystem.controllers;

import com.inventory.system.InventorySystem.api.response.*;
import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.services.*;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RestController
@CrossOrigin("*")
public class WarehouseController {

    static Logger logger = Logger.getLogger(WarehouseController.class);

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private InventoryService inventoryService;

    /* Warehouse Controller */
    @PostMapping("/warehouse")
    public WarehouseDto addWarehouse(@Valid @RequestBody WarehouseDto warehouse) {
        logger.info("Calling addWarehouse function");
        return warehouseService.addWarehouse(warehouse);
    }

    @GetMapping("/warehouse")
    public List<WarehouseDto> getWarehouse() {
        logger.info("Calling getWarehouse method from controller");
        return warehouseService.getWarehouse();
    }

    @GetMapping("/warehouseById")
    public WarehouseDto getWarehouseById(@RequestBody WarehouseDto getWarehouse) {
        logger.info("Calling getWarehouseById method from controller");
        return warehouseService.getWarehouseById(getWarehouse);
    }

    @GetMapping("/itemsinwarehouse/{warehouseId}")
    public List<ItemQuantityDto> getItemQuantityInSingleWarehouse(@PathVariable int warehouseId) {
        logger.info("Calling getItemQuantityInSingleWarehouse method from controller");
        return warehouseService.getItemQuantityInSingleWarehouse(warehouseId);
    }

    @GetMapping("/itemsinwarehouse/")
    public List<ItemQuantityDto> getItemQuantityInAllWarehouses() {
        logger.info("Calling getItemQuantityInAllWarehouses method from controller");
        return warehouseService.getItemQuantityInAllWarehouses();
    }

    @DeleteMapping("/warehouse/{warehouseId}")
    public ResponseEntity<?> deleteWarehouseById(@PathVariable int warehouseId) {
        logger.info("Calling deleteWarehouse method from controller");
        warehouseService.deleteWarehouseById(warehouseId);
        return new ResponseEntity<>(new ApiResponse("warehouse deleted succesfully", warehouseId),
                HttpStatus.FOUND);
    }

    @PutMapping("/inventoryinwarehouse/{warehouseId}")
    public WarehouseDto putInventoryInWarehouse(@RequestBody Set<InventoryDetailDto> inventoryDetails, @PathVariable int warehouseId) {
        logger.info("Calling putInventoryInWarehouse method from controller");
        return warehouseService.putInventoryInWarehouse(inventoryDetails, warehouseId);
    }

    @PutMapping("inventory/{inventoryId}/warehouse/{warehouseId}")
    public WarehouseDto setItemQuantityInSingleWarehouse(@RequestBody InventoryDetailDto inventoryDetail, @PathVariable int inventoryId, @PathVariable int warehouseId) {
        logger.info("Calling setItemQuantityInSingleWarehouse method from controller");
        return warehouseService.setItemQuantityInSingleWarehouse(inventoryDetail, warehouseId, inventoryId);
    }
}
