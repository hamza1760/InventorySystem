package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.WarehouseAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.system.InventorySystem.entities.Warehouse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseDao extends JpaRepository<Warehouse, Integer> {

    @Query("Select new com.inventory.system.InventorySystem.entities.WarehouseAddress( A.countryName,B.cityName,C.areaName,D.warehouseName) " +
            "FROM CountryDetail A " +
            "JOIN A.cityDetails B " +
            "JOIN B.address C " +
            "JOIN C.warehouses D "+
            "where A.countryId =?1 and B.country =?1 " +
            "and B.cityId =?2 and C.city =?2 "+
            "and C.addressId =?3 and D.address =?3")
    public List<WarehouseAddress> getWarehouseAddress(int countryId, int cityId, int addressId);

}
