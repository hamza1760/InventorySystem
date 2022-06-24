package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.BrandDetail;

import java.util.List;


public interface BrandService {

    List<BrandDetail> getBrand();

    BrandDetail getBrandById(int brandId);

    BrandDetail addBrand(BrandDetail brandDetail);

    void deleteBrand(int brandId);
}
