package com.inventory.system.InventorySystem.services;

import java.util.List;



import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.exceptions.DataIntegrityException;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.InventoryAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.*;
import com.inventory.system.InventorySystem.exceptions.notfound.InventoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryDetailDao inventoryDetailDao;


	@Autowired
	private ItemDao itemDao;

	@Autowired
	private ItemTypeDao itemTypeDao;


	@Override
	public InventoryDetail addInventory(InventoryDetail inventoryDetail) {

		Item itemInInventory = inventoryDetail.getItem();
		int itemId = itemInInventory.getItemId();
		Item item = itemDao.findById(itemId).orElseThrow(()-> new ItemNotFoundException(itemId));
		String itemStatus = item.getStatus();
		if(itemStatus.contains("deleted")){
			throw new ItemNotFoundException(itemId);
		}

		ItemType itemTypeInInventory = inventoryDetail.getItemType();
		int itemTypeId = itemTypeInInventory.getItemTypeId();
		ItemType itemType = itemTypeDao.findById(itemTypeId).orElseThrow(()-> new ItemTypeNotFoundException(itemTypeId));
		String itemTypeStatus = itemType.getStatus();
		if(itemTypeStatus.contains("deleted")){
			throw new ItemTypeNotFoundException(itemTypeId);
		}

		int inventoryId = inventoryDetail.getInventoryId();
		boolean checkInventory = inventoryDetailDao.findById(inventoryId).isPresent();
		if(checkInventory==true){
			throw new InventoryAlreadyExists(inventoryId);
		}
		else{
			inventoryDetail.setItem(item);
			inventoryDetail.setItemType(itemTypeInInventory);
		}
		return inventoryDetailDao.save(inventoryDetail);



			
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
