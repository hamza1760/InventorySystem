package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.*;

public interface AddressDao extends JpaRepository<Address, Integer> {

    @Transactional
    @Query("update Address set status = ?1 where addressId = ?2")
    @Modifying
    void softDelete(String status, int addressId);
}
