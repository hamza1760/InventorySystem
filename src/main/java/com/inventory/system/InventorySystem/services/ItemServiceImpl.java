package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;
import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import com.inventory.system.InventorySystem.dao.BrandDetailDao;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.ProductTypeDao;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.exceptions.DataIntegrityException;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private InventoryDetailDao  inventoryDetailDao;

    @Autowired
    private ModelMapper modelMapper;


    public Item addItem(Item item) {
        if (item.getStatus().equals(StatusConstant.ACTIVE.getValue())) {
            logger.info("getting product type id from request body");
            int productTypeId = item.getProductType().getProductTypeId();
            logger.info("checking if productType exists in database with productId: " + productTypeId);
            ProductType productType = productTypeDao.findById(productTypeId).orElseThrow(() -> {
                logger.info("throwing exception " + NotFoundConstant.PRODUCT_TYPE_NOT_FOUND.getValue() + " with productTypeId: " + productTypeId);
                throw new NotFoundException(NotFoundConstant.PRODUCT_TYPE_NOT_FOUND, productTypeId);
            });
            logger.info("product found in database");
            logger.info("checking product status");
            if (productType.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("productType status is deleted");
                logger.info("throwing exception " + NotFoundConstant.PRODUCT_TYPE_NOT_FOUND.getValue() + " with productTypeId: " + productTypeId);
                throw new NotFoundException(NotFoundConstant.PRODUCT_TYPE_NOT_FOUND, productTypeId);
            }
            logger.info("getting brandId from request body");
            int brandId = item.getBrand().getBrandId();
            logger.info("checking if brand exists in database with brandId: " + brandId);
            BrandDetail brand = brandDetailDao.findById(brandId).orElseThrow(() -> {
                logger.info("throwing exception " + NotFoundConstant.BRAND_NOT_FOUND.getValue() + " with brandId: " + brandId);
                throw new NotFoundException(NotFoundConstant.BRAND_NOT_FOUND, brandId);
            });
            logger.info("brand found in database");
            logger.info("checking brand status");
            if (brand.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("brand status is deleted");
                logger.info("throwing exception " + NotFoundConstant.BRAND_NOT_FOUND.getValue() + " with brandId: " + brandId);
                throw new NotFoundException(NotFoundConstant.BRAND_NOT_FOUND, brandId);
            }
            logger.info("getting itemId from request body");
            int itemId = item.getItemId();
            logger.info("checking if item is already present in database with itemId: " + itemId);
            boolean checkItemId = itemDao.findById(itemId).isPresent();
            if (checkItemId) {
                logger.info("item found in database");
                logger.info("throwing exception " + AlreadyExistsConstant.ITEM_ALREADY_EXISTS.getValue() + " with itemId: " + itemId);
                throw new AlreadyExists(AlreadyExistsConstant.ITEM_ALREADY_EXISTS, itemId);
            } else {
                logger.info("setting productType to item");
                item.setProductType(productType);
                logger.info("setting brand to item");
                item.setBrand(brand);
                logger.info("Saving item in database with itemId: " + itemId + " productTypeId: " + productTypeId + " brandId: " + brandId);
                return itemDao.save(item);
            }
        }
        if (item.getStatus().equals(StatusConstant.DELETED.getValue())) {
            throw new DataIntegrityException("Cannot add item with status deleted", item.getItemId());
        } else {
            throw new DataIntegrityException("status not supported", item.getItemId());
        }
    }

    @Override
    public List<Item> getItem() {
        List<Item> items = itemDao.findAll();
        for (Item item : items) {
            if (item.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("Throwing exception " + NotFoundConstant.ITEM_NOT_FOUND.getValue());
                throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, 0);
            }
        }
        logger.info("returning list of items with status active");
        return itemDao.findByStatus(StatusConstant.ACTIVE.getValue());
    }

    @Override
    public Item getItemById(int itemId) {
        logger.info("checking if the item is present in database with itemId: " + itemId);
        Item item = itemDao.findById(itemId).orElseThrow(() -> {
            logger.info("Throwing exception " + NotFoundConstant.ITEM_NOT_FOUND.getValue() + " with itemId: " + itemId);
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
        });
        if(item.getStatus().equals(StatusConstant.DELETED.getValue())){
            logger.info("Throwing exception " + NotFoundConstant.ITEM_NOT_FOUND.getValue() + " with itemId: " + itemId);
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND,itemId);
        }
        logger.info("returning item with itemId: " + itemId);
        return itemDao.findByStatusAndItemId(StatusConstant.ACTIVE.getValue(), itemId);
    }


    @Override
    public Item updateItem(Item item, int itemId) {
        logger.info("checking if the item is present in database with itemId: " + itemId);
        Item updateItem = itemDao.findById(itemId).orElseThrow(() -> {
            logger.info("Throwing exception " + NotFoundConstant.ITEM_NOT_FOUND.getValue() + " with itemId: " + itemId);
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
        });
        logger.info("setting new item name");
        updateItem.setItemName(item.getItemName());
        logger.info("saving item to database with new name: " + updateItem.getItemName());
        return itemDao.save(updateItem);
    }

    @Override
    public void deleteItemById(int itemId) {
        logger.info("checking if the item is present in database with itemId: " + itemId);
        itemDao.findById(itemId).orElseThrow(() -> {
            logger.info("Throwing exception " + NotFoundConstant.ITEM_NOT_FOUND.getValue() + " with itemId: " + itemId);
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
        });
        logger.info("setting status of item to " + StatusConstant.DELETED.getValue());
        itemDao.softDelete(StatusConstant.DELETED.getValue(), itemId);
    }


    @Override
    public List<ItemSize> getItemSizeById(int itemId) {
        logger.info("checking if the item is present in the database with itemId: " + itemId);
        Item item = itemDao.findById(itemId).orElseThrow(() -> {
            logger.info("Throwing exception " + NotFoundConstant.ITEM_NOT_FOUND.getValue() + " with itemId: " + itemId);
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
        });
        logger.info("item found in database");
        if (item.getStatus().equals(StatusConstant.DELETED.getValue())) {
            logger.info("item status is deleted");
            logger.info("throwing exception " + NotFoundConstant.ITEM_NOT_FOUND.getValue() + " with itemId: " + itemId);
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
        }
        Set<InventoryDetail> inventory = item.getInventory();
        logger.info("checking if item has inventory");
        if (inventory.size() == 0) {
            logger.info("throwing exception " + NotFoundConstant.INVENTORY_NOT_FOUND.getValue() + " in item with itemId: " + itemId);
            throw new DataIntegrityException("This item does not have any inventory ", itemId);
        }
        logger.info("returning item size of item with itemId: " + itemId);
        return itemDao.getItemSizeById(itemId);
    }

    @Override
    public List<ItemSize> getAllItemSize() {
        List<InventoryDetail> inventory = inventoryDetailDao.findAll();
        logger.info("checking if item has inventory");
        if(inventory.size()==0){
            logger.info("throwing exception none of the item has inventory");
            throw new DataIntegrityException("None of the Item has inventory",0);
        }
        logger.info("returning list of itemSize based on custom query");
        return itemDao.getAllItemSize();
    }
//    public ItemDto itemToItemDto(Item item) {
//        ItemDto itemDto = new ItemDto();
//        itemDto = modelMapper.map(item, ItemDto.class);
//        return itemDto;
//    }
//
//    public Item itemDtoToItem(ItemDto itemDto) {
//        Item item = new Item();
//        item = modelMapper.map(itemDto, Item.class);
//        return item;
//    }
}
////ItemDto itemDto = new ItemDto();
////itemDto.setItemId(item.getItemId());
////itemDto.setItemType(item.getItemType());
////return itemDto;
