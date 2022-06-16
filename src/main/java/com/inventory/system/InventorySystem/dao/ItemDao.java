package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.ItemSize;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.Item;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemDao extends JpaRepository<Item, Integer>{



    @Query("Select new com.inventory.system.InventorySystem.entities.ItemSize(A.itemName,A.itemSize,B.itemType,C.productType,D.brandName) " +
            "FROM Item A " +
            "JOIN A.itemTypeSet B " +
            "JOIN B.products C " +
            "JOIN C.brands D "+
            "where A.itemId =?1")
    public ItemSize getItemSize(int itemId);

    @Query("Select new com.inventory.system.InventorySystem.entities.ItemSize(A.itemName,A.itemSize,B.itemType,C.productType,D.brandName) " +
            "FROM Item A " +
            "JOIN A.itemTypeSet B " +
            "JOIN B.products C " +
            "JOIN C.brands D ")
    public List<ItemSize> getAllItemSize();
	

}
