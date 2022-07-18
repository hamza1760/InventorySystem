package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Transactional
public interface ProductTypeDao extends JpaRepository<ProductType, Integer> {

    @Modifying
    @Query("Update ProductType Set status=?1 Where productTypeId = ?2")
    void softDelete(String status, int productTypeId);

    List<ProductType> findByStatus(String status);

    ProductType findByStatusAndProductTypeId(String status, int productTypeId);
}
