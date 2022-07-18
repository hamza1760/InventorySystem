package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Transactional
public interface ItemTypeDao extends JpaRepository<ItemType, Integer> {

    @Modifying
    @Query("Update ItemType Set status=?1 Where itemTypeId =?2 ")
    void softDelete(String status, int itemTypeId);

    List<ItemType> findByStatus(String status);

    ItemType findByStatusAndItemTypeId(String status, int itemTypeId);
}
