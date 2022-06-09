package com.inventory.system.InventorySystem.services;

import java.util.List;


import java.util.stream.Collectors;

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
	private ModelMapper modelMapper;

	@Override
	public List<ItemDto> getItem() {

		return itemDao.findAll().stream().map(this::itemToItemDto).collect(Collectors.toList());

	}

	public Item addItem(Item item) {

		int id = item.getItemId();
		boolean check = itemDao.findById(id).isPresent();
		if (check == true) {
			throw new ItemAlreadyExists(id);
		} else {
			return itemDao.save(item);
		}

	}

	@Override
	public Item updateItem(Item item, int itemId) {
		Item item1 = itemDao.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
		item1.setPassword(item.getPassword());
		item1.setItemName(item.getItemName());
		item1.setItemColor(item.getItemColor());
		Item updatedItem = itemDao.save(item1);
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
	public ItemDto getItemById(int itemId) {

		Item item = itemDao.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
		ItemDto itemDto = itemToItemDto(item);
		return itemDto;

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
