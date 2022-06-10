package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.Address;


public interface AddressDao extends JpaRepository<Address, Integer> {

}
