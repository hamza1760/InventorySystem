package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.BrandDetail;



public interface BrandService {
	
	public List<BrandDetail> getBrand();
	
	public BrandDetail getBrandById(int brandId);
	
	public BrandDetail addBrand(BrandDetail brandDetail);
	
	public void deleteBrand(int brandId);
	
	

}
