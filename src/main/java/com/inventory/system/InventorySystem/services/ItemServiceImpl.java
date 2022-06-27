package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;
import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    static Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);


    @Autowired
    private ItemDao itemDao;

    @Autowired
    private BrandDetailDao brandDetailDao;

    @Autowired
    private ProductTypeDao productTypeDao;

    @Autowired
    private ModelMapper modelMapper;


    public Item addItem(Item item) {
        int productTypeId = item.getProductType().getProductTypeId();
        ProductType productType = productTypeDao.findById(productTypeId).orElseThrow(() -> new NotFoundException(NotFoundConstant.PRODUCT_TYPE_NOT_FOUND, productTypeId));
        if (productType.getStatus().equals("Deleted")) {
            throw new NotFoundException(NotFoundConstant.PRODUCT_TYPE_NOT_FOUND, productTypeId);
        }
        BrandDetail brandInItem = item.getBrand();
        int brandId = brandInItem.getBrandId();
        BrandDetail brand = brandDetailDao.findById(brandId).orElseThrow(() -> new NotFoundException(NotFoundConstant.BRAND_NOT_FOUND, brandId));
        if (brand.getStatus().equals("Deleted")) {
            throw new NotFoundException(NotFoundConstant.BRAND_NOT_FOUND, brandId);
        }
        int itemId = item.getItemId();
        boolean checkItemId = itemDao.findById(itemId).isPresent();
        if (checkItemId) {
            throw new AlreadyExists(AlreadyExistsConstant.ITEM_ALREADY_EXISTS, itemId);
        } else {
            item.setProductType(productType);
            item.setBrand(brand);
            return itemDao.save(item);
        }
    }

    @Override
    public List<Item> getItem() {
        logger.info("returning list of items with status active");
        return itemDao.findByStatus("Active");
    }

    @Override
    public Item getItemById(int itemId) {
        logger.info("checking if the item is present in database with itemId: " + itemId);
        itemDao.findById(itemId).orElseThrow(() -> new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
        logger.info("returning item with itemId: " + itemId + " and status active");
        return itemDao.findByStatusAndItemId("Active", itemId);
    }


    @Override
    public Item updateItem(Item item, int itemId) {
        Item updateItem = itemDao.findById(itemId).orElseThrow(() -> new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
        updateItem.setItemName(item.getItemName());
        Item updatedItem = itemDao.save(updateItem);
        return updatedItem;
    }

    @Override
    public void deleteItemById(int itemId) {
        itemDao.findById(itemId).orElseThrow(() -> new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
        itemDao.softDelete(StatusConstant.DELETED.getValue(), itemId);
    }


    @Override
    public List<ItemSize> getItemSizeById(int itemId) {
        logger.info("checking if the item is present in the database with itemId: " + itemId);
        itemDao.findById(itemId).orElseThrow(() -> new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
        logger.info("returning item size of of item with itemId: " + itemId);
        return itemDao.getItemSizeById(itemId);
    }

    @Override
    public List<ItemSize> getAllItemSize() {
        logger.info("returning list of itemSize based on custom query");
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
