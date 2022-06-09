package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.ItemType;



public interface ItemTypeService {
	
public List<ItemType> getItemType();
	
	public ItemType getItemTypeById();
	
	public ItemType addItemType(ItemType itemType);
	
	public void deleteItemType();
	

}
