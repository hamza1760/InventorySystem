package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.WarehouseAddress;


public interface WarehouseAddressDao extends JpaRepository<WarehouseAddress, Long> {

}
