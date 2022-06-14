package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.dao.BrandDetailDao;
import com.inventory.system.InventorySystem.dao.ProductDetailDao;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.BrandDetail;
import com.inventory.system.InventorySystem.entities.ProductDetail;
import com.inventory.system.InventorySystem.entities.ProductDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ProductAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.AddressNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.BrandNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.ProductNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDetailDao productDetailDao;

	@Autowired
	private BrandDetailDao brandDetailDao;

	@Override
	public List<ProductDetail> getProduct() {
		return productDetailDao.findAll();
	}

	@Override
	public ProductDetail getProductById(int productId) {
		ProductDetail productDetail = productDetailDao.findById(productId).orElseThrow(()-> new ProductNotFoundException(productId));
		return productDetail;
	}

	@Override
	public ProductDetail addProduct(ProductDetail productDetail,int brandId) {

			return productDetailDao.save(productDetail);
	}

	@Override
	public ProductDetail saveProduct(ProductDetail productDetail) {
		return productDetailDao.save(productDetail);
	}


	@Override
	public void deleteProduct(int productId) {
		ProductDetail productDetail = productDetailDao.findById(productId).orElseThrow(()-> new ProductNotFoundException(productId));
		productDetailDao.delete(productDetail);

	}

}
