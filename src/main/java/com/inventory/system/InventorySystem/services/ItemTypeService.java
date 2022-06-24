package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.ItemType;

import java.util.List;



public interface ItemTypeService {
	
public List<ItemType> getItemType();
	
	public ItemType getItemTypeById(int itemTypeId);
	
	public ItemType addItemType(ItemType itemType);

	public ItemType saveItemType(ItemType itemType);
	
	public void deleteItemType(int itemTypeId);
	

}
