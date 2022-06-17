package com.inventory.system.InventorySystem.services;

import java.util.List;
import java.util.Set;


import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.WarehouseDao;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.InventoryAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.InventoryNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.InventoryNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.ItemNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.WarehouseNotFoundException;
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
	public List<InventoryDetail> getInventory() {
		return inventoryDetailDao.findByStatus("active");
	}

	@Override
	public InventoryDetail getInventoryById(int inventoryId) {

		inventoryDetailDao.findById(inventoryId).orElseThrow(()-> new InventoryNotFoundException(inventoryId));
		return inventoryDetailDao.findByStatusAndInventoryId("active",inventoryId);

	}




	@Override
	public InventoryDetail setItemQuantityInAllWarehouses(InventoryDetail inventoryDetail,int inventoryId) {

		InventoryDetail setItemQuantity = inventoryDetailDao.findById(inventoryId).orElseThrow(()-> new InventoryNotFoundException(inventoryId));
		setItemQuantity.setAvlQty(inventoryDetail.getAvlQty());
		setItemQuantity.setInStock(inventoryDetail.getInStock());
		return inventoryDetailDao.save(setItemQuantity);
	}




	@Override
	public InventoryDetail updateInventoryById() {
		return null;
	}


	@Override
	public void deleteInventory(int inventoryId) {
		InventoryDetail inventoryDetail = inventoryDetailDao.findById(inventoryId).orElseThrow(()-> new InventoryNotFoundException(inventoryId));
		inventoryDetailDao.softDelete(inventoryId);
		
	}

	

}
