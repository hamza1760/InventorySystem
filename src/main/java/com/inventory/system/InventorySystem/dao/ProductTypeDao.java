package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.system.InventorySystem.entities.ProductType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProductTypeDao extends JpaRepository<ProductType, Integer> {



    @Modifying
    @Query("Update ProductDetail ItemType Set status='deleted' Where productId =?1 ")
    public void softDelete(int productTypeId);

    List<ProductType> findByStatus(String status);

    public ProductType findByStatusAndProductTypeId(String status, int productTypeId);


}
