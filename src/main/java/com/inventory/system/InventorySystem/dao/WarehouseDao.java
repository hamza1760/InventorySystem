package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.Warehouse;

public interface WarehouseDao extends JpaRepository<Warehouse, Integer> {

}
