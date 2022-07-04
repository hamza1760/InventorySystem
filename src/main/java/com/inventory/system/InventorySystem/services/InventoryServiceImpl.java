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
import com.inventory.system.InventorySystem.entities.Warehouse;
import com.inventory.system.InventorySystem.exceptions.DataIntegrityException;
import com.inventory.system.InventorySystem.exceptions.alreadyexists.AlreadyExists;
import com.inventory.system.InventorySystem.exceptions.notfound.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InventoryServiceImpl implements InventoryService {

    static Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);


    @Autowired
    private InventoryDetailDao inventoryDetailDao;


    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemTypeDao itemTypeDao;


    @Override
    public InventoryDetail addInventory(InventoryDetail inventoryDetail) {
        if (inventoryDetail.getStatus().equals(StatusConstant.ACTIVE.getValue())) {
            logger.info("getting item from request body");
            int itemId = inventoryDetail.getItem().getItemId();
            logger.info("checking if item exists in database with itemId: " + itemId);
            Item item = itemDao.findById(itemId).orElseThrow(() -> {
                logger.info("Throwing exception " + NotFoundConstant.ITEM_NOT_FOUND.getValue() + " with itemId: " + itemId);
                throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
            });
            logger.info("returning item");
            logger.info("checking item status");
            if (item.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("item status is deleted");
                logger.info("throwing exception " + NotFoundConstant.ITEM_NOT_FOUND.getValue() + " with itemId: " + itemId);
                throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemId);
            }
            logger.info("getting itemTypeId from request body");
            int itemTypeId = inventoryDetail.getItemType().getItemTypeId();
            logger.info("checking if itemType exists in database with itemTypeId: " + itemTypeId);
            ItemType itemType = itemTypeDao.findById(itemTypeId).orElseThrow(() -> {
                logger.info("Throwing exception " + NotFoundConstant.ITEM_TYPE_NOT_FOUND.getValue() + " with itemTypeId: " + itemTypeId);
                throw new NotFoundException(NotFoundConstant.ITEM_TYPE_NOT_FOUND, itemTypeId);
            });
            logger.info("returning itemType");
            logger.info("checking itemType status");
            if (itemType.getStatus().equals(StatusConstant.DELETED.getValue())) {
                logger.info("itemType status is deleted");
                logger.info("throwing exception " + NotFoundConstant.ITEM_TYPE_NOT_FOUND.getValue() + " with itemTypeId: " + itemTypeId);
                throw new NotFoundException(NotFoundConstant.ITEM_NOT_FOUND, itemTypeId);
            }
            logger.info("getting inventoryId from request body");
            int inventoryId = inventoryDetail.getInventoryId();
            logger.info("checking if inventory is already present in database with inventoryId: " + inventoryId);
            boolean checkInventory = inventoryDetailDao.findById(inventoryId).isPresent();
            if (checkInventory) {
                logger.info("inventory found in database");
                logger.info("throwing exception " + AlreadyExistsConstant.INVENTORY_ALREADY_EXISTS.getValue() + " with inventoryId: " + inventoryId);
                throw new AlreadyExists(AlreadyExistsConstant.INVENTORY_ALREADY_EXISTS, inventoryId);
            } else {
                logger.info("setting item to inventory");
                inventoryDetail.setItem(item);
                logger.info("setting itemType to inventory");
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
                logger.info("Throwing exception " + NotFoundConstant.INVENTORY_NOT_FOUND.getValue());
                throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, 0);
            }
        }
        logger.info("returning list of inventories from database");
        return inventoryDetailDao.findByStatus(StatusConstant.ACTIVE.getValue());
    }

    @Override
    public InventoryDetail getInventoryById(int inventoryId) {
        logger.info("checking if the inventory is present in database with inventoryId: " + inventoryId);
        InventoryDetail inventory = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
            logger.info("Throwing exception " + NotFoundConstant.INVENTORY_NOT_FOUND.getValue() + " with inventoryId: " + inventoryId);
            throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
        });
        if(inventory.getStatus().equals(StatusConstant.DELETED.getValue())){
            logger.info("Throwing exception " + NotFoundConstant.INVENTORY_NOT_FOUND.getValue() + " with inventoryId: " + inventoryId);
            throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND,inventoryId);
        }
        logger.info("returning inventory with inventoryId: " + inventoryId);
        return inventoryDetailDao.findByStatusAndInventoryId(StatusConstant.ACTIVE.getValue(), inventoryId);
    }


    @Override
    public InventoryDetail setItemQuantityInAllWarehouses(InventoryDetail inventoryDetail, int inventoryId) {
        logger.info("checking if the inventory is present in database with inventoryId: " + inventoryId);
        InventoryDetail setItemQuantity = inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
            logger.info("Throwing exception " + NotFoundConstant.INVENTORY_NOT_FOUND.getValue() + " with inventoryId: " + inventoryId);
            throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
        });
        logger.info("setting Available Quantity of item in database");
        setItemQuantity.setAvlQty(inventoryDetail.getAvlQty());
        logger.info("setting In Stock Quantity of item in database");
        setItemQuantity.setInStock(inventoryDetail.getInStock());
        logger.info("saving updated inventory in database");
        return inventoryDetailDao.save(setItemQuantity);
    }


    @Override
    public void deleteInventoryById(int inventoryId) {
        logger.info("checking if the inventory is present in database with inventoryId: " + inventoryId);
        inventoryDetailDao.findById(inventoryId).orElseThrow(() -> {
            logger.info("Throwing exception " + NotFoundConstant.INVENTORY_NOT_FOUND.getValue() + " with inventoryId: " + inventoryId);
            throw new NotFoundException(NotFoundConstant.INVENTORY_NOT_FOUND, inventoryId);
        });
        logger.info("setting status of inventory to: " + StatusConstant.DELETED.getValue());
        inventoryDetailDao.softDelete(StatusConstant.DELETED.getValue(), inventoryId);
    }
}
