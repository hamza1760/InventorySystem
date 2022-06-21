package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.entities.WarehouseAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.Warehouse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Transactional
public interface WarehouseDao extends JpaRepository<Warehouse, Integer> {



    @Query("Select new com.inventory.system.InventorySystem.entities.ItemQuantity(A.warehouseId,A.warehouseName,B.areaName," +
            "C.cityName,D.countryName,E.inventoryId,E.itemSize,E.inStock,E.avlQty,F.itemName,F.itemId,G.itemType,H.productType,I.brandName) "+
            "From Warehouse A " +
            "Join A.address B " +
            "Join B.city C " +
            "Join C.country D " +
            "Join A.inventory E " +
            "Join E.item F " +
            "Join E.itemType G " +
            "Join F.productType H " +
            "Join F.brand I "+
            "Where A.warehouseId =?1")
    public List<ItemQuantity> getItemQuantityInSingleWarehouse(int warehouseId);


    @Query("Select new com.inventory.system.InventorySystem.entities.ItemQuantity(A.warehouseId,A.warehouseName,B.areaName," +
            "C.cityName,D.countryName,E.inventoryId,E.itemSize,E.inStock,E.avlQty,F.itemName,F.itemId,G.itemType,H.productType,I.brandName) "+
            "From Warehouse A " +
            "Join A.address B " +
            "Join B.city C " +
            "Join C.country D " +
            "Join A.inventory E " +
            "Join E.item F " +
            "Join E.itemType G " +
            "Join F.productType H " +
            "Join F.brand I ")
    public List<ItemQuantity> getItemQuantityAllWarehouses();



    @Modifying
    @Query("Update Warehouse Set status='deleted' Where warehouseId =?1 ")
    public void softDelete(int warehouseId);

    List<Warehouse> findByStatus(String status);

    public Warehouse findByStatusAndWarehouseId(String status,int warehouseId);

}
