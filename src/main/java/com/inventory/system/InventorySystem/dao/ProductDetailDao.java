package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.system.InventorySystem.entities.ProductDetail;

public interface ProductDetailDao extends JpaRepository<ProductDetail, Integer> {

}
