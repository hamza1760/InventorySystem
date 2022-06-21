package com.inventory.system.InventorySystem.controllers.warehouse.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponseItem;
import com.inventory.system.InventorySystem.api.response.ApiResponseWarehouse;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.exceptions.DataIntegrityException;
import com.inventory.system.InventorySystem.exceptions.notfound.AddressNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.InventoryNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.WarehouseNotFoundException;
import com.inventory.system.InventorySystem.pojo.ItemDto;
import com.inventory.system.InventorySystem.services.AddressService;
import com.inventory.system.InventorySystem.services.InventoryService;
import com.inventory.system.InventorySystem.services.ItemService;
import com.inventory.system.InventorySystem.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Warehouse getWarehouseById(@PathVariable int warehouseId){
        return warehouseService.getWarehouseById(warehouseId);
    }

    @GetMapping("/itemsinwarehouse/{warehouseId}")
    public List<ItemQuantity> getItemQuantityInSingleWarehouse(@PathVariable int warehouseId){
       List<ItemQuantity> itemQuantity =warehouseService.getItemQuantityInSingleWarehouse(warehouseId);
       if(itemQuantity==null){
           throw new WarehouseNotFoundException(warehouseId);
       }
       else {
           return warehouseService.getItemQuantityInSingleWarehouse(warehouseId);
       }
    }

    @GetMapping("/itemsinwarehouse/")
    public List<ItemQuantity> getItemQuantityInAllWarehouse(){
        return warehouseService.getItemQuantityInAllWarehouse();
    }


    @DeleteMapping("/warehouse/{warehouseId}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable int warehouseId) {

        warehouseService.deleteWarehouse(warehouseId);
        return new ResponseEntity<>(new ApiResponseWarehouse("warehouse deleted succesfully", warehouseId),
                HttpStatus.FOUND);
    }

    @PutMapping("warehouse/{warehouseId}/inventory/{inventoryId}")
    public Warehouse putInventoryInWarehouse(@PathVariable int warehouseId, @PathVariable int inventoryId){

        return warehouseService.putInventoryInWarehouse(warehouseId,inventoryId);
    }

    @PutMapping("inventory/{inventoryId}/warehouse/{warehouseId}")
    public Warehouse setItemQuantityInSingleWarehouse(@RequestBody InventoryDetail inventoryDetail, @PathVariable int inventoryId,@PathVariable int warehouseId){
       if(inventoryDetail==null){
           throw new InventoryNotFoundException(inventoryId);
       }
       else {
           Warehouse updatedItemQuantity = warehouseService.setItemQuantityInSingleWarehouse(inventoryDetail, warehouseId, inventoryId);
           if (updatedItemQuantity == null) {
               throw new WarehouseNotFoundException(warehouseId);
           } else {
               return updatedItemQuantity;
           }
       }

    }


}
