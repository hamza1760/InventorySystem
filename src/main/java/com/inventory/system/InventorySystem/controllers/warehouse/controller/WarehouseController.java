package com.inventory.system.InventorySystem.controllers.warehouse.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponseItem;
import com.inventory.system.InventorySystem.api.response.ApiResponseWarehouse;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.pojo.ItemDto;
import com.inventory.system.InventorySystem.services.AddressService;
import com.inventory.system.InventorySystem.services.InventoryService;
import com.inventory.system.InventorySystem.services.ItemService;
import com.inventory.system.InventorySystem.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
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
    @PostMapping("/warehouse/address/{addressId}")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse, @PathVariable int addressId) {

        /*getting address*/
        Address address = addressService.getAddressById(addressId);

        /*checking if address is already assigned to warehouse*/
        List<Warehouse> warehouses = warehouseService.getWarehouse();
        for(Warehouse singleWarehouse:  warehouses){
            Address checkAddress= singleWarehouse.getAddress();
            int addressIdInWarehouse = checkAddress.getAddressId();
            if(addressIdInWarehouse!=addressId) {
                break;
            }
            else {
                throw new DataIntegrityViolationException("address is already assigned to warehouse");
            }
        }

        /*if address is not assigned to warehouse than mapping address to the warehouse*/
        warehouse.setAddress(address);
        warehouseService.addWarehouse(warehouse,addressId);

        return warehouse;

    }

    @GetMapping("/warehouse")
    public List<Warehouse> getWarehouse() {

        return warehouseService.getWarehouse();

    }

    @GetMapping("/warehouse/{warehouseId}")
    public Warehouse getWarehouseById(@PathVariable int warehouseId){
        return warehouseService.getWarehouseById(warehouseId);
    }

    @GetMapping("/country/{countryId}/city/{cityId}/address/{addressId}")
    public List<WarehouseAddress> getCountryById(@PathVariable int countryId, @PathVariable int cityId, @PathVariable int addressId) {
        return warehouseService.getWarehouseAddress(countryId,cityId,addressId);

    }

    @GetMapping("/itemsinwarehouse/{warehouseId}")
    public List<ItemQuantity> getItemQuantityInSingleWarehouse(@PathVariable int warehouseId){
        return warehouseService.getItemQuantityInSingleWarehouse(warehouseId);
    }


    @DeleteMapping("/warehouse/{warehouseId}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable int warehouseId) {

        warehouseService.deleteWarehouse(warehouseId);
        return new ResponseEntity<>(new ApiResponseWarehouse("warehouse deleted succesfully", warehouseId),
                HttpStatus.FOUND);
    }

    @PutMapping("warehouse/{warehouseId}/inventory/{inventoryId}")
    public Warehouse putInventoryInWarehouse(@PathVariable int warehouseId, @PathVariable int inventoryId){

        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);

        InventoryDetail inventory = inventoryService.getInventoryById(inventoryId);

        inventory.setWarehouse(warehouse);
        inventoryService.saveInventory(inventory);

        return warehouse;
    }

    @PutMapping("inventory/{inventoryId}/warehouse/{warehouseId}")
    public Warehouse setItemQuantityInSingleWarehouse(@RequestBody InventoryDetail inventoryDetail, @PathVariable int inventoryId,@PathVariable int warehouseId){
       Warehouse updatedItemQuantity = warehouseService.setItemQuantityInSingleWarehouse(inventoryDetail,warehouseId,inventoryId);
        return updatedItemQuantity;
    }


}
