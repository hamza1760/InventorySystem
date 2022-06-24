package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.Address;

import java.util.List;


public interface AddressService {
	
public List<Address> getAddress();
	
	public Address getAddressById(int addressId);
	
	public Address addAddress(Address address,int cityId);
	
	public void deleteAddress(int addressId);

}
