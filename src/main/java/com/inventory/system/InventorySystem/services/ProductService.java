package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.ProductDetail;


public interface ProductService {
	
public List<ProductDetail> getProduct();
	
	public ProductDetail getProductById(int productId);
	
	public ProductDetail addProduct(ProductDetail productDetail,int brandId);

	public ProductDetail saveProduct(ProductDetail productDetail);

	public void deleteProduct(int  productId);
	

}
