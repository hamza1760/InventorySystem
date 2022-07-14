package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.InventoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface InventoryDetailDao extends JpaRepository<InventoryDetail, Integer> {

    @Modifying
    @Query("Update InventoryDetail Set status=?1 Where inventoryId =?2 ")
    void softDelete(String status, int inventoryId);

    List<InventoryDetail> findByStatus(String status);

    InventoryDetail findByStatusAndInventoryId(String status, int inventoryId);
}
