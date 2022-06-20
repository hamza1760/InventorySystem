package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.dao.ProductTypeDao;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.entities.ProductType;
import com.inventory.system.InventorySystem.exceptions.notfound.ItemTypeNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.ProductTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {

	@Autowired
	private ItemTypeDao itemTypeDao;

	@Autowired
	private ProductTypeDao productTypeDao;


	@Override
	public List<ItemType> getItemType() {
		return itemTypeDao.findByStatus("active");
	}

	@Override
	public ItemType getItemTypeById(int itemTypeId) {
		itemTypeDao.findById(itemTypeId).orElseThrow( ()->new ItemTypeNotFoundException(itemTypeId));
		return itemTypeDao.findByStatusAndItemTypeId("active",itemTypeId);
	}

	@Override
	public ItemType addItemType(ItemType itemType) {
			return itemTypeDao.save(itemType);

	}

	@Override
	public ItemType saveItemType(ItemType itemType) {
		return itemTypeDao.save(itemType);
	}


	@Override
	public void deleteItemType(int itemTypeId) {
		ItemType itemType = itemTypeDao.findById(itemTypeId).orElseThrow(()-> new ItemTypeNotFoundException(itemTypeId));
		itemTypeDao.softDelete(itemTypeId);
		
	}



}
