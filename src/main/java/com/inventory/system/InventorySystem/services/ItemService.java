package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dto.ItemDto;
import com.inventory.system.InventorySystem.dto.ItemSizeDto;
import com.inventory.system.InventorySystem.entities.Item;

import java.util.List;

public interface ItemService {

    List<ItemDto> getItem();

    ItemDto addItem(Item item);

    Item updateItem(Item item, int Itemid);

    void deleteItemById(int itemId);

    ItemDto getItemById(int itemId);

    List<ItemSizeDto> getItemSizeById(int itemId);

    List<ItemSizeDto> getAllItemSize();

    ItemDto itemToItemDto(Item item);
}
