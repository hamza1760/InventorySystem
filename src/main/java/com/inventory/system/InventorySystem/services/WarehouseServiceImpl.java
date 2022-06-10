package com.inventory.system.InventorySystem.services;

import java.util.List;



import com.inventory.system.InventorySystem.exceptions.alreadyexists.WarehouseAlreadyExists;
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
	
	
	
	
	

	@Override
	public Warehouse addWarehouse(Warehouse warehouse) {
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

	@Override
	public  void getItemFromWarehouse(Warehouse warehouse,int warehouseId,int itemId) {
		
		 warehouse = warehouseDao.findById(warehouseId).orElseThrow(()-> new WarehouseNotFoundException(warehouseId));
		 Warehouse item = warehouseDao.findById(itemId).orElseThrow(()->new ItemNotFoundException(itemId));
		 warehouseDao.delete(item);
		
		
	}
	
	

}
