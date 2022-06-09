package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.ItemType;

public interface ItemTypeDao extends JpaRepository<ItemType, Integer>{

}
