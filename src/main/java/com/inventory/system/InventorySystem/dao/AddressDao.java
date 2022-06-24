package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressDao extends JpaRepository<Address, Integer> {

}
