package com.inventory.system.InventorySystem.services;

import java.util.Iterator;
import java.util.List;


import java.util.Set;
import java.util.stream.Collectors;

import com.inventory.system.InventorySystem.dao.BrandDetailDao;
import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.dao.ProductTypeDao;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.exceptions.DataIntegrityException;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.WarehouseAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ItemAlreadyExists;
import com.inventory.system.InventorySystem.pojo.ItemDto;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private BrandDetailDao brandDetailDao;

	@Autowired
	private ProductTypeDao productTypeDao;

	@Autowired
	private ModelMapper modelMapper;


	public Item addItem(Item item) {

		ProductType productTypeInItem = item.getProductType();
		int productTypeId = productTypeInItem.getProductTypeId();
		productTypeDao.findById(productTypeId).orElseThrow(()-> new ProductTypeNotFoundException(productTypeId));

		BrandDetail brandInItem = item.getBrand();
		int brandId= brandInItem.getBrandId();
		brandDetailDao.findById(brandId).orElseThrow(()-> new BrandNotFoundException(brandId));

		int itemId= item.getItemId();
		boolean checkItemId = itemDao.findById(itemId).isPresent();
		if(checkItemId==true){
			throw new ItemAlreadyExists(itemId);
		}

		else{

			ProductType productType = productTypeDao.getReferenceById(productTypeId);
			Item itemInProduct = productType.getItem();
			if(itemInProduct==null){
				item.setProductType(productType);
			}
			else{
				int itemIdInProduct = itemInProduct.getItemId();
				throw new DataIntegrityException("Product already exist in other item",itemIdInProduct);
			}

			BrandDetail brand = brandDetailDao.getReferenceById(brandId);
			Item itemInBrand = brand.getItem();
			if(itemInBrand==null){
				item.setBrand(brand);
			}
			else{
				int itemIdInBrand  = itemInBrand.getItemId();
				throw new DataIntegrityException("Brand already exist in other item",itemIdInBrand);
			}

			return itemDao.save(item);




		}


	}

	@Override
	public Item saveItem(Item item) {
		return itemDao.save(item);
	}

	@Override
	public List<Item> getItem() {

		return itemDao.findByStatus("active");
	}

	@Override
	public Item getItemById(int itemId) {

		itemDao.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
		return itemDao.findByStatusAndItemId("active",itemId);

	}



	@Override
	public Item updateItem(Item item, int itemId) {
		Item updateItem = itemDao.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
		updateItem.setItemName(item.getItemName());
		Item updatedItem = itemDao.save(updateItem);
		return updatedItem;
	}

	@Override
	public void deleteItemById(int itemId) {

		Item item = itemDao.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
		itemDao.softDelete(itemId);

	}



	@Override
	public List<ItemSize> getItemSizeById(int itemId) {
		itemDao.findById(itemId).orElseThrow(()-> new ItemNotFoundException(itemId));
		return itemDao.getItemSizeById(itemId);
	}

	@Override
	public List<ItemSize> getAllItemSize() {
		return itemDao.getAllItemSize();
	}


	public ItemDto itemToItemDto(Item item) {
		ItemDto itemDto = new ItemDto();
		itemDto = modelMapper.map(item, ItemDto.class);
		return itemDto;
	}

	public Item itemDtoToItem(ItemDto itemDto) {
		Item item = new Item();
		item = modelMapper.map(itemDto, Item.class);
		return item;
	}

}

////ItemDto itemDto = new ItemDto();
////itemDto.setItemId(item.getItemId());
////itemDto.setItemType(item.getItemType());
////return itemDto;
