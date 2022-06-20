package com.inventory.system.InventorySystem.services;

import java.util.List;
import java.util.Set;

import com.inventory.system.InventorySystem.dao.*;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.entities.ProductType;
import com.inventory.system.InventorySystem.exceptions.notfound.ProductTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductTypeDao productTypeDao;

	@Autowired
	private BrandDetailDao brandDetailDao;

	@Autowired
	private ItemTypeDao itemTypeDao;

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private InventoryDetailDao inventoryDetailDao;



	@Override
	public List<ProductType> getProductType() {
		return productTypeDao.findByStatus("active");
	}

	@Override
	public ProductType getProductTypeById(int productTypeId) {
		 productTypeDao.findById(productTypeId).orElseThrow(()-> new ProductTypeNotFoundException(productTypeId));
		 return productTypeDao.findByStatusAndProductTypeId("active",productTypeId);

	}

	@Override
	public ProductType addProductType(ProductType productDetail, int brandId) {

			return productTypeDao.save(productDetail);
	}

	@Override
	public ProductType saveProductType(ProductType productDetail) {
		return productTypeDao.save(productDetail);
	}


	@Override
	public void deleteProductType(int productTypeId) {
		 ProductType product = productTypeDao.findById(productTypeId).orElseThrow(()-> new ProductTypeNotFoundException(productTypeId));
		/*Set<ItemType> itemTypeSet = product.getItemTypeSet();
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

		}*/

			productTypeDao.softDelete(productTypeId);
		}

		}



