package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemSize;

import java.util.List;

public interface ItemService {

    List<Item> getItem();

    Item addItem(Item item);

    Item updateItem(Item item, int Itemid);

    void deleteItemById(int itemId);


    Item getItemById(int itemId);

    List<ItemSize> getItemSizeById(int itemId);

    List<ItemSize> getAllItemSize();
//    Item itemDtoToItem(ItemDto itemDto);
//
//    ItemDto itemToItemDto(Item item);
}
