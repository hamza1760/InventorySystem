package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.dao.AddressDao;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ItemAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AddressAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressDao addressDao;

	@Override
	public List<Address> getAddress() {
		return addressDao.findAll();
	}

	@Override
	public Address getAddressById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address addAddress(Address address) {

		int addressId = address.getAddressId();
		boolean checkPostalCode = addressDao.findById(addressId).isPresent();
		if(checkPostalCode==true){
			throw new AddressAlreadyExists(addressId);
		}
		else {
			return addressDao.save(address);
		}
	}

	@Override
	public void deleteAddress() {
		// TODO Auto-generated method stub
		
	}

	

}
