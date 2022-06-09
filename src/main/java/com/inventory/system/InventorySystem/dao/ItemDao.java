package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.Item;

public interface ItemDao extends JpaRepository<Item, Integer>{
	

}
