package com.inventory.system.InventorySystem.services;

import java.util.List;



import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.pojo.ItemDto;

public interface ItemService {
	
	public List<ItemDto> getItem();
	
	public Item addItem(Item item);
	
	public Item updateItem (Item item,int Itemid);
	
	public void deleteItemById(int itemId);

	public Item viewSize(int itemId);
	
	public ItemDto getItemById (int itemId);
	
	public Item itemDtoToItem(ItemDto itemDto);
	
	public  ItemDto itemToItemDto(Item item);
	

}
