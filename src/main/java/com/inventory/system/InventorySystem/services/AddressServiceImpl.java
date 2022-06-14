package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.dao.AddressDao;
import com.inventory.system.InventorySystem.dao.CityDetailDao;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.CityDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ItemAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AddressAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.AddressNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.CityNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.InventoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressDao addressDao;

	@Autowired
	private CityDetailDao cityDetailDao;

	@Override
	public List<Address> getAddress() {
		return addressDao.findAll();
	}

	@Override
	public Address getAddressById(int addressId) {
		Address address = addressDao.findById(addressId).orElseThrow(()->new AddressNotFoundException(addressId));
		return address;
	}

	@Override
	public Address addAddress(Address address,int cityId) {
		CityDetail cityDetail = cityDetailDao.findById(cityId).orElseThrow(()-> new CityNotFoundException(cityId));

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
	public void deleteAddress(int addressId){

		Address address = addressDao.findById(addressId).orElseThrow(()-> new AddressNotFoundException(addressId));
		addressDao.delete(address);
		
	}

	

}
