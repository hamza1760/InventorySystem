package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.dao.AddressDao;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.WarehouseAddress;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.WarehouseAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.inventory.system.InventorySystem.dao.WarehouseDao;

import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.exceptions.notfound.ItemNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.WarehouseNotFoundException;

@Service
public class WarehouseServiceImpl implements WarehouseService {
	
	@Autowired
	private WarehouseDao warehouseDao;

	@Autowired
	private AddressDao addressDao;
	
	
	
	
	

	@Override
	public Warehouse addWarehouse(Warehouse warehouse, int addressId) {
		Address address = addressDao.findById(addressId).orElseThrow(()-> new AddressNotFoundException(addressId));

		int warehouseId = warehouse.getWarehouseId();
		boolean checkWarehouseId = warehouseDao.findById(warehouseId).isPresent();
		if(checkWarehouseId==true){
			throw new WarehouseAlreadyExists(warehouseId);
		}
		else{
			return warehouseDao.save(warehouse);
		}
	
	}

	@Override
	public List<Warehouse> getWarehouse() {

		return warehouseDao.findAll();
	}

	@Override
	public Warehouse getWarehouseById(int warehouseId) {
		Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(()-> new WarehouseNotFoundException(warehouseId));
		return warehouse;
	}

	@Override
	public Warehouse updateWarehouse(Warehouse warehouse, int warehouseId) {

		return null;
	}

	@Override
	public void deleteWarehouse(int warehouseId) {
		Warehouse warehouse = warehouseDao.findById(warehouseId).orElseThrow(()->new WarehouseNotFoundException(warehouseId));

		warehouseDao.delete(warehouse);
		
		
	}

	public List<WarehouseAddress> getWarehouseAddress(int countryId, int cityId, int addressId){
		return warehouseDao.getWarehouseAddress(countryId,cityId,addressId);
	}


	
	

}
