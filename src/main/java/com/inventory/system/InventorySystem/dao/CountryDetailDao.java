package com.inventory.system.InventorySystem.dao;

import com.inventory.system.InventorySystem.entities.CountryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CountryDetailDao extends JpaRepository<CountryDetail, Integer> {

    @Modifying
    @Query("UPDATE CountryDetail SET status='deleted' WHERE countryId =?1")
    void softDelete(Integer id);


    @Query("From CountryDetail where status='active' and countryId =?1")
    CountryDetail getCountryById(Integer id);
}


