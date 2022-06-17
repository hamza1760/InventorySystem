package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.ItemType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ItemTypeDao extends JpaRepository<ItemType, Integer>{


    @Modifying
    @Query("Update ItemType Set status='deleted' Where itemTypeId =?1 ")
    public void softDelete(int itemTypeId);

    List<ItemType> findByStatus(String status);

    public ItemType findByStatusAndItemTypeId(String status,int itemtypeId);

}
