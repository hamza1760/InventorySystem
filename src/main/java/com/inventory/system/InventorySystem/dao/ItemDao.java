package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.ItemSize;
import com.inventory.system.InventorySystem.entities.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ItemDao extends JpaRepository<Item, Integer>{



   /* @Query("Select new com.inventory.system.InventorySystem.entities.ItemSize(A.inventoryId,A.itemSize,B.itemName,C.itemType,D.productType,E.brandName) " +
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
    public List<ItemSize> getAllItemSize();*/

    @Modifying
    @Query("Update Item Set status='deleted' Where itemId =?1 ")
    public void softDelete(int itemId);

    List<Item> findByStatus(String status);

    public Item findByStatusAndItemId(String status,int itemId);


	

}
