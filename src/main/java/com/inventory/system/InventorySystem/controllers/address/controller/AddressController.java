package com.inventory.system.InventorySystem.controllers.address.controller;

import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.services.AddressService;
import com.inventory.system.InventorySystem.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* warehouse address Controller*/
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/address")
    public Address addWaddress(@RequestBody Address address){

        return addressService.addAddress(address);
    }

    @GetMapping("/address")
    public List<Address> getWarehouseAddress(){

        return addressService.getAddress();
    }

    @GetMapping("/address/{addressId}")
    public Address getAddressById(@PathVariable int addressId){
        return addressService.getAddressById(addressId);
    }
}
