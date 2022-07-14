package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AddressDao extends JpaRepository<Address, Integer> {

    @Transactional
    @Query("update Address set status = ?1 where addressId = ?2")
    @Modifying
    void softDelete(String status, int addressId);
}
