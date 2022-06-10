package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.dao.ProductDetailDao;
import com.inventory.system.InventorySystem.entities.ProductDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ProductAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDetailDao productDetailDao;

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
	public ProductDetail addProduct(ProductDetail productDetail) {

		int productId= productDetail.getProductId();
		boolean checkId = productDetailDao.findById(productId).isPresent();
		if(checkId==true){
			throw new ProductAlreadyExists(productId);
		}
		else {
			return productDetailDao.save(productDetail);
		}
	}

	@Override
	public void deleteProduct() {
		// TODO Auto-generated method stub

	}

}
