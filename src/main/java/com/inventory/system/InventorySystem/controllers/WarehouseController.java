package com.inventory.system.InventorySystem.controllers;

import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.dto.ItemQuantityDto;
import com.inventory.system.InventorySystem.dto.WarehouseDto;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.services.InventoryService;
import com.inventory.system.InventorySystem.services.WarehouseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

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
    public WarehouseDto addWarehouse(@Valid @RequestBody Warehouse warehouse) {
        logger.info("Calling addWarehouse function");
        return warehouseService.addWarehouse(warehouse);
    }

    @GetMapping("/warehouse")
    public List<WarehouseDto> getWarehouse() {
        logger.info("Calling getWarehouse method from controller");
        return warehouseService.getWarehouse();
    }

    @GetMapping("/warehouseById")
    public WarehouseDto getWarehouseById(@RequestBody Warehouse getWarehouse) {
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
    public WarehouseDto putInventoryInWarehouse(@RequestBody Set<InventoryDetail> inventoryDetails, @PathVariable int warehouseId) {
        logger.info("Calling putInventoryInWarehouse method from controller");
        return warehouseService.putInventoryInWarehouse(inventoryDetails, warehouseId);
    }

    @PutMapping("inventory/{inventoryId}/warehouse/{warehouseId}")
    public WarehouseDto setItemQuantityInSingleWarehouse(@RequestBody InventoryDetail inventoryDetail, @PathVariable int inventoryId, @PathVariable int warehouseId) {
        logger.info("Calling setItemQuantityInSingleWarehouse method from controller");
        return warehouseService.setItemQuantityInSingleWarehouse(inventoryDetail, warehouseId, inventoryId);
    }
}
