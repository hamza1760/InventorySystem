package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.dao.WarehouseAddressDao;
import com.inventory.system.InventorySystem.entities.WarehouseAddress;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ItemAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.WarehouseAddressAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseAddressServiceImpl implements WarehouseAddressService{

	@Autowired
	private WarehouseAddressDao warehouseAddressDao;

	@Override
	public List<WarehouseAddress> getWarehouseAddress() {
		return warehouseAddressDao.findAll();
	}

	@Override
	public WarehouseAddress getWarehouseAddressById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WarehouseAddress addWarehouseAddress(WarehouseAddress warehouseAddress) {

		long postalCode= warehouseAddress.getPostalCode();
		boolean checkPostalCode = warehouseAddressDao.findById(postalCode).isPresent();
		if(checkPostalCode==true){
			throw new WarehouseAddressAlreadyExists(postalCode);
		}
		else {
			return warehouseAddressDao.save(warehouseAddress);
		}
	}

	@Override
	public void deleteWarehouseAddress() {
		// TODO Auto-generated method stub
		
	}

	

}
