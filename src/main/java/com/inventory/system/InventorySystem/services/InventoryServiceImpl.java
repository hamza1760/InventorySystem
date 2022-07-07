package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;
import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;
import com.inventory.system.InventorySystem.constant.status.StatusConstant;
import com.inventory.system.InventorySystem.dao.InventoryDetailDao;
import com.inventory.system.InventorySystem.dao.ItemDao;
import com.inventory.system.InventorySystem.dao.ItemTypeDao;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Item;
import com.inventory.system.InventorySystem.entities.ItemType;
import com.inventory.system.InventorySystem.exceptions.DataIntegrityException;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InventoryServiceImpl implements InventoryService {

    static Logger logger = Logger.getLogger(InventoryServiceImpl.class);


    @Autowired
    private InventoryDetailDao inventoryDetailDao;


    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemTypeDao itemTypeDao;


    @Override
    public InventoryDetail addInventory(InventoryDetail inventoryDetail) {
        if (inventoryDetail.getStatus().equals(StatusConstant.ACTIVE.getValue())) {
            logger.info("Getting item from request body");
            int itemId = inventoryDetail.getItem().getItemId();
            logger.info("Checking if item exists in database with itemId: " + itemId);
            Item item = itemDao.findById(itemId).orElseThrow(() -> {
                logger.error("Item not found ", new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
                throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
            });
            logger.info("Item found in database");
            logger.info("Checking item status");
            if (item.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("Item status is deleted");
                logger.error("Item not found ", new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId));
                throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
            }
            logger.info("Getting itemTypeId from request body");
            int itemTypeId = inventoryDetail.getItemType().getItemTypeId();
            logger.info("Checking if itemType exists in database with itemTypeId: " + itemTypeId);
            ItemType itemType = itemTypeDao.findById(itemTypeId).orElseThrow(() -> {
                logger.error("Item Type not found ", new NotFoundException(NotFoundConstant.ITEM_TYPE_NOT_FOUND, itemTypeId));
                throw new NotFoundException(NotFoundConstant.ITEM_TYPE_NOT_FOUND, itemTypeId);
            });
            logger.info("Item Type found in database");
            logger.info("Checking itemType status");
            if (itemType.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("Item Type status is deleted");
                logger.error("Item Type not found ", new NotFoundException(NotFoundConstant.ITEM_TYPE_NOT_FOUND, itemTypeId));
                throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemTypeId);
            }
            logger.info("Getting inventoryId from request body");
            int inventoryId = inventoryDetail.getInventoryId();
            logger.info("Checking if inventory is already present in database with inventoryId: " + inventoryId);
            boolean checkInventory = inventoryDetailDao.findById(inventoryId).isPresent();
            if (checkInventory) {
                logger.info("Inventory found in database");
                logger.error("Inventory already exists",new AlreadyExists(AlreadyExistsConstant.INVENTORY_ALREADY_EXISTS, inventoryId));
                throw new AlreadyExists(AlreadyExistsConstant.INVENTORY_ALREADY_EXISTS, inventoryId);
            } else {
                logger.info("Setting item to inventory");
                inventoryDetail.setItem(item);
                logger.info("Setting itemType to inventory");
                inventoryDetail.setItemType(itemType);
                logger.info("Saving inventory in database with inventoryId: " + inventoryId + " itemId: " + itemId + " itemTypeId: " + itemTypeId);
                return inventoryDetailDao.save(inventoryDetail);
            }
        }
        if (inventoryDetail.getStatus().equals(StatusConstant.DELETED.getValue())) {
            throw new DataIntegrityException("Cannot add inventory with status Deleted", inventoryDetail.getInventoryId());
        } else {
            throw new DataIntegrityException("status not supported", inventoryDetail.getInventoryId());
        }
    }

    @Override
    public List<InventoryDetail> getInventory() {
        List<InventoryDetail> inventoryDetail = inventoryDetailDao.findAll();
        for (InventoryDetail inventory : inventoryDetail) {
            if (inventory.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.error("Inventory not found",new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, 0));
                throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, 0);
            }
        }
        logger.info("Returning list of inventories from database");
        return inventoryDetailDao.findByStatus(StatusConstant.ACTIVE.getValue());
    }

    @Override
    public InventoryDetail getInventoryById(int inventoryId) {
        logger.info("Checking if the inventory is present in database with inventoryId: " + inventoryId);
        InventoryDetail inventory = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
            logger.error("Inventory not found",new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId));
            throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
        });
        if (inventory.getStatus().equals(StatusConstant.DELETED.getValue())) {
            logger.error("Inventory not found",new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId));
            throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
        }
        logger.info("Returning inventory with inventoryId: " + inventoryId);
        return inventoryDetailDao.findByStatusAndInventoryId(StatusConstant.ACTIVE.getValue(), inventoryId);
    }


    @Override
    public InventoryDetail setItemQuantityInAllWarehouses(InventoryDetail inventoryDetail, int inventoryId) {
        logger.info("Checking if the inventory is present in database with inventoryId: " + inventoryId);
        InventoryDetail setItemQuantity = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
            logger.error("Inventory not found",new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId));
            throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
        });
        logger.info("Setting Available Quantity of item in database");
        setItemQuantity.setAvlQty(inventoryDetail.getAvlQty());
        logger.info("Setting In Stock Quantity of item in database");
        setItemQuantity.setInStock(inventoryDetail.getInStock());
        logger.info("Saving updated inventory in database");
        return inventoryDetailDao.save(setItemQuantity);
    }


    @Override
    public void deleteInventoryById(int inventoryId) {
        logger.info("checking if the inventory is present in database with inventoryId: " + inventoryId);
        inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
            logger.error("Inventory not found",new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId));
            throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
        });
        logger.info("Setting status of inventory to " + StatusConstant.DELETED.getValue());
        inventoryDetailDao.softDelete(StatusConstant.DELETED.getValue(), inventoryId);
    }
}
