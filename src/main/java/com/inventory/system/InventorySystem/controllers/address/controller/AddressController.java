package com.inventory.system.InventorySystem.controllers.address.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponse;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.CityDetail;
import com.inventory.system.InventorySystem.services.AddressService;
import com.inventory.system.InventorySystem.services.CityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* warehouse address Controller*/
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CityDetailService cityDetailService;

    @PostMapping("/address/city/{cityId}")
    public Address addAddress(@RequestBody Address address, @PathVariable int cityId){

        /*mapping city to address*/
        CityDetail city = cityDetailService.getCityById(cityId);
        address.setCity(city);
        addressService.addAddress(address,cityId);

        return address;


    }

    @GetMapping("/address")
    public List<Address> getWarehouseAddress(){

        return addressService.getAddress();
    }

    @GetMapping("/address/{addressId}")
    public Address getAddressById(@PathVariable int addressId){
        return addressService.getAddressById(addressId);
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<?> deleteAddressById(@PathVariable int addressId){
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>((new ApiResponse("Address Deleted",addressId)),HttpStatus.FOUND);
    }
}


