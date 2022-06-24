package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.BrandDetail;

import java.util.List;



public interface BrandService {
	
	public List<BrandDetail> getBrand();
	
	public BrandDetail getBrandById(int brandId);
	
	public BrandDetail addBrand(BrandDetail brandDetail);
	public BrandDetail saveBrand(BrandDetail brandDetail);
	
	public void deleteBrand(int brandId);
	
	

}
