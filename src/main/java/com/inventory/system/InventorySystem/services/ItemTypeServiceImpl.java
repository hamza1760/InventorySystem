package com.inventory.system.InventorySystem.services;

import java.util.List;


import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.dao.ProductDetailDao;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.entities.ProductDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ItemAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ItemTypeAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.ItemTypeNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.ItemTypeNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.ProductNotFoundException;
import com.inventory.system.InventorySystem.services.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {

	@Autowired
	private ItemTypeDao itemTypeDao;

	@Autowired
	private ProductDetailDao productDetailDao;


	@Override
	public List<ItemType> getItemType() {
		return itemTypeDao.findAll();
	}

	@Override
	public ItemType getItemTypeById(int itemTypeId) {
		ItemType itemType = itemTypeDao.findById(itemTypeId).orElseThrow( ()->new ItemTypeNotFoundException(itemTypeId));
		return itemType;
	}

	@Override
	public ItemType addItemType(ItemType itemType,int productId) {

		ProductDetail productDetail = productDetailDao.findById(productId).orElseThrow(()-> new ProductNotFoundException(productId));

			return itemTypeDao.save(itemType);

	}

	@Override
	public ItemType saveItemType(ItemType itemType) {
		return itemTypeDao.save(itemType);
	}


	@Override
	public void deleteItemType(int itemTypeId) {
		ItemType itemType = itemTypeDao.findById(itemTypeId).orElseThrow(()-> new ItemTypeNotFoundException(itemTypeId));
		itemTypeDao.delete(itemType);
		
	}



}
