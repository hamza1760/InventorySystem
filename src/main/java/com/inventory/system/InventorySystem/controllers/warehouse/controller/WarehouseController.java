package com.inventory.system.InventorySystem.controllers.warehouse.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.services.AddressService;
import com.inventory.system.InventorySystem.services.InventoryService;
import com.inventory.system.InventorySystem.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private InventoryService inventoryService;


    /* Warehouse Controller */
    @PostMapping("/warehouse/")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.addWarehouse(warehouse);
    }

    @GetMapping("/warehouse")
    public List<Warehouse> getWarehouse() {
        return warehouseService.getWarehouse();
    }

    @GetMapping("/warehouse/{warehouseId}")
    public Warehouse getWarehouseById(@PathVariable int warehouseId) {
        return warehouseService.getWarehouseById(warehouseId);
    }


    @GetMapping("/itemsinwarehouse/{warehouseId}")
    public List<ItemQuantity> getItemQuantityInSingleWarehouse(@PathVariable int warehouseId) {
        return warehouseService.getItemQuantityInSingleWarehouse(warehouseId);
    }


    @GetMapping("/itemsinwarehouse/")
    public List<ItemQuantity> getItemQuantityInAllWarehouse() {
        return warehouseService.getItemQuantityInAllWarehouse();
    }

    @PutMapping("/warehouse/{warehouseId}")
    public Warehouse updateWarehouse(@RequestBody Warehouse warehouse, @PathVariable int warehouseId) {
        return warehouseService.updateWarehouse(warehouse, warehouseId);
    }


    @DeleteMapping("/warehouse/{warehouseId}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable int warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
        return new ResponseEntity<>(new ApiResponse("warehouse deleted succesfully", warehouseId),
                HttpStatus.FOUND);
    }

    @PutMapping("warehouse/{warehouseId}/inventory/{inventoryId}")
    public Warehouse putInventoryInWarehouse(@PathVariable int warehouseId, @PathVariable int inventoryId) {
        return warehouseService.putInventoryInWarehouse(warehouseId, inventoryId);
    }

    @PutMapping("inventory/{inventoryId}/warehouse/{warehouseId}")
    public Warehouse setItemQuantityInSingleWarehouse(@RequestBody InventoryDetail inventoryDetail, @PathVariable int inventoryId, @PathVariable int warehouseId) {
        return warehouseService.setItemQuantityInSingleWarehouse(inventoryDetail, warehouseId, inventoryId);
    }
}
