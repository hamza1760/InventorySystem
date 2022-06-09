package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.ProductDetail;


public interface ProductService {
	
public List<ProductDetail> getProduct();
	
	public ProductDetail getProductById();
	
	public ProductDetail addProduct(ProductDetail productDetail);
	
	public void deleteProduct();
	

}
