package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.ItemQuantity;
import com.inventory.system.InventorySystem.entities.WarehouseAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.Warehouse;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;
import java.util.List;

public interface WarehouseDao extends JpaRepository<Warehouse, Integer> {

    @Query("Select new com.inventory.system.InventorySystem.entities.WarehouseAddress( A.countryName,B.cityName,C.areaName,D.warehouseName) " +
            "FROM CountryDetail A " +
            "JOIN A.cityDetails B " +
            "JOIN B.address C " +
            "JOIN C.warehouse D "+
            "where A.countryId =?1 and B.country =?1 " +
            "and B.cityId =?2 and C.city =?2 "+
            "and C.addressId =?3 and D.address =?3")
    public List<WarehouseAddress> getWarehouseAddress(int countryId, int cityId, int addressId);


    @Query("Select new com.inventory.system.InventorySystem.entities.ItemQuantity(A.warehouseName,B.areaName," +
            "C.cityName,D.countryName,E.inventoryId,E.itemSize,E.inStock,E.avlQty,F.itemName,F.itemColor,G.itemType,H.productType,I.brandName) "+
            "From Warehouse A " +
            "Join A.address B " +
            "Join B.city C " +
            "Join C.country D " +
            "Join A.inventory E " +
            "Join E.item F Join " +
            "F.itemTypeSet G " +
            "Join G.products H " +
            "Join H.brands I "+
            "Where A.warehouseId =?1")
    public List<ItemQuantity> getItemQuantityInSingleWarehouse(int warehouseId);

}
