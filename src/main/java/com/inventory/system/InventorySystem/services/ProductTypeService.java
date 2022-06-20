package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.ProductType;


public interface ProductTypeService {
	
public List<ProductType> getProductType();
	
	public ProductType getProductTypeById(int productTypeId);
	
	public ProductType addProductType(ProductType productDetail);

	public ProductType saveProductType(ProductType productDetail);

	public void deleteProductType(int  productTypeId);
	

}
