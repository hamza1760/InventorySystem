package com.inventory.system.InventorySystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.system.InventorySystem.entities.ProductDetail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ProductDetailDao extends JpaRepository<ProductDetail, Integer> {



    @Modifying
    @Query("Update ProductDetail ItemType Set status='deleted' Where productId =?1 ")
    public void softDelete(int productId);

    List<ProductDetail> findByStatus(String status);

    public ProductDetail findByStatusAndProductId(String status,int productId);


}
