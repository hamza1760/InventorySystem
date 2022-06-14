package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.InventoryAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.InventoryNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.InventoryNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	public InventoryDetail addInventory(InventoryDetail inventoryDetail,int itemId) {

		Item item = itemDao.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));

		int inventoryId = inventoryDetail.getInventoryId();
		boolean checkInventoryId = inventoryDetailDao.findById(inventoryId).isPresent();
		if (checkInventoryId == true) {
			InventoryDetail inventoryDetail1 = inventoryDetailDao.getReferenceById(inventoryId);
			Item item1 = inventoryDetail1.getItem();
			int id = item1.getItemId();
			String name = item.getItemName();
			throw new InventoryAlreadyExists(inventoryId, id, name);

		} else
			try {
				inventoryDetailDao.save(inventoryDetail);
				return inventoryDetailDao.save(inventoryDetail);
			} catch (Exception e) {
				throw new DataIntegrityViolationException("inventory is soft deleted");
			}
	}

	@Override
	public InventoryDetail saveInventory(InventoryDetail inventoryDetail) {
		return inventoryDetailDao.save(inventoryDetail);
	}


	@Override
	public InventoryDetail updateInventoryById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInventory(int inventoryId) {
		InventoryDetail inventoryDetail = inventoryDetailDao.findById(inventoryId).orElseThrow(()-> new InventoryNotFoundException(inventoryId));
		inventoryDetailDao.delete(inventoryDetail);
		
	}

	

}
