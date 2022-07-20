package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.*;
import com.inventory.system.InventorySystem.dao.*;
import com.inventory.system.InventorySystem.dto.*;
import com.inventory.system.InventorySystem.entities.*;
import com.inventory.system.InventorySystem.exceptions.*;
import com.inventory.system.InventorySystem.mapper.*;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class ItemServiceImpl implements ItemService {

    static Logger logger = Logger.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private BrandDetailDao brandDetailDao;

    @Autowired
    private ProductTypeDao productTypeDao;

    @Autowired
    private InventoryDetailDao inventoryDetailDao;

    @Autowired
    private GlobalMapper globalMapper;

    public ItemDto addItem(ItemDto itemDto) {
            logger.info("Getting product type id from request body");
            int productTypeId = itemDto.getProductType().getProductTypeId();
            logger.info("Checking if productType exists in database with productTypeId: " + productTypeId);
            ProductType productType = productTypeDao.findById(productTypeId).orElseThrow(() -> {
                logger.error("Product Type not found", new GlobalException(Constants.PRODUCT_TYPE_NOT_FOUND.getValue(), productTypeId));
                throw new GlobalException(Constants.PRODUCT_TYPE_NOT_FOUND.getValue(), productTypeId);
            });
            logger.info("Product Type found in database");
            logger.info("Checking productType status");
            if (productType.getStatus().equals(Constants.DELETED.getValue())) {
                logger.info("Product Type status is deleted");
                logger.error("Product Type not found", new GlobalException(Constants.PRODUCT_TYPE_NOT_FOUND.getValue(), productTypeId));
                throw new GlobalException(Constants.PRODUCT_TYPE_NOT_FOUND.getValue(), productTypeId);
            }
            logger.info("Getting brandId from request body");
            int brandId = itemDto.getBrand().getBrandId();
            logger.info("Checking if brand exists in database with brandId: " + brandId);
            BrandDetail brand = brandDetailDao.findById(brandId).orElseThrow(() -> {
                logger.error("Brand not found", new GlobalException(Constants.BRAND_NOT_FOUND.getValue(), brandId));
                throw new GlobalException(Constants.BRAND_NOT_FOUND.getValue(), brandId);
            });
            logger.info("Brand found in database");
            logger.info("Checking brand status");
            if (brand.getStatus().equals(Constants.DELETED.getValue())) {
                logger.info("Brand status is deleted");
                logger.error("Brand not found", new GlobalException(Constants.BRAND_NOT_FOUND.getValue(), brandId));
                throw new GlobalException(Constants.BRAND_NOT_FOUND.getValue(), brandId);
            }
            logger.info("Getting itemId from request body");
            int itemId = itemDto.getItemId();
            logger.info("Checking if item is already present in database with itemId: " + itemId);
            boolean checkItemId = itemDao.findById(itemId).isPresent();
            if (checkItemId) {
                logger.info("Item found in database");
                logger.error("Item already exists", new GlobalException(Constants.ITEM_ALREADY_EXISTS.getValue(), itemId));
                throw new GlobalException(Constants.ITEM_ALREADY_EXISTS.getValue(), itemId);
            } else {
                logger.info("Setting productType to item");
                itemDto.setProductType(globalMapper.productTypeToProductTypeDto(productType));
                logger.info("Setting brand to item");
                itemDto.setBrand(globalMapper.brandDetailToBrandDetailDto(brand));
                logger.info("Saving item in database with itemId: " + itemId + " productTypeId: " + productTypeId + " brandId: " + brandId);
                Item item = globalMapper.itemDtoItem(itemDto);
                return globalMapper.itemToItemDto(itemDao.save(item));
            }
    }

    @Override
    public List<ItemDto> getItem() {
        List<Item> items = itemDao.findAll();
        for (Item item : items) {
            if (item.getStatus().equals(Constants.DELETED.getValue())) {
                logger.error("Item not found ", new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), 0));
                throw new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), 0);
            }
        }
        logger.info("Returning list of items with status active");
        return itemDao.findByStatus(Constants.ACTIVE.getValue()).stream().map(globalMapper::itemToItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto getItemById(int itemId) {
        logger.info("Checking if the item is present in database with itemId: " + itemId);
        Item item = itemDao.findById(itemId).orElseThrow(() -> {
            logger.error("Item not found ", new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId));
            throw new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId);
        });
        if (item.getStatus().equals(Constants.DELETED.getValue())) {
            logger.error("Item not found ", new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId));
            throw new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId);
        }
        logger.info("Returning item with itemId: " + itemId);
        return globalMapper.itemToItemDto(itemDao.findByStatusAndItemId(Constants.ACTIVE.getValue(), itemId));
    }

    @Override
    public void deleteItemById(int itemId) {
        logger.info("Checking if the item is present in database with itemId: " + itemId);
        Item item = itemDao.findById(itemId).orElseThrow(() -> {
            logger.error("Item not found ", new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId));
            throw new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId);
        });
        Set<InventoryDetail> inventoryDetail = item.getInventory();
        for (InventoryDetail inventory : inventoryDetail) {
            inventory.setStatus(Constants.DELETED.getValue());
            inventoryDetailDao.save(inventory);
        }
        logger.info("Setting status of item to " + Constants.DELETED.getValue());
        itemDao.softDelete(Constants.DELETED.getValue(), itemId);
    }

    @Override
    public List<ItemSizeDto> getItemSizeById(int itemId) {
        logger.info("Checking if the item is present in the database with itemId: " + itemId);
        Item item = itemDao.findById(itemId).orElseThrow(() -> {
            logger.error("Item not found ", new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId));
            throw new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId);
        });
        logger.info("Item found in database");
        if (item.getStatus().equals(Constants.DELETED.getValue())) {
            logger.info("Item status is deleted");
            logger.error("Item not found ", new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId));
            throw new GlobalException(Constants.ITEM_NOT_FOUND.getValue(), itemId);
        }
        Set<InventoryDetail> inventoryDetail = item.getInventory();
        logger.info("Checking if item has inventory");
        if (inventoryDetail.size() == 0) {
            logger.error("Inventory not found", new GlobalException("This item does not have any inventory ", itemId));
            throw new GlobalException("This item does not have any inventory ", itemId);
        }
        List<ItemSize> itemSizes = itemDao.getItemSizeById(Constants.ACTIVE.getValue(), itemId);
        if (itemSizes.size() == 0) {
            logger.error("Inventory not found", new GlobalException("This item does not have any inventory ", itemId));
            throw new GlobalException("This item does not have any inventory ", itemId);
        }
        logger.info("Returning item size of item with itemId: " + itemId);
        return itemSizes.stream().map(globalMapper::itemSizeToItemSizeDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemSizeDto> getAllItemSize() {
        List<InventoryDetail> inventory = inventoryDetailDao.findAll();
        logger.info("Checking if item has inventory");
        if (inventory.size() == 0) {
            logger.error("Throwing exception none of the item has inventory");
            throw new GlobalException("None of the Item has inventory", 0);
        }
        List<ItemSize> itemSizes = itemDao.getAllItemSize(Constants.ACTIVE.getValue());
        if (itemSizes.size() == 0) {
            logger.error("Throwing exception none of the item has inventory");
            throw new GlobalException("None of the Item has inventory", 0);
        }
        logger.info("Returning list of itemSize based on custom query");
        return itemSizes.stream().map(globalMapper::itemSizeToItemSizeDto).collect(Collectors.toList());
    }
}
