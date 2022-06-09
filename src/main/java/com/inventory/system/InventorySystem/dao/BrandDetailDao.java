package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.BrandDetail;

public interface BrandDetailDao extends JpaRepository<BrandDetail, Integer> {

}
