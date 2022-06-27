package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProductTypeDao extends JpaRepository<ProductType, Integer> {


    @Modifying
    @Query("Update ProductType Set status=?1 Where productTypeId = ?2")
    void softDelete(String status,int productTypeId);

    List<ProductType> findByStatus(String status);

    ProductType findByStatusAndProductTypeId(String status, int productTypeId);
}
