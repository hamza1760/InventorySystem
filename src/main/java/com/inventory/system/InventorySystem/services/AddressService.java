package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.Address;


public interface AddressService {
	
public List<Address> getAddress();
	
	public Address getAddressById();
	
	public Address addAddress(Address address);
	
	public void deleteAddress();

}
