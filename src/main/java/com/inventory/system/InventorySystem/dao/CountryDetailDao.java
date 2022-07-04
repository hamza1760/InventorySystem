package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.CountryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CountryDetailDao extends JpaRepository<CountryDetail, Integer> {

    @Query("update CountryDetail set status = ?1 where countryId = ?2")
    @Modifying
    void softDelete(String status, int countryId);


    @Query("From CountryDetail where status='active' and countryId =?1")
    CountryDetail getCountryById(Integer id);
}


