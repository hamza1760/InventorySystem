package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ItemDao extends JpaRepository<Item, Integer> {


    @Query("Select new com.inventory.system.InventorySystem.entities.ItemSize(A.inventoryId,B.itemId,A.itemSize,B.itemName,C.itemType,D.productType,E.brandName) " +
            "FROM InventoryDetail A " +
            "JOIN A.item B " +
            "JOIN A.itemType C " +
            "JOIN B.productType D " +
            "JOIN B.brand E " +
            "where A.status=?1 and B.itemId =?2")
    List<ItemSize> getItemSizeById(String status, int itemId);

    @Query("Select new com.inventory.system.InventorySystem.entities.ItemSize(A.inventoryId,B.itemId,A.itemSize,B.itemName,C.itemType,D.productType,E.brandName) " +
            "FROM InventoryDetail A " +
            "JOIN A.item B " +
            "JOIN A.itemType C " +
            "JOIN B.productType D " +
            "JOIN B.brand E " +
            "where A.status =?1")
    List<ItemSize> getAllItemSize(String status);


    @Modifying
    @Query("Update Item Set status=?1 Where itemId =?2 ")
    void softDelete(String status, int itemId);

    List<Item> findByStatus(String status);

    Item findByStatusAndItemId(String status, int itemId);
}
