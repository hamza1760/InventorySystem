package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.InventoryAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryDetailDao inventoryDetailDao;

	@Override
	public List<InventoryDetail> getInventory() {
		return inventoryDetailDao.findAll();
	}

	@Override
	public InventoryDetail getInventoryById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InventoryDetail addInventory(InventoryDetail inventoryDetail) {
		int inventoryId= inventoryDetail.getInventoryId();
		boolean checkId = inventoryDetailDao.findById(inventoryId).isPresent();
		if(checkId==true){
			throw new InventoryAlreadyExists(inventoryId);
		}
		else {
			return inventoryDetailDao.save(inventoryDetail);
		}
	}

	@Override
	public InventoryDetail updateInventoryById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInventory() {
		// TODO Auto-generated method stub
		
	}

	

}
