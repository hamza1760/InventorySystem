package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Transactional
public interface InventoryDetailDao extends JpaRepository<InventoryDetail, Integer> {

    @Modifying
    @Query("Update InventoryDetail Set status=?1 Where inventoryId =?2 ")
    void softDelete(String status, int inventoryId);

    List<InventoryDetail> findByStatus(String status);

    InventoryDetail findByStatusAndInventoryId(String status, int inventoryId);
}
