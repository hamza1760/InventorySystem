package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.dao.BrandDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.ProductTypeDao;
import com.inventory.system.InventorySystem.entities.BrandDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemSize;
import com.inventory.system.InventorySystem.entities.ProductType;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import com.inventory.system.InventorySystem.pojo.ItemDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    final String ITEM_NOT_FOUND = "Item Not Found";
    final String ITEM_ALREADY_EXIST = "Item Already Exist";

    final String BRAND_NOT_FOUND = "Brand Not Found";

    final String PRODUCT_TYPE_NOT_FOUND = "Product Not Found";

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
        ProductType productType = productTypeDao.findById(productTypeId).orElseThrow(() -> new NotFoundException(PRODUCT_TYPE_NOT_FOUND, productTypeId));
        String productTypeStatus = productType.getStatus();
        if (productTypeStatus.contains("deleted")) {
            throw new NotFoundException(PRODUCT_TYPE_NOT_FOUND, productTypeId);
        }
        BrandDetail brandInItem = item.getBrand();
        int brandId = brandInItem.getBrandId();
        BrandDetail brand = brandDetailDao.findById(brandId).orElseThrow(() -> new NotFoundException(BRAND_NOT_FOUND, brandId));
        String brandStatus = brand.getStatus();
        if (brandStatus.contains("deleted")) {
            throw new NotFoundException(BRAND_NOT_FOUND, brandId);
        }
        int itemId = item.getItemId();
        boolean checkItemId = itemDao.findById(itemId).isPresent();
        if (checkItemId) {
            throw new AlreadyExists(ITEM_ALREADY_EXIST, itemId);
        } else {
            item.setProductType(productType);
            item.setBrand(brand);
            return itemDao.save(item);
        }
    }

    @Override
    public List<Item> getItem() {
        return itemDao.findByStatus("active");
    }

    @Override
    public Item getItemById(int itemId) {
        itemDao.findById(itemId).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND, itemId));
        return itemDao.findByStatusAndItemId("active", itemId);
    }


    @Override
    public Item updateItem(Item item, int itemId) {
        Item updateItem = itemDao.findById(itemId).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND, itemId));
        updateItem.setItemName(item.getItemName());
        Item updatedItem = itemDao.save(updateItem);
        return updatedItem;
    }

    @Override
    public void deleteItemById(int itemId) {
        itemDao.findById(itemId).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND, itemId));
        itemDao.softDelete(itemId);
    }


    @Override
    public List<ItemSize> getItemSizeById(int itemId) {
        itemDao.findById(itemId).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND, itemId));
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
