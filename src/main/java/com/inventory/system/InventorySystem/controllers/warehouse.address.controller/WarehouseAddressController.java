package com.inventory.system.InventorySystem.controllers.warehouse.address.controller;

import com.inventory.system.InventorySystem.entities.WarehouseAddress;
import com.inventory.system.InventorySystem.services.WarehouseAddressService;
import com.inventory.system.InventorySystem.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/* warehouse address Controller*/
@RestController
public class WarehouseAddressController {

    @Autowired
    private WarehouseAddressService warehouseAddressService;

    @PostMapping("/address")
    public WarehouseAddress addWarehouseAddress(@RequestBody WarehouseAddress warehouseAddress){

        return warehouseAddressService.addWarehouseAddress(warehouseAddress);
    }

    @GetMapping("/address")
    public List<WarehouseAddress> getWarehouseAddress(){
        return warehouseAddressService.getWarehouseAddress();
    }
}
