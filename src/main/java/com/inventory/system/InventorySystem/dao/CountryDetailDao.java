package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.CountryDetail;

public interface CountryDetailDao extends JpaRepository<CountryDetail, String>{

}
