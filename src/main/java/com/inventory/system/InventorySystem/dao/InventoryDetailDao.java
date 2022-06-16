package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.InventoryDetail;

public interface InventoryDetailDao extends JpaRepository<InventoryDetail, Integer>  {




}
