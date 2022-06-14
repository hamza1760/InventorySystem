package com.inventory.system.InventorySystem.services;

import java.util.Iterator;
import java.util.List;


import java.util.Set;
import java.util.stream.Collectors;

import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.exceptions.notfound.ItemTypeNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.ItemAlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.ItemNotFoundException;
import com.inventory.system.InventorySystem.pojo.ItemDto;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private ItemTypeDao itemTypeDao;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Item> getItem() {

		return itemDao.findAll();
//		return itemDao.findAll().stream().map(this::itemToItemDto).collect(Collectors.toList());

	}

	public Item addItem(Item item,int itemTypeId) {

		ItemType itemType = itemTypeDao.findById(itemTypeId).orElseThrow(()-> new ItemTypeNotFoundException(itemTypeId));

		return itemDao.save(item);
	}

	@Override
	public Item saveItem(Item item) {
		return itemDao.save(item);
	}


	@Override
	public Item updateItem(Item item, int itemId) {
		Item updateItem = itemDao.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
		updateItem.setPassword(item.getPassword());
		updateItem.setItemName(item.getItemName());
		updateItem.setItemColor(item.getItemColor());
		Item updatedItem = itemDao.save(updateItem);
		return updatedItem;
	}

	@Override
	public void deleteItemById(int itemId) {

		Item item = itemDao.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
		itemDao.delete(item);

	}


	@Override
	public Item viewSize(int itemId) {
		return itemDao.getReferenceById(itemId);
	}

	@Override
	public Item getItemById(int itemId) {

		Item item = itemDao.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
		return item;

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
