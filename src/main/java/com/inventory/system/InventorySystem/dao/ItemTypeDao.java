package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ItemTypeDao extends JpaRepository<ItemType, Integer> {


    @Modifying
    @Query("Update ItemType Set status=?1 Where itemTypeId =?2 ")
    void softDelete(String status, int itemTypeId);

    List<ItemType> findByStatus(String status);

    ItemType findByStatusAndItemTypeId(String status, int itemtypeId);
}
