package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.Address;

import java.util.List;


public interface AddressService {

    List<Address> getAddress();

    Address getAddressById(int addressId);

    Address addAddress(Address address, int cityId);

    void deleteAddress(int addressId);
}
