package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dto.*;

import java.util.*;

public interface ItemService {

    List<ItemDto> getItem();

    ItemDto addItem(ItemDto item);

    void deleteItemById(int itemId);

    ItemDto getItemById(int itemId);

    List<ItemSizeDto> getItemSizeById(int itemId);

    List<ItemSizeDto> getAllItemSize();
}
