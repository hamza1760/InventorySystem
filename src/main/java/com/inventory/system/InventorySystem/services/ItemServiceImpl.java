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
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    private ModelMapper modelMapper;


    public Item addItem(Item item) {
        if (item.getStatus().equals(StatusConstant.ACTIVE.getValue())) {
            logger.info("Getting product type id from request body");
            int productTypeId = item.getProductType().getProductTypeId();
            logger.info("Checking if productType exists in database with productTypeId: " + productTypeId);
            ProductType productType = productTypeDao.findById(productTypeId).orElseThrow(() -> {
                logger.error("Product Type not found",new NotFoundException(NotFoundConstant.PRODUCT_TYPE_NOT_FOUND, productTypeId));
                throw new NotFoundException(NotFoundConstant.PRODUCT_TYPE_NOT_FOUND, productTypeId);
            });
            logger.info("Product Type found in database");
            logger.info("Checking productType status");
            if (productType.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("Product Type status is deleted");
                logger.error("Product Type not found",new NotFoundException(NotFoundConstant.PRODUCT_TYPE_NOT_FOUND, productTypeId));
                throw new NotFoundException(NotFoundConstant.PRODUCT_TYPE_NOT_FOUND, productTypeId);
            }
            logger.info("Getting brandId from request body");
            int brandId = item.getBrand().getBrandId();
            logger.info("Checking if brand exists in database with brandId: " + brandId);
            BrandDetail brand = brandDetailDao.findById(brandId).orElseThrow(() -> {
                logger.error("Brand not found",new NotFoundException(NotFoundConstant.BRAND_NOT_FOUND, brandId));
                throw new NotFoundException(NotFoundConstant.BRAND_NOT_FOUND, brandId);
            });
            logger.info("Brand found in database");
            logger.info("Checking brand status");
            if (brand.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("Brand status is deleted");
                logger.error("Brand not found",new NotFoundException(NotFoundConstant.BRAND_NOT_FOUND, brandId));
                throw new NotFoundException(NotFoundConstant.BRAND_NOT_FOUND, brandId);
            }
            logger.info("Getting itemId from request body");
            int itemId = item.getItemId();
            logger.info("Checking if item is already present in database with itemId: " + itemId);
            boolean checkItemId = itemDao.findById(itemId).isPresent();
            if (checkItemId) {
                logger.info("Item found in database");
                logger.error("Item already exists",new AlreadyExists(AlreadyExistsConstant.ITEM_ALREADY_EXISTS, itemId));
                throw new AlreadyExists(AlreadyExistsConstant.ITEM_ALREADY_EXISTS, itemId);
            } else {
                logger.info("Setting productType to item");
                item.setProductType(productType);
                logger.info("Setting brand to item");
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
                logger.error("Item not found ", new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND,0));
                throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, 0);
            }
        }
        logger.info("Returning list of items with status active");
        return itemDao.findByStatus(StatusConstant.ACTIVE.getValue());
    }

    @Override
    public Item getItemById(int itemId) {
        logger.info("Checking if the item is present in database with itemId: " + itemId);
        Item item = itemDao.findById(itemId).orElseThrow(() -> {
            logger.error("Item not found ", new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
        });
        if (item.getStatus().equals(StatusConstant.DELETED.getValue())) {
            logger.error("Item not found ", new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
        }
        logger.info("Returning item with itemId: " + itemId);
        return itemDao.findByStatusAndItemId(StatusConstant.ACTIVE.getValue(), itemId);
    }


    @Override
    public Item updateItem(Item item, int itemId) {
        logger.info("Checking if the item is present in database with itemId: " + itemId);
        Item updateItem = itemDao.findById(itemId).orElseThrow(() -> {
            logger.error("Item not found ", new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
        });
        logger.info("Setting new item name");
        updateItem.setItemName(item.getItemName());
        logger.info("Saving item to database with new name: " + updateItem.getItemName());
        return itemDao.save(updateItem);
    }

    @Override
    public void deleteItemById(int itemId) {
        logger.info("Checking if the item is present in database with itemId: " + itemId);
        Item item = itemDao.findById(itemId).orElseThrow(() -> {
            logger.error("Item not found ", new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
        });
        Set<InventoryDetail> inventoryDetail = item.getInventory();
        for(InventoryDetail inventory : inventoryDetail){
            inventory.setStatus(StatusConstant.DELETED.getValue());
            inventoryDetailDao.save(inventory);
        }

        logger.info("Setting status of item to " + StatusConstant.DELETED.getValue());
        itemDao.softDelete(StatusConstant.DELETED.getValue(), itemId);
    }


    @Override
    public List<ItemSize> getItemSizeById(int itemId) {
        logger.info("Checking if the item is present in the database with itemId: " + itemId);
        Item item = itemDao.findById(itemId).orElseThrow(() -> {
            logger.error("Item not found ", new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
        });
        logger.info("Item found in database");
        if (item.getStatus().equals(StatusConstant.DELETED.getValue())) {
            logger.info("Item status is deleted");
            logger.error("Item not found ", new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
            throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
        }
        Set<InventoryDetail> inventoryDetail = item.getInventory();
        logger.info("Checking if item has inventory");
        if (inventoryDetail.size() == 0) {
            logger.error("Inventory not found", new DataIntegrityException("This item does not have any inventory ", itemId));
            throw new DataIntegrityException("This item does not have any inventory ", itemId);
        }
        List<ItemSize> itemSizes = itemDao.getItemSizeById(StatusConstant.ACTIVE.getValue(), itemId);
        if (itemSizes.size() == 0) {
            logger.error("Inventory not found", new DataIntegrityException("This item does not have any inventory ", itemId));
            throw new DataIntegrityException("This item does not have any inventory ", itemId);
        }
        logger.info("Returning item size of item with itemId: " + itemId);
        return itemDao.getItemSizeById(StatusConstant.ACTIVE.getValue(), itemId);
    }


    @Override
    public List<ItemSize> getAllItemSize() {
        List<InventoryDetail> inventory = inventoryDetailDao.findAll();
        logger.info("Checking if item has inventory");
        if (inventory.size() == 0) {
            logger.error("Throwing exception none of the item has inventory");
            throw new DataIntegrityException("None of the Item has inventory", 0);
        }
        List<ItemSize> itemSizes = itemDao.getAllItemSize(StatusConstant.ACTIVE.getValue());
        if (itemSizes.size() == 0) {
            logger.error("Throwing exception none of the item has inventory");
            throw new DataIntegrityException("None of the Item has inventory", 0);
        }
        logger.info("Returning list of itemSize based on custom query");
        return itemDao.getAllItemSize(StatusConstant.ACTIVE.getValue());
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
