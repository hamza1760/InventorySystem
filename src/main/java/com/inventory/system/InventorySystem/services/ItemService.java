package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemSize;
import com.inventory.system.InventorySystem.pojo.ItemDto;

import java.util.List;

public interface ItemService {
	
	public List<Item> getItem();
	
	public Item addItem(Item item);

	public Item saveItem(Item item);
	
	public Item updateItem (Item item,int Itemid);
	
	public void deleteItemById(int itemId);

	
	public Item getItemById (int itemId);

	public List<ItemSize> getItemSizeById(int itemId);

	public List<ItemSize> getAllItemSize();
	
	public Item itemDtoToItem(ItemDto itemDto);
	
	public  ItemDto itemToItemDto(Item item);
	

}
