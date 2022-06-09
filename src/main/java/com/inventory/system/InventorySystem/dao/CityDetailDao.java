package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.CityDetail;

public interface CityDetailDao extends JpaRepository<CityDetail, String> {

}
