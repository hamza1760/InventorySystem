package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Transactional
public interface WarehouseDao extends JpaRepository<Warehouse, Integer> {

    @Query("Select new com.inventory.system.InventorySystem.entities.ItemQuantity(A.warehouseId,A.warehouseName,B.areaName," +
            "C.cityName,D.countryName,E.inventoryId,E.itemSize,E.inStock,E.avlQty,F.itemName,F.itemId,G.itemType,H.productType,I.brandName) " +
            "From Warehouse A " +
            "Join A.address B " +
            "Join B.city C " +
            "Join C.country D " +
            "Join A.inventory E " +
            "Join E.item F " +
            "Join E.itemType G " +
            "Join F.productType H " +
            "Join F.brand I " +
            "Where E.status =?1 and A.warehouseId =?2")
    List<ItemQuantity> getItemQuantityInSingleWarehouse(String status, int warehouseId);

    @Query("Select new com.inventory.system.InventorySystem.entities.ItemQuantity(A.warehouseId,A.warehouseName,B.areaName," +
            "C.cityName,D.countryName,E.inventoryId,E.itemSize,E.inStock,E.avlQty,F.itemName,F.itemId,G.itemType,H.productType,I.brandName) " +
            "From Warehouse A " +
            "Join A.address B " +
            "Join B.city C " +
            "Join C.country D " +
            "Join A.inventory E " +
            "Join E.item F " +
            "Join E.itemType G " +
            "Join F.productType H " +
            "Join F.brand I " +
            "Where E.status =?1")
    List<ItemQuantity> getItemQuantityAllWarehouses(String status);

    @Modifying
    @Query("Update Warehouse Set status= ?1 Where warehouseId =?2")
    void softDelete(String status, int warehouseId);

    List<Warehouse> findByStatus(String status);

    Warehouse findByStatusAndWarehouseId(String status, int warehouseId);
}
