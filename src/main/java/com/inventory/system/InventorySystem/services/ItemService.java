package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dto.*;

import java.util.*;

public interface ItemService {

    List<ItemDto> getItem();

    ItemDto addItem(ItemDto itemDto);

    ItemDto getItemById(int itemId);

    List<ItemSizeDto> getAllItemSize();

    List<ItemSizeDto> getItemSizeById(int itemId);

    void deleteItemById(int itemId);
}
