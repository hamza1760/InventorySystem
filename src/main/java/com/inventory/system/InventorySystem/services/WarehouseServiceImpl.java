package com.inventory.system.InventorySystem.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.inventory.system.InventorySystem.dao.AddressDao;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.WarehouseAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.AddressNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.InventoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import com.inventory.system.InventorySystem.dao.WarehouseDao;

import com.inventory.system.InventorySystem.exceptions.notfound.ItemNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.WarehouseNotFoundException;

@Service
public class WarehouseServiceImpl implements WarehouseService {
	
	@Autowired
	private WarehouseDao warehouseDao;

	@Autowired
	private AddressDao addressDao;

	@Autowired
	private InventoryDetailDao inventoryDetailDao;



	@Override
	public Warehouse addWarehouse(Warehouse warehouse, int addressId) {
		Address address = addressDao.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));

		int warehouseId = warehouse.getWarehouseId();
		boolean checkWarehouseId = warehouseDao.findById(warehouseId).isPresent();
		if (checkWarehouseId == true) {

			throw new WarehouseAlreadyExists(warehouseId);
		}
		else{
			return warehouseDao.save(warehouse);

		}

	}


	@Override
	public Warehouse saveWarehouse(Warehouse warehouse) {
		return warehouseDao.save(warehouse);
	}
	





	@Override
	public List<Warehouse> getWarehouse() {

		return warehouseDao.findByStatus("active");
	}

	@Override
	public Warehouse getWarehouseById(int warehouseId) {
		 warehouseDao.findById(warehouseId).orElseThrow(()-> new WarehouseNotFoundException(warehouseId));
		return warehouseDao.findByStatusAndWarehouseId("active",warehouseId);
	}

	@Override
	public Warehouse updateWarehouse(Warehouse warehouse, int warehouseId) {

		return null;
	}


	@Override
	public Warehouse setItemQuantityInSingleWarehouse(InventoryDetail inventory, int warehouseId, int inventoryId) {
		Warehouse warehouse=warehouseDao.findById(warehouseId).orElseThrow(()-> new WarehouseNotFoundException(warehouseId));
		if(warehouse.getStatus().equals("deleted")){
			return null;
		}
		else {
			InventoryDetail updateInventory = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> new InventoryNotFoundException(inventoryId));
			Set<InventoryDetail> inventoryDetails = warehouse.getInventory();
			for (InventoryDetail setItemQuantity : inventoryDetails) {
				if (setItemQuantity.getInventoryId() == inventoryId) {

					setItemQuantity.setInStock(inventory.getInStock());
					setItemQuantity.setAvlQty(inventory.getAvlQty());
					warehouse.setInventory(setItemQuantity);
					inventoryDetailDao.save(setItemQuantity);
					return warehouseDao.save(warehouse);


				} else {
					throw new InventoryNotFoundException(inventoryId);

				}


			}
			return null;
		}

	}

	@Override
	public List<ItemQuantity> getItemQuantityInSingleWarehouse(int warehouseId) {
		Warehouse warehouse= warehouseDao.findById(warehouseId).orElseThrow(()-> new WarehouseNotFoundException(warehouseId));
		if(warehouse.getStatus().contains("deleted")){
			return null;
		}
		else {
			return warehouseDao.getItemQuantityInSingleWarehouse(warehouseId);
		}
	}


	@Override
	public void deleteWarehouse(int warehouseId) {
		 Warehouse warehouse= warehouseDao.findById(warehouseId).orElseThrow(()->new WarehouseNotFoundException(warehouseId));
		Set<InventoryDetail> inventoryDetailSet=  warehouse.getInventory();
		for(InventoryDetail inventory : inventoryDetailSet) {
			inventory.setStatus("deleted");
			inventoryDetailDao.save(inventory);
		}
		warehouseDao.softDelete(warehouseId);
		
		
	}









	
	

}
