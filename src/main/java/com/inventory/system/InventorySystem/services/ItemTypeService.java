package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.ItemType;

import java.util.List;


public interface ItemTypeService {

    List<ItemType> getItemType();

    ItemType getItemTypeById(int itemTypeId);

    ItemType addItemType(ItemType itemType);

    void deleteItemType(int itemTypeId);
}
