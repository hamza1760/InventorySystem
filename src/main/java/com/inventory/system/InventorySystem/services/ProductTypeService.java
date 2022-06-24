package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.ProductType;

import java.util.List;


public interface ProductTypeService {
	
public List<ProductType> getProductType();
	
	public ProductType getProductTypeById(int productTypeId);
	
	public ProductType addProductType(ProductType productDetail);

	public ProductType saveProductType(ProductType productDetail);

	public void deleteProductType(int  productTypeId);
	

}
