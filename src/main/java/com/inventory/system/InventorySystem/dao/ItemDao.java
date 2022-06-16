package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.ItemSize;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.Item;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemDao extends JpaRepository<Item, Integer>{



    @Query("Select new com.inventory.system.InventorySystem.entities.ItemSize(A.inventoryId,A.itemSize,B.itemName,C.itemType,D.productType,E.brandName) " +
            "FROM InventoryDetail A " +
            "JOIN A.item B " +
            "JOIN B.itemTypeSet C " +
            "JOIN C.products D "+
            "JOIN D.brands E "+
             "where B.itemId =?1" )
    public List<ItemSize> getItemSizeById(int itemId);

    @Query("Select new com.inventory.system.InventorySystem.entities.ItemSize(A.inventoryId,A.itemSize,B.itemName,C.itemType,D.productType,E.brandName) " +
            "FROM InventoryDetail A " +
            "JOIN A.item B " +
            "JOIN B.itemTypeSet C " +
            "JOIN C.products D "+
            "JOIN D.brands E ")
    public List<ItemSize> getAllItemSize();
	

}
