package com.inventory.system.InventorySystem.services;

import java.util.List;
import java.util.Set;

import com.inventory.system.InventorySystem.dao.*;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.entities.ProductDetail;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ProductAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.AddressNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.BrandNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.ProductNotFoundException;
import com.inventory.system.InventorySystem.exceptions.notfound.ProductNotFoundException;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDetailDao productDetailDao;

	@Autowired
	private BrandDetailDao brandDetailDao;

	@Autowired
	private ItemTypeDao itemTypeDao;

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private InventoryDetailDao inventoryDetailDao;



	@Override
	public List<ProductDetail> getProduct() {
		return productDetailDao.findByStatus("active");
	}

	@Override
	public ProductDetail getProductById(int productId) {
		 productDetailDao.findById(productId).orElseThrow(()-> new ProductNotFoundException(productId));
		 return productDetailDao.findByStatusAndProductId("active",productId);

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
		 ProductDetail product = productDetailDao.findById(productId).orElseThrow(()-> new ProductNotFoundException(productId));
		Set<ItemType> itemTypeSet = product.getItemTypeSet();
		for(ItemType itemType : itemTypeSet) {
			int itemTypeId= itemType.getItemTypeId();
			itemType.setStatus("deleted");
			itemTypeDao.save(itemType);
			ItemType itemType2 = itemTypeDao.getReferenceById(itemTypeId);
			Set<Item> itemSet = itemType2.getItems();
			for(Item item :itemSet){
				int itemId = item.getItemId();
				item.setStatus("deleted");
				itemDao.save(item);
				Item item2 = itemDao.getReferenceById(itemId);
				InventoryDetail inventory = item2.getInventoryDetail();
				inventory.setStatus("deleted");
				inventoryDetailDao.save(inventory);
			}

		}

			productDetailDao.softDelete(productId);
		}

		}



