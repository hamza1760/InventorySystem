package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.ProductType;

import java.util.List;


public interface ProductTypeService {

    List<ProductType> getProductType();

    ProductType getProductTypeById(int productTypeId);

    ProductType addProductType(ProductType productDetail);

    void deleteProductType(int productTypeId);
}
