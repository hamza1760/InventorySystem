package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ItemAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ItemTypeAlreadyExists;
import com.inventory.system.InventorySystem.services.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {

	@Autowired
	private ItemTypeDao itemTypeDao;


	@Override
	public List<ItemType> getItemType() {
		return itemTypeDao.findAll();
	}

	@Override
	public ItemType getItemTypeById(int itemTypeId) {
		ItemType itemType = itemTypeDao.findById(itemTypeId).orElseThrow( ()->new ItemTypeAlreadyExists(itemTypeId));
		return itemType;
	}

	@Override
	public ItemType addItemType(ItemType itemType) {
		int itemId= itemType.getItemTypeId();
		boolean checkId = itemTypeDao.findById(itemId).isPresent();
		if(checkId==true){
			throw new ItemTypeAlreadyExists(itemId);
		}
		else {
			return itemTypeDao.save(itemType);
		}
	}

	@Override
	public void deleteItemType() {
		// TODO Auto-generated method stub
		
	}



}
