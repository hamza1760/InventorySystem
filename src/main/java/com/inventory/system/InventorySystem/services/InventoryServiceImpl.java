package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.InventoryAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.InventoryNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryDetailDao inventoryDetailDao;

	@Autowired
	private ItemDao itemDao;

	@Override
	public List<InventoryDetail> getInventory() {
		return inventoryDetailDao.findAll();
	}

	@Override
	public InventoryDetail getInventoryById(int inventoryId) {

		InventoryDetail inventoryDetail = inventoryDetailDao.findById(inventoryId).orElseThrow(()-> new InventoryNotFoundException(inventoryId));
		return inventoryDetail;

	}

	@Override
	public InventoryDetail addInventory(int inventoryId,InventoryDetail inventoryDetail,int itemId){
		boolean checkItemId = itemDao.findById(itemId).isPresent();
		Item item = itemDao.getReferenceById(itemId);
		String itemname = item.getItemName();
		if (checkItemId == true){
		boolean checkInventoryId = inventoryDetailDao.findById(inventoryId).isPresent();
		if (checkInventoryId == true) {
			throw new InventoryAlreadyExists(inventoryId,itemId,itemname);
		} else {
			return inventoryDetailDao.save(inventoryDetail);
		}
	}
		else
			throw new ItemNotFoundException(itemId);
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
